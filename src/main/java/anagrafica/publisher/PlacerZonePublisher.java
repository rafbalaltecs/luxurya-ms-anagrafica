package anagrafica.publisher;

import anagrafica.dto.event.PlacerZoneEventDTO;
import anagrafica.utils.RedisTopics;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

@Service
public class PlacerZonePublisher extends Publisher<PlacerZoneEventDTO> {
    public PlacerZonePublisher(RedisTemplate<String, Object> redisTemplate) {
        super(redisTemplate, RedisTopics.AGENT_ZONE);
    }
}
