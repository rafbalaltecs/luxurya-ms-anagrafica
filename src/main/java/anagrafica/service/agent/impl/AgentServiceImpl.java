package anagrafica.service.agent.impl;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import anagrafica.client.ProductClient;
import anagrafica.dto.agent.AgentConfigurationVoyageResponse;
import anagrafica.dto.agent.AgentCurrentVoyageResponse;
import anagrafica.dto.agent.AgentProductResponse;
import anagrafica.dto.agent.AgentRequest;
import anagrafica.dto.agent.AgentResponse;
import anagrafica.dto.company.CompanyStockResponse;
import anagrafica.dto.event.AgentZoneEventDTO;
import anagrafica.dto.ext.ProductResponse;
import anagrafica.dto.voyage.VoyageClientResponse;
import anagrafica.dto.voyage.VoyageCustomerStatusAgentResponse;
import anagrafica.dto.voyage.VoyageOperationResponse;
import anagrafica.dto.zone.ZoneResponse;
import anagrafica.entity.Agent;
import anagrafica.entity.AgentCurrentVoyage;
import anagrafica.entity.AgentZone;
import anagrafica.entity.Company;
import anagrafica.entity.CompanyAddress;
import anagrafica.entity.CompanyStock;
import anagrafica.entity.ConfigurationVoyage;
import anagrafica.entity.ConfigurationVoyageZone;
import anagrafica.entity.User;
import anagrafica.entity.Voyage;
import anagrafica.entity.VoyageCompany;
import anagrafica.entity.VoyageCompanyOperation;
import anagrafica.entity.Zone;
import anagrafica.entity.ZoneCompany;
import anagrafica.entity.audit.AgentZoneAudit;
import anagrafica.entity.audit.OperationAuditEnum;
import anagrafica.exception.RestException;
import anagrafica.publisher.AgentZonePublisher;
import anagrafica.repository.agent.AgentCurrentVoyageRepository;
import anagrafica.repository.agent.AgentRepository;
import anagrafica.repository.agent.AgentZoneRepository;
import anagrafica.repository.audit.AuditAgentZoneRepository;
import anagrafica.repository.company.CompanyAddressRepository;
import anagrafica.repository.company.CompanyStockRepository;
import anagrafica.repository.user.UserRepository;
import anagrafica.repository.voyage.ConfigurationVoyageRepository;
import anagrafica.repository.voyage.ConfigurationVoyageZoneRepository;
import anagrafica.repository.voyage.VoyageCompanyOperationRepository;
import anagrafica.repository.voyage.VoyageCompanyRepository;
import anagrafica.repository.voyage.VoyageRepository;
import anagrafica.repository.zone.ZoneCompanyRepository;
import anagrafica.repository.zone.ZoneRepository;
import anagrafica.service.agent.AgentService;
import anagrafica.service.user.UserMapper;
import anagrafica.service.voyage.impl.ConfigurationVoyageMapper;
import anagrafica.utils.JwtUtil;
import anagrafica.utils.MethodUtils;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class AgentServiceImpl implements AgentService {

    private final AgentRepository agentRepository;
    private final ZoneRepository zoneRepository;
    private final AgentZoneRepository agentZoneRepository;
    private final AuditAgentZoneRepository auditAgentZoneRepository;
    private final UserRepository userRepository;
    private final AgentZonePublisher agentZonePublisher;
    private final JwtUtil jwtUtil;
    private final UserMapper userMapper;
    private final ZoneCompanyRepository zoneCompanyRepository;
    private final ConfigurationVoyageZoneRepository configurationVoyageZoneRepository;
    private final ConfigurationVoyageRepository configurationVoyageRepository;
    private final ConfigurationVoyageMapper configurationVoyageMapper;
    private final VoyageCompanyRepository voyageCompanyRepository;
    private final VoyageCompanyOperationRepository voyageCompanyOperationRepository;
    private final AgentCurrentVoyageRepository agentCurrentVoyageRepository;
    private final VoyageRepository voyageRepository;
    private final ProductClient productClient;
    private final CompanyAddressRepository companyAddressRepository;
    private final CompanyStockRepository companyStockRepository;
   

	public AgentServiceImpl(AgentRepository agentRepository, ZoneRepository zoneRepository,
			AgentZoneRepository agentZoneRepository, AuditAgentZoneRepository auditAgentZoneRepository,
			UserRepository userRepository, AgentZonePublisher agentZonePublisher, JwtUtil jwtUtil,
			UserMapper userMapper, ZoneCompanyRepository zoneCompanyRepository,
			ConfigurationVoyageZoneRepository configurationVoyageZoneRepository,
			ConfigurationVoyageRepository configurationVoyageRepository,
			ConfigurationVoyageMapper configurationVoyageMapper,
			VoyageCompanyRepository voyageCompanyRepository,
			VoyageCompanyOperationRepository voyageCompanyOperationRepository,
			AgentCurrentVoyageRepository agentCurrentVoyageRepository,
			VoyageRepository voyageRepository,
			ProductClient productClient,
			CompanyAddressRepository companyAddressRepository,
			CompanyStockRepository companyStockRepository) {
		this.agentRepository = agentRepository;
		this.zoneRepository = zoneRepository;
		this.agentZoneRepository = agentZoneRepository;
		this.auditAgentZoneRepository = auditAgentZoneRepository;
		this.userRepository = userRepository;
		this.agentZonePublisher = agentZonePublisher;
		this.jwtUtil = jwtUtil;
		this.userMapper = userMapper;
		this.zoneCompanyRepository = zoneCompanyRepository;
		this.configurationVoyageRepository = configurationVoyageRepository;
		this.configurationVoyageZoneRepository = configurationVoyageZoneRepository;
		this.configurationVoyageMapper = configurationVoyageMapper;
		this.voyageCompanyRepository = voyageCompanyRepository;
		this.voyageCompanyOperationRepository = voyageCompanyOperationRepository;
		this.agentCurrentVoyageRepository = agentCurrentVoyageRepository;
		this.voyageRepository = voyageRepository;
		this.productClient = productClient;
		this.companyAddressRepository = companyAddressRepository;
		this.companyStockRepository = companyStockRepository;
	}
    


    @Override
    public List<AgentResponse> findAll(Integer offset, Integer limit) {
        final List<AgentResponse> responses = new ArrayList<>();
        final List<Agent> list = agentRepository.findAll();
        for(final Agent agent: list){
        	Integer totalCompany = 0;
            final List<AgentZone> agentZones = agentZoneRepository.findAllZonesFromAgentId(agent.getId());

            ZoneResponse zoneResponse = null;
            if(!agentZones.isEmpty()){
                zoneResponse = new ZoneResponse();
                final AgentZone agentZone = agentZones.get(0);
                if(agentZone.getZone() != null) {
                	zoneResponse.setId(agentZone.getZone().getId());
                    zoneResponse.setName(agentZone.getZone() != null ? agentZone.getZone().getName() : null);
                    zoneResponse.setCity(agentZone.getZone().getCitta() != null ? agentZone.getZone().getCitta().getNome() : null);
                    
                    final List<ZoneCompany> listZoneCompany = zoneCompanyRepository.findAllCompanyFromZone(agentZone.getZone().getId());
                    totalCompany = listZoneCompany.size();
                }
                
                
            }
            
            responses.add(
                    new AgentResponse(
                            agent.getId(),
                            agent.getName(),
                            agent.getSurname(),
                            agent.getTelephone(),
                            zoneResponse,
                            userMapper.entityToResponse(agent.getUser()),
                            totalCompany
                    )
            );
        }
        return responses;
    }

    @Override
    @Transactional
    public AgentResponse create(AgentRequest request) {
        final Optional<Agent> optionalAgent = agentRepository.findAgentFromUserId(request.getUserId());
        if(optionalAgent.isPresent()){
            throw new RestException("This user owns an agent");
        }

        final Optional<User> optionalUser = userRepository.findById(request.getUserId());

        if(optionalUser.isEmpty()){
            throw new RestException("User Not Found");
        }

        final Optional<Zone> optionalZone = zoneRepository.findById(request.getZoneId());

        if(optionalZone.isEmpty()){
            throw new RestException("Zone Not Found");
        }

        Agent agent = new Agent();
        agent.setName(request.getName());
        agent.setSurname(request.getSurname());
        agent.setUser(optionalUser.get());
        agent.setCreatedBy(jwtUtil.getIdProfileLogged().toString());
        agent.setTelephone(request.getTelephone());

        agent = agentRepository.save(agent);

        addZoneToAgent(agent.getId(), optionalZone.get().getId());

        final ZoneResponse zoneResponse = new ZoneResponse();
        zoneResponse.setCity(optionalZone.get().getCitta().getNome());
        zoneResponse.setName(optionalZone.get().getName());
        zoneResponse.setId(optionalZone.get().getId());

        return new AgentResponse(
                agent.getId(),
                request.getName(),
                request.getSurname(),
                request.getTelephone(),
                zoneResponse,
                userMapper.entityToResponse(agent.getUser()),
                null
                );
    }

    @Override
    @Transactional
    public AgentResponse update(Long id, AgentRequest request) {
        final Optional<Agent> optionalAgent = agentRepository.findById(id);
        if(optionalAgent.isEmpty()){
            throw new RestException("Agent Not Found");
        }

        final Optional<Agent> optionalAgentExistUser = agentRepository.findAgentFromUserId(request.getUserId());
        if(optionalAgentExistUser.isPresent()){
            if(!optionalAgentExistUser.get().getId().equals(optionalAgent.get().getId())){
                throw new RestException("This user owns an agent");
            }
        }

        final Optional<User> optionalUser = userRepository.findById(request.getUserId());

        if(optionalUser.isEmpty()){
            throw new RestException("User Not Found");
        }

        final Optional<Zone> optionalZone = zoneRepository.findById(request.getZoneId());

        if(optionalZone.isEmpty()){
            throw new RestException("Zone Not Found");
        }

        optionalAgent.get().setName(request.getName());
        optionalAgent.get().setSurname(request.getSurname());
        optionalAgent.get().setUpdatedBy(jwtUtil.getIdProfileLogged().toString());
        optionalAgent.get().setTelephone(request.getTelephone());
        agentRepository.save(optionalAgent.get());

        addZoneToAgent(optionalAgent.get().getId(), optionalZone.get().getId());

        final ZoneResponse zoneResponse = new ZoneResponse();
        zoneResponse.setCity(optionalZone.get().getCitta().getNome());
        zoneResponse.setName(optionalZone.get().getName());
        zoneResponse.setId(optionalZone.get().getId());

        return new AgentResponse(
                optionalAgent.get().getId(),
                request.getName(),
                request.getSurname(),
                request.getTelephone(),
                zoneResponse,
                userMapper.entityToResponse(optionalAgent.get().getUser()),
                null
        );
    }

    @Override
    @Transactional
    public void delete(Long id) {
        final Optional<Agent> optionalAgent = agentRepository.findById(id);
        if(optionalAgent.isEmpty()){
            throw new RestException("Agent Not Found");
        }

        final List<AgentZone> agentZones = agentZoneRepository.findAllZonesFromAgentId(id);
        if(agentZones.isEmpty()){
            log.warn("Not Zone For This Agent , name: {} , surname: {}", optionalAgent.get().getName(), optionalAgent.get().getSurname());
        }else{
            for(final AgentZone agentZone: agentZones){
                log.info("This Agent cancelled in this Zone, name: {} , surname: {}", agentZone.getAgent().getName(), agentZone.getAgent().getSurname());
                agentZone.setIsActive(Boolean.FALSE);
                agentZone.setDeleted(Boolean.FALSE);
                agentZoneRepository.save(agentZone);
            }

            publishRevokeAllZone(agentZones);
        }

        optionalAgent.get().setDeleted(Boolean.TRUE);
        agentRepository.save(optionalAgent.get());
    }

    @Override
    public List<ZoneResponse> findZonesFromAgent(Long agentId) {
        final Optional<Agent> optionalAgent = agentRepository.findById(agentId);
        if(optionalAgent.isEmpty()){
            throw new RestException("Agent Not Exist");
        }
        final List<AgentZone> agentZones = agentZoneRepository.findAllZonesFromAgentId(agentId);
        if(agentZones.isEmpty()){
            log.warn("Not Zone For This Agent , name: {} , surname: {}", optionalAgent.get().getName(), optionalAgent.get().getSurname());
        }
        final List<ZoneResponse> zoneResponses = new ArrayList<>();
        for(final AgentZone agentZone: agentZones){
            final ZoneResponse zoneResponse = new ZoneResponse();
            zoneResponse.setId(agentZone.getZone().getId());
            zoneResponse.setCity(agentZone.getZone().getCitta() != null ? agentZone.getZone().getCitta().getNome() : "N/A");
            zoneResponse.setName(agentZone.getZone().getName());
            zoneResponses.add(zoneResponse);
        }
        return zoneResponses;
    }

    @Override
    public void auditAgentZone(AgentZoneEventDTO eventDTO) {
        final AgentZoneAudit audit = new AgentZoneAudit();
        audit.setCreatedAt(LocalDateTime.parse(eventDTO.getOperationDate()));
        audit.setOperation(OperationAuditEnum.valueOf(eventDTO.getOperationAudit()));
        audit.setIdZone(Long.valueOf(eventDTO.getZoneId()));
        audit.setIdAgent(Long.valueOf(eventDTO.getAgentId()));
        audit.setUserOperationId(Long.valueOf(eventDTO.getOperationBy()));
        auditAgentZoneRepository.save(audit);
    }

    @Override
    @Transactional
    public void addZoneToAgent(Long idAgent, Long idZone) {
        final Optional<Agent> optionalAgent = agentRepository.findById(idAgent);
        if(optionalAgent.isEmpty()){
            throw new RestException("Agent Not Found");
        }

        final Optional<Zone> optionalZone = zoneRepository.findById(idZone);

        if(optionalZone.isEmpty()){
            throw new RestException("Zone Not Found");
        }

        final Optional<AgentZone> optionalAgentZone = agentZoneRepository.findAgentZoneWithSameData(
                idAgent, idZone
        );

        if(optionalAgentZone.isPresent()){
            log.warn("Exist This Associate With Agent ID: {} And Zone: {} ", optionalAgent.get().getId(), optionalZone.get().getName());
            throw new RestException("Exist This Associate");
        }

        final List<AgentZone> agentZoneList = agentZoneRepository.findAllZoneWithIdZoneAndAgents(idZone);

        if(!agentZoneList.isEmpty()){
            for(final AgentZone agentZoneItem: agentZoneList){
                agentZoneItem.setDeleted(Boolean.TRUE);
                agentZoneRepository.save(agentZoneItem);
            }
            publishRevokeAllZone(agentZoneList);
        }

        final AgentZone agentZone = new AgentZone();
        agentZone.setZone(optionalZone.get());
        agentZone.setAgent(optionalAgent.get());
        agentZone.setIsActive(Boolean.TRUE);
        agentZone.setDeleted(Boolean.FALSE);
        agentZoneRepository.save(agentZone);

        final AgentZoneEventDTO audit = new AgentZoneEventDTO();
        audit.setOperationBy(String.valueOf(jwtUtil.getIdProfileLogged()));
        audit.setOperationDate(LocalDateTime.now().toString());
        audit.setOperationAudit(OperationAuditEnum.ADD.name());
        audit.setZoneId(String.valueOf(agentZone.getZone().getId()));
        audit.setAgentId(String.valueOf(agentZone.getAgent().getId()));
        agentZonePublisher.publish(audit);

    }

    @Override
    @Transactional
    public void removeZoneToAgent(Long id, Long idAgent, Long idZone) {
        final Optional<AgentZone> optionalAgentZone = agentZoneRepository.findById(id);

        if(optionalAgentZone.isEmpty()){
            throw new RestException("AgentZone Not Found");
        }

        final Optional<Agent> optionalAgent = agentRepository.findById(idAgent);
        if(optionalAgent.isEmpty()){
            throw new RestException("Agent Not Found");
        }

        final Optional<Zone> optionalZone = zoneRepository.findById(idZone);

        if(optionalZone.isEmpty()){
            throw new RestException("Zone Not Found");
        }

        final Optional<AgentZone> optionalAgentZoneExist = agentZoneRepository.findAgentZoneWithSameData(
                idAgent, idZone
        );

        if(optionalAgentZoneExist.isPresent()){
            if(optionalAgentZoneExist.get().getId().equals(id)){

                optionalAgentZoneExist.get().setDeleted(Boolean.TRUE);
                agentZoneRepository.save(optionalAgentZoneExist.get());

                final AgentZoneEventDTO audit = new AgentZoneEventDTO();
                audit.setOperationBy(String.valueOf(jwtUtil.getIdProfileLogged()));
                audit.setOperationDate(LocalDateTime.now().toString());
                audit.setOperationAudit(OperationAuditEnum.REVOKE.name());
                audit.setZoneId(String.valueOf(optionalAgentZoneExist.get().getZone().getId()));
                audit.setAgentId(String.valueOf(optionalAgentZoneExist.get().getAgent().getId()));
                agentZonePublisher.publish(audit);
            }else{
                log.error("ATTENTION: Exist Another Associate For AgentZone , Agent: {} - Zone: {}", optionalAgent.get().getId(), optionalZone.get().getId());
                throw new RestException("Exist another associate to AgentZone");
            }
        }else{
            throw new RestException("Not Exist This Associate");
        }
    }

    @Override
    @Transactional
    public void removeZoneToAgent(Long idAgent, Long idZone) {

        final Optional<Agent> optionalAgent = agentRepository.findById(idAgent);
        if(optionalAgent.isEmpty()){
            throw new RestException("Agent Not Found");
        }

        final Optional<Zone> optionalZone = zoneRepository.findById(idZone);

        if(optionalZone.isEmpty()){
            throw new RestException("Zone Not Found");
        }

        final Optional<AgentZone> optionalAgentZoneExist = agentZoneRepository.findAgentZoneWithSameData(
                idAgent, idZone
        );

        if(optionalAgentZoneExist.isPresent()){
            optionalAgentZoneExist.get().setDeleted(Boolean.TRUE);
            agentZoneRepository.save(optionalAgentZoneExist.get());

            final AgentZoneEventDTO audit = new AgentZoneEventDTO();
            audit.setOperationBy(String.valueOf(jwtUtil.getIdProfileLogged()));
            audit.setOperationDate(LocalDateTime.now().toString());
            audit.setOperationAudit(OperationAuditEnum.REVOKE.name());
            audit.setZoneId(String.valueOf(optionalAgentZoneExist.get().getZone().getId()));
            audit.setZoneId(String.valueOf(optionalAgentZoneExist.get().getAgent().getId()));
            agentZonePublisher.publish(audit);
        }else{
            throw new RestException("Not Exist This Associate");
        }
    }


    private void publishRevokeAllZone(final List<AgentZone> agentZones){
        final LocalDateTime now = LocalDateTime.now();
        for(final AgentZone agentZone: agentZones){
            final AgentZoneEventDTO audit = new AgentZoneEventDTO();
            audit.setOperationBy(String.valueOf(jwtUtil.getIdProfileLogged()));
            audit.setOperationDate(now.toString());
            audit.setOperationAudit(OperationAuditEnum.REVOKE.name());
            audit.setZoneId(String.valueOf(agentZone.getZone().getId()));
            audit.setAgentId(String.valueOf(agentZone.getAgent().getId()));
            agentZonePublisher.publish(audit);
        }
    }



	@Override
	public List<AgentConfigurationVoyageResponse> listAgentConfigurationVoyage(Long agentId) {
		
		final List<ConfigurationVoyage> listConfigurationVoyages = configurationVoyageRepository.findAllConfigurationVoyageFromAgentId(agentId);
		
		if(!listConfigurationVoyages.isEmpty()) {
			final List<AgentConfigurationVoyageResponse> response = new ArrayList<AgentConfigurationVoyageResponse>();
			for(final ConfigurationVoyage item: listConfigurationVoyages) {
				response.add(
						configurationVoyageMapper.entityToListConfigurationVoyage(
								item, 
								configurationVoyageZoneRepository.findAllConfigurationVoyageZoneConfVoyageId(item.getId()
										)
								)
						);
			}
			return response;
		}
		
		return new ArrayList<AgentConfigurationVoyageResponse>();
	}



	@Override
	public List<VoyageOperationResponse> findAllOperationVoyageFromAgentId(Long agentId, final Integer offset, final Integer limit) {
		
		final List<VoyageCompany> findAllVoyageCompaniesFromAgent = voyageCompanyRepository.findAllVoyageCompanyFromAgentId(agentId, MethodUtils.getPagination(offset, limit));
		
		if(findAllVoyageCompaniesFromAgent.isEmpty()) {
			return new ArrayList<VoyageOperationResponse>();
		}
		final List<VoyageOperationResponse> response = new ArrayList<VoyageOperationResponse>();
		for(final VoyageCompany voyageCompany: findAllVoyageCompaniesFromAgent) {
			final List<VoyageCompanyOperation> operations = voyageCompanyOperationRepository.findAllOperationFromVoyageCompanyId(voyageCompany.getId());
			if(!operations.isEmpty()) {
				for(final VoyageCompanyOperation op: operations) {
					
				}
			}
		}
		
		return response;
	}

	
	private void createVoyage(final Agent agent, final List<Zone> zones) {
		if(!zones.isEmpty()) {
			for(final Zone zone: zones) {
				Voyage voyage = new Voyage();
				voyage.setAgent(agent);
				voyage.setZone(zone);
				
				voyage.setCreatedAt(LocalDateTime.now());
				voyage.setStartDate(LocalDate.now());
				voyage.setEndDate(MethodUtils.getEndOfWorkWeek(voyage.getStartDate()));
				voyage.setCreatedBy(jwtUtil.getUsernameLogged());
				voyage.setIsFinished(Boolean.FALSE);
				voyage = voyageRepository.save(voyage);
				
				final String code = "V-" + String.format("%04d", voyage.getId());
				voyage.setCode(code);
				
				voyageRepository.save(voyage);
				
				final List<ZoneCompany> findAllZoneCompany = zoneCompanyRepository.findAllCompanyFromZone(zone.getId());
				if(!findAllZoneCompany.isEmpty()) {
					for(final ZoneCompany zoneCompany: findAllZoneCompany) {
						VoyageCompany voyageCompany = new VoyageCompany();
						voyageCompany.setAgent(agent);
						voyageCompany.setCompany(zoneCompany.getCompany());
						voyageCompany.setCreatedAt(LocalDateTime.now());
						voyageCompany.setIsCompleted(Boolean.FALSE);
						voyageCompany.setVoyage(voyage);
						voyageCompany.setCreatedBy(jwtUtil.getUsernameLogged());
						voyageCompanyRepository.save(voyageCompany);
					}
				}
				
			}
		}
	}

	@Override
	@Transactional
	public AgentCurrentVoyageResponse currentVoyage(Long idAgent) {
		
		final Optional<Agent> optionalAgent = agentRepository.findById(idAgent);
		
		if(optionalAgent.isEmpty()) {
			throw new RestException("Agent Not Found"); 
		}
		
		final List<AgentCurrentVoyage> findCurrentVoyages = agentCurrentVoyageRepository.findCurrentVoyageFromAgentId(idAgent);
		if(findCurrentVoyages.isEmpty()) {
			throw new RestException("Not Exist Current Voyage For This Agent"); 
		}
		
		final AgentCurrentVoyage currentVoyage = MethodUtils.firstElement(findCurrentVoyages);
		
		final List<ConfigurationVoyage> findConfigurationVoyages = configurationVoyageRepository.findAllConfigurationVoyageFromAgentIdAndWeek(idAgent, currentVoyage.getVoyageNumber());
		if(findConfigurationVoyages.isEmpty()) {
			throw new RestException("Not Exist Configuration Voyage For This Agent"); 
		}
		final ConfigurationVoyage configurationVoyage = MethodUtils.firstElement(findConfigurationVoyages);
		
		final List<Zone> zoneList = new ArrayList<Zone>();
		
		final AgentCurrentVoyageResponse response = new AgentCurrentVoyageResponse();
		response.setZones(new ArrayList<ZoneResponse>());
		
		response.setCurrentVoyage(currentVoyage.getVoyageNumber());
		response.setId(currentVoyage.getId());
		
		final List<ConfigurationVoyageZone> configurationVoyageZones = configurationVoyageZoneRepository.findAllConfigurationVoyageZoneConfVoyageId(configurationVoyage.getId());
		if(configurationVoyageZones.isEmpty()) {
			log.warn("Not Exist Zones For This Configuration Voyage");
			return response;
		}else {
			for(final ConfigurationVoyageZone configurationVoyageZone: configurationVoyageZones) {
				zoneList.add(configurationVoyageZone.getZone());
				final String city = configurationVoyageZone.getZone().getCitta() != null ? configurationVoyageZone.getZone().getCitta().getNome() : "N/A";
				response.getZones().add(
						new ZoneResponse(configurationVoyageZone.getZone().getId(), configurationVoyageZone.getZone().getName(),city)
						);
			}
		}
		
		final List<Voyage> findAllvoyage = voyageRepository.findPresentVoyageFromAgentId(idAgent);
		if(findAllvoyage.isEmpty()) {
			//Creo i viaggi per far funzionare la parte operazioni
			createVoyage(optionalAgent.get(), zoneList);
		}
		
		//GESTIONE CLIENTI EXTRA VIAGGI
		final Voyage voyage = MethodUtils.firstElement(findAllvoyage);
		final List<VoyageCompany> findExternalVoyageCompany = voyageCompanyRepository.findAllVoyageCompanyFromVoyageIdAndExternal(voyage.getId());
		if(!findExternalVoyageCompany.isEmpty()) {
			for(final VoyageCompany vce: findExternalVoyageCompany) {
				final List<ZoneCompany> zonesCompany = zoneCompanyRepository.findZoneFromCompany(vce.getCompany().getId());
				if(!zonesCompany.isEmpty()) {
					final ZoneCompany zoneCompany = MethodUtils.firstElement(zonesCompany);
					final String city = zoneCompany.getZone().getCitta() != null ? zoneCompany.getZone().getCitta().getNome() : "N/A";
					response.getZones().add(
							new ZoneResponse(zoneCompany.getZone().getId(), zoneCompany.getZone().getName(),city)
							);
				}
			}
		}
		return response;
	}



	@Override
	@Transactional
	public void closeCurrentVoyage(Long idAgent) {
		final Optional<Agent> optionalAgent = agentRepository.findById(idAgent);
		
		if(optionalAgent.isEmpty()) {
			throw new RestException("Agent Not Found"); 
		}
		
		final List<AgentCurrentVoyage> findCurrentVoyages = agentCurrentVoyageRepository.findCurrentVoyageFromAgentId(idAgent);
		if(findCurrentVoyages.isEmpty()) {
			throw new RestException("Not Exist Current Voyage For This Agent"); 
		}
		
		final AgentCurrentVoyage currentVoyage = MethodUtils.firstElement(findCurrentVoyages);
		
		Integer nextVoyage = currentVoyage.getVoyageNumber();
		
		final List<ConfigurationVoyage> findConfigurationVoyagesFromAgent = configurationVoyageRepository.findAllConfigurationVoyageFromAgentId(idAgent);
		
		Boolean existNextVoyage = false;
		
		for(final ConfigurationVoyage configurationVoyage: findConfigurationVoyagesFromAgent) {
			nextVoyage = currentVoyage.getVoyageNumber() + 1;
			if(configurationVoyage.getWeek().equals(nextVoyage)) {
				if(!existNextVoyage) {
					existNextVoyage = true;
					currentVoyage.setVoyageNumber(nextVoyage);
					agentCurrentVoyageRepository.save(currentVoyage);
				}
			}
		}
		
		//Chiudo il Viaggio
		final List<Voyage> findVoyages = voyageRepository.findPresentVoyageFromAgentId(idAgent);
		final Voyage voyage = MethodUtils.firstElement(findVoyages);
		voyage.setIsFinished(Boolean.TRUE);
		
		voyageRepository.save(voyage);
		
	}



	@Override
	public List<AgentProductResponse> findAllProductsFromAgentId(Long idAgent) {
		final Optional<Agent> optionalAgent = agentRepository.findById(idAgent);
		
		if(optionalAgent.isEmpty()) {
			throw new RestException("Agent Not Found"); 
		}
		final List<AgentProductResponse> response = new ArrayList<AgentProductResponse>();

		//TODO: Qui non deve tornare i prodotti cosi ma quelli caricati durante il carico dal magazzino
		
		final List<ProductResponse> productsFromClient = productClient.getProducts();
		
		if(productsFromClient.isEmpty()) {
			return response;
		}
		
		for(final ProductResponse productResponse: productsFromClient) {
			final AgentProductResponse agentProductResponse = new AgentProductResponse();
			agentProductResponse.setProductCode(productResponse.getCode());
			agentProductResponse.setProductId(productResponse.getId());
			agentProductResponse.setProductName(productResponse.getName());
			agentProductResponse.setProductDescription(productResponse.getDescription());
			agentProductResponse.setQuantity(productResponse.getQuantity());
			response.add(agentProductResponse);
		}
		
		
		return response;
	}



	private VoyageCompany findVoyageFromCompany(final Long idCompany) {
		List<VoyageCompany> voyageCompanies = new ArrayList<VoyageCompany>();
		final Optional<Agent> optionalAgent = agentRepository.findAgentFromUserId(jwtUtil.getIdProfileLogged());
		if(optionalAgent.isPresent()) {
			voyageCompanies = voyageCompanyRepository
					.findAllVoyageCompanyFromCompanyIdAndAgent(
							idCompany, optionalAgent.get().getId());
		}else {
			voyageCompanies = voyageCompanyRepository
					.findAllVoyageCompanyFromCompanyId(
							idCompany);
		}
		
		if(!voyageCompanies.isEmpty()) {
			return MethodUtils.firstElement(voyageCompanies);
		}
		return null;
	}
	
	@Override
	public VoyageCustomerStatusAgentResponse currentVoyageCustomer(Long idAgent, Long zoneId) {

		final Optional<Agent> optionalAgent = agentRepository.findById(idAgent);
		
		if(optionalAgent.isEmpty()) {
			throw new RestException("Agent Not Found"); 
		}
		
		final List<AgentCurrentVoyage> findCurrentVoyages = agentCurrentVoyageRepository.findCurrentVoyageFromAgentId(idAgent);
		if(findCurrentVoyages.isEmpty()) {
			throw new RestException("Not Exist Current Voyage For This Agent"); 
		}
		
		final AgentCurrentVoyage currentVoyage = MethodUtils.firstElement(findCurrentVoyages);
		
		final List<ConfigurationVoyage> findConfigurationVoyages = configurationVoyageRepository.findAllConfigurationVoyageFromAgentIdAndWeek(idAgent, currentVoyage.getVoyageNumber());
		if(findConfigurationVoyages.isEmpty()) {
			throw new RestException("Not Exist Configuration Voyage For This Agent"); 
		}
		
		final ConfigurationVoyage configurationVoyage = MethodUtils.firstElement(findConfigurationVoyages);
		
		final List<ConfigurationVoyageZone> configurationVoyageZones = configurationVoyageZoneRepository.findAllConfigurationVoyageZoneConfVoyageIdAndZoneId(configurationVoyage.getId(), zoneId);
		
		final ConfigurationVoyageZone configurationVoyageZone = MethodUtils.firstElement(configurationVoyageZones);
		
        final VoyageCustomerStatusAgentResponse voyageCustomerStatusAgentResponse = new VoyageCustomerStatusAgentResponse();
		
		final List<ZoneCompany> zoneCompanies = zoneCompanyRepository.findAllCompanyFromZone(configurationVoyageZone.getZone().getId());
		
		if(!zoneCompanies.isEmpty()) {
			final List<VoyageClientResponse> toVisit = new ArrayList<VoyageClientResponse>();
			final List<VoyageClientResponse> toVisited = new ArrayList<VoyageClientResponse>();
			
			
			for(final ZoneCompany zc: zoneCompanies) {
				final VoyageClientResponse voyageClientResponse = new VoyageClientResponse();
				voyageClientResponse.setClientName(zc.getCompany().getName());
				voyageClientResponse.setClientId(zc.getCompany().getId());
				voyageClientResponse.setPiva(zc.getCompany().getPiva());
				voyageClientResponse.setTelephone(zc.getCompany().getTelephone());
				voyageClientResponse.setZoneName(configurationVoyageZone.getZone().getName());
				final List<CompanyAddress> findListAddress = companyAddressRepository.findAllCompanyAddressFromCompanyId(zc.getCompany().getId());
				if(!findListAddress.isEmpty()) {
					final CompanyAddress companyAddress = findListAddress.get(0);
					voyageClientResponse.setLat(companyAddress.getLat());
					voyageClientResponse.setLon(companyAddress.getLon());
					voyageClientResponse.setAddress(companyAddress.getAddress() != null ? companyAddress.getAddress().getAddress() : null);
				}
				
				
				final VoyageCompany voyageCompany = findVoyageFromCompany(zc.getCompany().getId());
				if(voyageCompany != null) {
					voyageClientResponse.setVoyageId(voyageCompany.getVoyage().getId());
				}
				
				final List<CompanyStockResponse> stockCompany = new ArrayList<CompanyStockResponse>();
				
				final List<CompanyStock> stockEntity = companyStockRepository.findAllStockFromCompanyId(zc.getCompany().getId());
				if(!stockEntity.isEmpty()) {
					for(final CompanyStock c: stockEntity) {
						final CompanyStockResponse companyStockResponse = new CompanyStockResponse();
						companyStockResponse.setCompanyId(c.getCompany().getId());
						final ProductResponse productResponse = productClient.getProductInfo(c.getProductIdExt());
						companyStockResponse.setProductCode(productResponse.getCode());
						companyStockResponse.setProductName(productResponse.getName());
						companyStockResponse.setQuantity(c.getQuantity());
						stockCompany.add(companyStockResponse);
						}
				}
				
				/*if(isVisitCarreiedOut(zc.getCompany(), optionalVoyage.get())) {
					toVisited.add(voyageClientResponse);
				}else {
					
				}*/
				voyageClientResponse.setStocks(stockCompany);
				toVisit.add(voyageClientResponse);
			}
			
			voyageCustomerStatusAgentResponse.setCustomersToVisit(toVisit);
			voyageCustomerStatusAgentResponse.setCustomersToVisited(toVisited);
			voyageCustomerStatusAgentResponse.setCounterCustomersToVisit(toVisit.size());
			voyageCustomerStatusAgentResponse.setCounterCustomersVisited(toVisited.size());
			
		}
		
		return voyageCustomerStatusAgentResponse;
		
	}


}
