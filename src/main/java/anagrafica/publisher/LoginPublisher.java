package anagrafica.publisher;

import anagrafica.dto.event.LoginEventDTO;
import anagrafica.utils.RedisTopics;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.listener.ChannelTopic;
import org.springframework.stereotype.Service;

@Service
public class LoginPublisher extends Publisher<LoginEventDTO> {
    public LoginPublisher(RedisTemplate<String, Object> redisTemplate) {
        super(redisTemplate, RedisTopics.LOGIN);
    }
}
