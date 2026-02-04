package anagrafica.service.zone.impl;

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
import anagrafica.repository.geography.CittaRepository;
import anagrafica.repository.zone.CompanyZoneAuditRepository;
import anagrafica.repository.zone.ZoneCompanyRepository;
import anagrafica.repository.zone.ZoneRepository;
import anagrafica.service.zone.ZoneService;
import anagrafica.utils.JwtUtil;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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

    public ZoneServiceImpl(ZoneRepository zoneRepository, AgentZoneRepository agentZoneRepository, CittaRepository cittaRepository, ZoneCompanyRepository zoneCompanyRepository, AgentZonePublisher agentZonePublisher, CompanyZonePublisher companyZonePublisher, CompanyZoneAuditRepository companyZoneAuditRepository, JwtUtil jwtUtil) {
        this.zoneRepository = zoneRepository;
        this.agentZoneRepository = agentZoneRepository;
        this.cittaRepository = cittaRepository;
        this.zoneCompanyRepository = zoneCompanyRepository;
        this.agentZonePublisher = agentZonePublisher;
        this.companyZonePublisher = companyZonePublisher;
        this.companyZoneAuditRepository = companyZoneAuditRepository;
        this.jwtUtil = jwtUtil;
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

        return new ZoneResponse(zone.getId(), zone.getName(), zone.getCitta().getNome());
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
                            zone.getCitta().getNome()
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
                            zoneResponse
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
}
