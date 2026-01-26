package anagrafica.publisher;

import anagrafica.dto.event.CompanyZoneEventDTO;
import anagrafica.utils.RedisTopics;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

@Service
public class CompanyZonePublisher extends Publisher<CompanyZoneEventDTO>{
    public CompanyZonePublisher(RedisTemplate<String, Object> redisTemplate) {
        super(redisTemplate, RedisTopics.COMPANY_ZONE);
    }
}
