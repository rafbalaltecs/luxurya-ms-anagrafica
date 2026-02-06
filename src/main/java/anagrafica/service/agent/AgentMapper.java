package anagrafica.service.agent;

import anagrafica.dto.agent.AgentResponse;
import anagrafica.entity.Agent;
import org.springframework.stereotype.Component;

@Component
public class AgentMapper {
    public AgentResponse toResponseLight(final Agent agent){
        if(agent != null){
            return new AgentResponse(
                    agent.getId(),
                    agent.getName(),
                    agent.getSurname(),
                    agent.getTelephone(),
                    null,
                    null
            );
        }
        return null;
    }
}
