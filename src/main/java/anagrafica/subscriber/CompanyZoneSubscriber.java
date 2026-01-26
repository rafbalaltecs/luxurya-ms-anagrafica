package anagrafica.subscriber;


import anagrafica.dto.event.AgentZoneEventDTO;
import anagrafica.dto.event.CompanyZoneEventDTO;
import anagrafica.service.agent.AgentService;
import anagrafica.service.zone.ZoneService;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.connection.MessageListener;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
@Slf4j
public class CompanyZoneSubscriber implements MessageListener {

    private final ObjectMapper objectMapper;
    private final ZoneService zoneService;

    public CompanyZoneSubscriber(ObjectMapper objectMapper, ZoneService zoneService) {
        this.objectMapper = objectMapper;
        this.zoneService = zoneService;
    }

    @Override
    public void onMessage(Message message, byte[] pattern) {
        final LocalDateTime now = LocalDateTime.now();
        try{
            final CompanyZoneEventDTO companyZoneEventDTO = objectMapper.readValue(
                    message.getBody(),
                    CompanyZoneEventDTO.class
            );
            zoneService.audit(companyZoneEventDTO);
        }catch (Exception e){
            log.error("Message Date: {}", now);
            log.error(e.getMessage());
        }
    }
}
