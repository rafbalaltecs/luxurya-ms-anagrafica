package anagrafica.service.voyage.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import anagrafica.dto.agent.AgentConfigurationVoyageResponse;
import anagrafica.dto.voyage.VoyageConfigurationItemResponse;
import anagrafica.entity.ConfigurationVoyage;
import anagrafica.entity.ConfigurationVoyageZone;
import anagrafica.entity.ZoneCompany;
import anagrafica.repository.zone.ZoneCompanyRepository;

@Component
public class ConfigurationVoyageMapper {
	
	private final ZoneCompanyRepository zoneCompanyRepository;
	
	public ConfigurationVoyageMapper(ZoneCompanyRepository zoneCompanyRepository) {
		this.zoneCompanyRepository = zoneCompanyRepository;
	}

	public AgentConfigurationVoyageResponse entityToListConfigurationVoyage(
			final ConfigurationVoyage configurationVoyage,
			final List<ConfigurationVoyageZone> listConfigurationVoyageZone
			) {
		if(configurationVoyage != null) {
			final AgentConfigurationVoyageResponse response = new AgentConfigurationVoyageResponse();
			response.setId(configurationVoyage.getId());
			response.setAgentId(configurationVoyage.getAgent().getId());
			response.setAgentName(configurationVoyage.getAgent().getName());
			response.setAgentSurname(configurationVoyage.getAgent().getSurname());
			
			response.setWeek(configurationVoyage.getWeek());
			if(listConfigurationVoyageZone != null && !listConfigurationVoyageZone.isEmpty()) {
				final List<VoyageConfigurationItemResponse> items = new ArrayList<VoyageConfigurationItemResponse>();
				for(final ConfigurationVoyageZone conf: listConfigurationVoyageZone) {
					
					final VoyageConfigurationItemResponse item = new VoyageConfigurationItemResponse();
					item.setZoneId(conf.getZone().getId());
					item.setZoneName(conf.getZone().getName());
					item.setProvinceName(conf.getZone().getCitta() != null ? conf.getZone().getCitta().getProvincia().getSigla() : "N/A");
					item.setTotalClients(0);
					
					final List<ZoneCompany> zoneCompanies = zoneCompanyRepository.findAllCompanyFromZone(item.getZoneId());
					
					if(!zoneCompanies.isEmpty()) {
						item.setTotalClients(zoneCompanies.size());
					}
					
					items.add(item);
				}
				response.setItems(items);
			}
			return response;
		}
		
		return null;
	}
	
}
