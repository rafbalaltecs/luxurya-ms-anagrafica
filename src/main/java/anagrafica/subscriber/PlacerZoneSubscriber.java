package anagrafica.subscriber;

import anagrafica.dto.event.CompanyZoneEventDTO;
import anagrafica.dto.event.PlacerZoneEventDTO;
import anagrafica.service.placer.PlacerService;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.connection.MessageListener;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@Slf4j
public class PlacerZoneSubscriber implements MessageListener {

    private final ObjectMapper objectMapper;
    private final PlacerService placerService;

    public PlacerZoneSubscriber(ObjectMapper objectMapper, PlacerService placerService) {
        this.objectMapper = objectMapper;
        this.placerService = placerService;
    }

    @Override
    public void onMessage(Message message, byte[] pattern) {
        final LocalDateTime now = LocalDateTime.now();
        try {
            final PlacerZoneEventDTO placerZoneEventDTO = objectMapper.readValue(
                    message.getBody(),
                    PlacerZoneEventDTO.class
            );
            placerService.auditAgentZone(placerZoneEventDTO);
        }catch (Exception e){
            log.error("Message Date: {}", now);
            log.error(e.getMessage());
        }
    }
}
