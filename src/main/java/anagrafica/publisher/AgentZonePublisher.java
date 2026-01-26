package anagrafica.publisher;

import anagrafica.dto.event.AgentZoneEventDTO;
import anagrafica.utils.RedisTopics;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

@Service
public class AgentZonePublisher extends Publisher<AgentZoneEventDTO>{
    public AgentZonePublisher(RedisTemplate<String, Object> redisTemplate) {
        super(redisTemplate, RedisTopics.AGENT_ZONE);
    }
}
