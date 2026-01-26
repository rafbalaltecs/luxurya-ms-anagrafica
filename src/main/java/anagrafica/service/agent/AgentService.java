package anagrafica.service.agent;

import anagrafica.dto.agent.AgentRequest;
import anagrafica.dto.agent.AgentResponse;
import anagrafica.dto.event.AgentZoneEventDTO;
import anagrafica.dto.zone.ZoneResponse;

import java.util.List;

public interface AgentService {
    List<AgentResponse> findAll(final Integer offset, final Integer limit);
    AgentResponse create(final AgentRequest request);
    AgentResponse update(final Long id, final AgentRequest request);
    void delete(final Long id);

    List<ZoneResponse> findZonesFromAgent(final Long agentId);
    void auditAgentZone(final AgentZoneEventDTO eventDTO);
}
