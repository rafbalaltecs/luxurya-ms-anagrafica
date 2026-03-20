package anagrafica.service.business;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import anagrafica.entity.Agent;
import anagrafica.entity.AgentCurrentVoyage;
import anagrafica.entity.ConfigurationVoyage;
import anagrafica.entity.ConfigurationVoyageZone;
import anagrafica.exception.BusinessError;
import anagrafica.exception.BusinessException;
import anagrafica.repository.agent.AgentCurrentVoyageRepository;
import anagrafica.repository.agent.AgentRepository;
import anagrafica.repository.voyage.ConfigurationVoyageRepository;
import anagrafica.repository.voyage.ConfigurationVoyageZoneRepository;
import anagrafica.repository.voyage.VoyageRepository;
import anagrafica.utils.JwtUtil;
import anagrafica.utils.MethodUtils;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class AgentBusinessFacadeService {
	private final AgentRepository agentRepository;
	private final ConfigurationVoyageRepository configurationVoyageRepository;
	private final ConfigurationVoyageZoneRepository configurationVoyageZoneRepository;
	private final VoyageRepository voyageRepository;
	private final AgentCurrentVoyageRepository agentCurrentVoyageRepository;
	private final JwtUtil jwtUtil;
	
	public AgentBusinessFacadeService(
			AgentRepository agentRepository,
			ConfigurationVoyageRepository configurationVoyageRepository,
			ConfigurationVoyageZoneRepository configurationVoyageZoneRepository,
			VoyageRepository voyageRepository,
			AgentCurrentVoyageRepository agentCurrentVoyageRepository,
			JwtUtil jwtUtil
			) {
		
		this.agentRepository = agentRepository;
		this.configurationVoyageRepository = configurationVoyageRepository;
		this.configurationVoyageZoneRepository = configurationVoyageZoneRepository;
		this.voyageRepository = voyageRepository;
		this.agentCurrentVoyageRepository = agentCurrentVoyageRepository;
		this.jwtUtil = jwtUtil;
	}
	
	private Long getAgentId() {
		final Optional<Agent> currentAgent = agentRepository.findAgentFromUserId(jwtUtil.getIdProfileLogged());
		if(currentAgent.isEmpty()) {
			throw BusinessError.USER_NOT_AGENT.toException();
		}
		return currentAgent.get().getId();
	}
	
	private Agent getAgent() {
		final Optional<Agent> currentAgent = agentRepository.findAgentFromUserId(jwtUtil.getIdProfileLogged());
		if(currentAgent.isEmpty()) {
			throw BusinessError.USER_NOT_AGENT.toException();
		}
		return currentAgent.get();
	}
	
	public ConfigurationVoyage getConfigurationVoyageLogged(final Integer week) {
		final List<ConfigurationVoyage> configurationVoyages = configurationVoyageRepository.findAllConfigurationVoyageFromAgentIdAndWeek(getAgentId(), week);
		if(!configurationVoyages.isEmpty()) {
			return MethodUtils.firstElement(configurationVoyages);
		}
		return null;
	}
	
	public List<ConfigurationVoyageZone> getConfigurationVoyageZonesLogged(final Integer week){
		final List<ConfigurationVoyage> configurationVoyages = configurationVoyageRepository.findAllConfigurationVoyageFromAgentIdAndWeek(getAgentId(), week);
		if(!configurationVoyages.isEmpty()) {
			 final ConfigurationVoyage conf = MethodUtils.firstElement(configurationVoyages);
			 return configurationVoyageZoneRepository.findAllConfigurationVoyageZoneConfVoyageId(conf.getId());
		}
		return new ArrayList<>();
	}
	
	@Transactional
	public AgentCurrentVoyage getCurrentVoyageLogged(Boolean persist) {
		
		if(!persist) {
			return getCurrentVoyageLogged();
		}
		
		try {
			return getCurrentVoyageLogged();
		}catch(BusinessException be) {
			AgentCurrentVoyage agentCurrentVoyage = new AgentCurrentVoyage();
			agentCurrentVoyage.setAgent(getAgent());
			agentCurrentVoyage.setDeleted(Boolean.FALSE);
			agentCurrentVoyage.setVoyageNumber(1);
			return agentCurrentVoyageRepository.save(agentCurrentVoyage);
		}
	}
	
	
	public AgentCurrentVoyage getCurrentVoyageLogged() {
		final List<AgentCurrentVoyage> agentCurrentVoyages = agentCurrentVoyageRepository.findCurrentVoyageFromAgentId(getAgentId());
		if(agentCurrentVoyages.isEmpty()) {
			throw BusinessError.NOT_EXIST_VOYAGE_FOR_THIS_AGENT.toException();
		}
		
		return MethodUtils.firstElement(agentCurrentVoyages);
	}
	
}
