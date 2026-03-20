package anagrafica.service.zone.impl;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import anagrafica.client.GeocodingClient;
import anagrafica.client.response.geo.GeocodingResult;
import anagrafica.dto.agent.AgentResponse;
import anagrafica.dto.company.CompanyResponse;
import anagrafica.dto.event.AgentZoneEventDTO;
import anagrafica.dto.event.CompanyZoneEventDTO;
import anagrafica.dto.zone.ZoneRequest;
import anagrafica.dto.zone.ZoneResponse;
import anagrafica.entity.AgentZone;
import anagrafica.entity.Citta;
import anagrafica.entity.Zone;
import anagrafica.entity.ZoneCompany;
import anagrafica.entity.audit.CompanyZoneAudit;
import anagrafica.entity.audit.OperationAuditEnum;
import anagrafica.exception.RestException;
import anagrafica.publisher.AgentZonePublisher;
import anagrafica.publisher.CompanyZonePublisher;
import anagrafica.repository.agent.AgentZoneRepository;
import anagrafica.repository.audit.CompanyZoneAuditRepository;
import anagrafica.repository.geography.CittaRepository;
import anagrafica.repository.zone.ZoneCompanyRepository;
import anagrafica.repository.zone.ZoneRepository;
import anagrafica.service.zone.ZoneService;
import anagrafica.utils.JwtUtil;
import anagrafica.utils.MethodUtils;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class ZoneServiceImpl implements ZoneService {

    private final ZoneRepository zoneRepository;
    private final AgentZoneRepository agentZoneRepository;
    private final CittaRepository cittaRepository;
    private final ZoneCompanyRepository zoneCompanyRepository;

    private final AgentZonePublisher agentZonePublisher;
    private final CompanyZonePublisher companyZonePublisher;
    private final CompanyZoneAuditRepository companyZoneAuditRepository;
    private final JwtUtil jwtUtil;
    private final GeocodingClient geocodingClient;

    public ZoneServiceImpl(ZoneRepository zoneRepository, AgentZoneRepository agentZoneRepository, CittaRepository cittaRepository, ZoneCompanyRepository zoneCompanyRepository, AgentZonePublisher agentZonePublisher, CompanyZonePublisher companyZonePublisher, CompanyZoneAuditRepository companyZoneAuditRepository, JwtUtil jwtUtil, GeocodingClient geocodingClient) {
        this.zoneRepository = zoneRepository;
        this.agentZoneRepository = agentZoneRepository;
        this.cittaRepository = cittaRepository;
        this.zoneCompanyRepository = zoneCompanyRepository;
        this.agentZonePublisher = agentZonePublisher;
        this.companyZonePublisher = companyZonePublisher;
        this.companyZoneAuditRepository = companyZoneAuditRepository;
        this.jwtUtil = jwtUtil;
        this.geocodingClient = geocodingClient;
    }

    @Override
    @Transactional
    public ZoneResponse create(ZoneRequest request) {
        final Optional<Citta> optionalCitta = cittaRepository.findById(request.getCityId());

        if(optionalCitta.isEmpty()){
            throw new RestException("Citta Not Exist");
        }

        final Optional<Zone> existOptionalZone = zoneRepository.existWithName(request.getName());

        if(existOptionalZone.isPresent()){
            throw new RestException("Zone With Name: "+request.getName()+" Is Exist");
        }

        Zone zone = new Zone();
        zone.setName(request.getName());
        zone.setCitta(optionalCitta.get());

        zone = zoneRepository.save(zone);

        return new ZoneResponse(zone.getId(), zone.getName(), zone.getCitta() != null ? zone.getCitta().getNome() : null);
    }

    @Override
    @Transactional
    public ZoneResponse update(Long id, ZoneRequest request) {

        final Optional<Zone> optionalZone = zoneRepository.findById(id);

        if(optionalZone.isEmpty()){
            throw new RestException("Zone Not Found");
        }

        final Optional<Citta> optionalCitta = cittaRepository.findById(request.getCityId());

        if(optionalCitta.isEmpty()){
            if(!optionalZone.get().getId().equals(optionalCitta.get().getId())){
                throw new RestException("Citta Not Exist");
            }
        }

        final Optional<Zone> existOptionalZone = zoneRepository.existWithName(request.getName());

        if(existOptionalZone.isPresent()){
            if(!optionalZone.get().getId().equals(
                    existOptionalZone.get().getId()
            )){
                throw new RestException("Zone With Name: "+request.getName()+" Is Exist");
            }
        }

        optionalZone.get().setCitta(optionalCitta.get());
        optionalZone.get().setName(request.getName());

        zoneRepository.save(optionalZone.get());

        return new ZoneResponse(optionalZone.get().getId(), optionalZone.get().getName(), optionalZone.get().getCitta().getNome());
    }

    @Override
    public List<ZoneResponse> findAll() {
        final List<ZoneResponse> responses = new ArrayList<>();
        final List<Zone> list = zoneRepository.findAll();
        for(final Zone zone: list){
            responses.add(
                    new ZoneResponse(
                            zone.getId(),
                            zone.getName(),
                            zone.getCitta() != null ? zone.getCitta().getNome() : null
                    )
            );
        }
        return responses;
    }

    @Override
    @Transactional
    public void delete(Long id) {

        final Optional<Zone> optionalZone = zoneRepository.findById(id);

        if(optionalZone.isEmpty()){
            throw new RestException("Zone Not Found");
        }

        final List<AgentZone> agentZones = agentZoneRepository.findAllZoneWithIdZoneAndAgents(id);
        if(agentZones.isEmpty()){
            log.info("For This Zone NOT Have Agents: {} , city: {} ", optionalZone.get().getName(), optionalZone.get().getCitta().getNome());
        }

        for(final AgentZone agentZone: agentZones){
            log.info("This Agent cancelled in this Zone, name: {} , surname: {}", agentZone.getAgent().getName(), agentZone.getAgent().getSurname());
            agentZone.setIsActive(Boolean.FALSE);
            agentZone.setDeleted(Boolean.FALSE);
            agentZoneRepository.save(agentZone);

            final AgentZoneEventDTO audit = new AgentZoneEventDTO();
            audit.setOperationBy(String.valueOf(jwtUtil.getIdProfileLogged()));
            audit.setOperationDate(LocalDateTime.now().toString());
            audit.setOperationAudit(OperationAuditEnum.REVOKE.name());
            audit.setZoneId(String.valueOf(agentZone.getZone().getId()));
            audit.setAgentId(String.valueOf(agentZone.getAgent().getId()));
            agentZonePublisher.publish(audit);
        }

        //ZONE COMPANY METHOD SYNC

        final List<ZoneCompany> zoneCompanies = zoneCompanyRepository.findAllCompanyFromZone(id);

        for(final ZoneCompany zoneCompany: zoneCompanies){
            zoneCompany.setDeleted(Boolean.TRUE);
            zoneCompanyRepository.save(zoneCompany);

            final CompanyZoneEventDTO companyZoneEventDTO = new CompanyZoneEventDTO();
            companyZoneEventDTO.setCompanyId(String.valueOf(zoneCompany.getCompany().getId()));
            companyZoneEventDTO.setOperationBy(String.valueOf(jwtUtil.getIdProfileLogged()));
            companyZoneEventDTO.setOperationDate(LocalDateTime.now());
            companyZoneEventDTO.setOperationAudit(OperationAuditEnum.REVOKE.name());
            companyZoneEventDTO.setZoneId(String.valueOf(zoneCompany.getZone().getId()));
            companyZonePublisher.publish(companyZoneEventDTO);
        }

        optionalZone.get().setDeleted(Boolean.TRUE);
        zoneRepository.save(optionalZone.get());
    }

    @Override
    public List<CompanyResponse> findAllCompanyFromZoneId(Long zoneId) {
        final List<ZoneCompany> zoneCompanies = zoneCompanyRepository.findAllCompanyFromZone(zoneId);
        if(zoneCompanies.isEmpty()){
            log.warn("Not Exist Company For This Zone");
            return new ArrayList<>();
        }
        final List<CompanyResponse> companyResponses = new ArrayList<>();
        for(final ZoneCompany zoneCompany: zoneCompanies){
            companyResponses.add(
                    new CompanyResponse(
                            zoneCompany.getCompany().getId(),
                            zoneCompany.getCompany().getName(),
                            zoneCompany.getCompany().getPiva(),
                            zoneCompany.getCompany().getCode(),
                            zoneCompany.getCompany().getDescription(),
                            null,
                            null,
                            null,
                            null,
                            null,
                            null,
                            null,
                            null
                    )
            );
        }
        return companyResponses;
    }

    @Override
    public List<AgentResponse> findAllAgentsFromZoneId(Long zoneId) {
        final List<AgentResponse> responses = new ArrayList<>();
        final List<AgentZone> agentZones = agentZoneRepository.findAllZoneWithIdZoneAndAgents(zoneId);
        if(agentZones.isEmpty()){
            throw new RestException("AgentZone Not Found");
        }

        for(final AgentZone agentZone: agentZones){
            final ZoneResponse zoneResponse = new ZoneResponse();
            zoneResponse.setId(agentZone.getZone().getId());
            zoneResponse.setName(agentZone.getZone().getName());
            zoneResponse.setCity(agentZone.getZone().getCitta().getNome());
            responses.add(
                    new AgentResponse(
                            agentZone.getAgent().getId(),
                            agentZone.getAgent().getName(),
                            agentZone.getAgent().getSurname(),
                            null,
                            zoneResponse,
                            null,
                            null,
                            null
                    )
            );
        }

        return responses;
    }

    @Override
    public void addCompanyToZone(Long placerId, Long zoneId, Long companyId) {

    }

    @Override
    public void audit(CompanyZoneEventDTO companyZoneEventDTO) {
        final CompanyZoneAudit audit = new CompanyZoneAudit();
        audit.setCreatedAt(companyZoneEventDTO.getOperationDate());
        audit.setOperation(OperationAuditEnum.valueOf(companyZoneEventDTO.getOperationAudit()));
        audit.setIdZone(Long.valueOf(companyZoneEventDTO.getZoneId()));
        audit.setIdCompany(Long.valueOf(companyZoneEventDTO.getCompanyId()));
        audit.setUserOperationId(Long.valueOf(companyZoneEventDTO.getOperationBy()));
        companyZoneAuditRepository.save(audit);
    }

	@Override
	public ZoneResponse findByName(String name) {
		final Optional<Zone> optionalZone = zoneRepository.existWithName(name);
		if(optionalZone.isEmpty()) {
			return null;
		}
		 final ZoneResponse zoneResponse = new ZoneResponse();
         zoneResponse.setId(optionalZone.get().getId());
         zoneResponse.setName(optionalZone.get().getName());
         zoneResponse.setCity(optionalZone.get().getCitta() != null ? optionalZone.get().getCitta().getNome() : null);
        return zoneResponse;
	}

	@Override
	@Transactional
	public ZoneResponse createForImport(ZoneRequest request) {
		
		if(request.getCityId() != null) {
			final Optional<Citta> optionalCitta = cittaRepository.findById(request.getCityId());
	        if(optionalCitta.isEmpty()){
	        }
		}
		
        final Optional<Zone> existOptionalZone = zoneRepository.existWithName(request.getName());

        if(existOptionalZone.isPresent()){
        	return new ZoneResponse(existOptionalZone.get().getId(), existOptionalZone.get().getName(),null);
        }

        Zone zone = new Zone();
        zone.setName(request.getName());

        zone = zoneRepository.save(zone);

        return new ZoneResponse(zone.getId(), zone.getName(), null);
	}
	
	@Transactional
	private void toSaveZone(final Zone zone) {
		 if (zone.getName() != null) {
             try {
                 log.info("Zone ID {} and NAME: {}", zone.getId(), zone.getName());
                 final GeocodingResult result = geocodingClient.geocode(zone.getName());
                 log.info("LAT {} and LON {}",result.latitude(), result.longitude());
                 zone.setLat(result.latitude());
                 zone.setLon(result.longitude());
                 zoneRepository.save(zone);
                 
                 Thread.sleep(1100);
             } catch (Exception e) {
                 log.error("❌ Errore per zona {} - {}: {}", zone.getId(), zone.getName(), e.getMessage());
             }
         }
	}

	@Override
	public void populateCoordinate() {
		int pageNumber = 0;
	    int pageSize = 20;
	    Page<Zone> page;

	    do {
	    	log.info("pageNumber {} and pageSize {}", pageNumber, pageSize);
	        page = zoneRepository.findAllNotDeletedWithNotCoordinate(MethodUtils.getPagination(pageNumber, pageSize));

	        for (Zone zone : page) {
	        	try {
	        		toSaveZone(zone);
	        	}catch (Exception e) {
					log.error(e.getMessage());
				}
	        }

	        pageNumber++;

	    } while (page.hasNext());
	}

	@Override
	public List<ZoneResponse> findAll(Integer offset, Integer limit) {
		final List<ZoneResponse> response = new ArrayList<>();
		final Page<Zone> entityList = zoneRepository.findAllNotDeleted(MethodUtils.getPagination(offset, limit));
		if(!entityList.isEmpty()) {
			for(final Zone item: entityList) {
				response.add(
						new ZoneResponse(item.getId(), item.getName(), item.getCitta() != null ? item.getCitta().getNome() : "N/A")
						);
			}
		}
		return response;
	}

	@Override
	public List<ZoneResponse> search(Integer offset, Integer limit, String name) {
		final List<ZoneResponse> response = new ArrayList<>();
		final Page<Zone> entityList = zoneRepository.searchAllNotDeleted(name, MethodUtils.getPagination(offset, limit));
		if(!entityList.isEmpty()) {
			for(final Zone item: entityList) {
				response.add(
						new ZoneResponse(item.getId(), item.getName(), item.getCitta() != null ? item.getCitta().getNome() : "N/A")
						);
			}
		}
		return response;
	}
}
