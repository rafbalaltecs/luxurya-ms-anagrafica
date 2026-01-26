package anagrafica.subscriber;

import anagrafica.dto.event.AgentZoneEventDTO;
import anagrafica.dto.event.LoginEventDTO;
import anagrafica.service.agent.AgentService;
import anagrafica.service.user.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.connection.MessageListener;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
@Slf4j
public class AgentZoneSubscriber implements MessageListener {

    private final ObjectMapper objectMapper;
    private final AgentService agentService;

    public AgentZoneSubscriber(ObjectMapper objectMapper, AgentService agentService) {
        this.objectMapper = objectMapper;
        this.agentService = agentService;
    }

    @Override
    public void onMessage(Message message, byte[] pattern) {
        final LocalDateTime now = LocalDateTime.now();
        try{
            final AgentZoneEventDTO agentZoneEventDTO = objectMapper.readValue(
                    message.getBody(),
                    AgentZoneEventDTO.class
            );
            agentService.auditAgentZone(agentZoneEventDTO);
        }catch (Exception e){
            log.error("Message Date: {}", now);
            log.error(e.getMessage());
        }
    }
}
