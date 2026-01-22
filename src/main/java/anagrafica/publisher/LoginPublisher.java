package anagrafica.publisher;

import anagrafica.utils.RedisTopics;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.listener.ChannelTopic;
import org.springframework.stereotype.Service;

@Service
public class LoginPublisher {
    private final RedisTemplate<String, Object> redisTemplate;
    private final ChannelTopic topic;

    public LoginPublisher(RedisTemplate<String, Object> redisTemplate) {
        this.redisTemplate = redisTemplate;
        this.topic = new ChannelTopic(RedisTopics.LOGIN);
    }

    public void publish(Object message) {
        redisTemplate.convertAndSend(topic.getTopic(), message);
    }
}
