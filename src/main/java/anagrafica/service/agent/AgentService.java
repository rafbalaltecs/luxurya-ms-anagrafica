package anagrafica.service.agent;

import java.util.List;

import anagrafica.dto.agent.AgentConfigurationVoyageResponse;
import anagrafica.dto.agent.AgentCurrentVoyageExternalResponse;
import anagrafica.dto.agent.AgentCurrentVoyageResponse;
import anagrafica.dto.agent.AgentProductResponse;
import anagrafica.dto.agent.AgentRequest;
import anagrafica.dto.agent.AgentResponse;
import anagrafica.dto.event.AgentZoneEventDTO;
import anagrafica.dto.ext.ProductResponse;
import anagrafica.dto.voyage.VoyageCustomerStatusAgentResponse;
import anagrafica.dto.voyage.VoyageOperationResponse;
import anagrafica.dto.zone.ZoneResponse;

public interface AgentService {
    List<AgentResponse> findAll(final Integer offset, final Integer limit);
    AgentResponse create(final AgentRequest request);
    AgentResponse update(final Long id, final AgentRequest request);
    void delete(final Long id);

    List<ZoneResponse> findZonesFromAgent(final Long agentId);
    void auditAgentZone(final AgentZoneEventDTO eventDTO);

    void addZoneToAgent(final Long idAgent , final Long idZone);
    void removeZoneToAgent(final Long id, final Long idAgent, final Long idZone);

    void removeZoneToAgent(final Long idAgent, final Long idZone);
    
    List<AgentConfigurationVoyageResponse> listAgentConfigurationVoyage(final Long agentId);
    List<VoyageOperationResponse> findAllOperationVoyageFromAgentId(final Long agentId, final Integer offet, final Integer limit);
    
    AgentCurrentVoyageResponse currentVoyage(final Long idAgent);
    
    AgentCurrentVoyageExternalResponse currentVoyageExternal(final Long idAgent);
    
    VoyageCustomerStatusAgentResponse currentVoyageCustomer(final Long idAgent, final Long zoneId);
    
    void closeCurrentVoyage(final Long idAgent);
    
    List<AgentProductResponse> findAllProductsFromAgentId(final Long idAgent);
}
