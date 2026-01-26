package anagrafica.publisher;

import anagrafica.utils.RedisTopics;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.listener.ChannelTopic;

public abstract class Publisher<T> {

    private final RedisTemplate<String, Object> redisTemplate;
    private final ChannelTopic topic;

    protected Publisher(RedisTemplate<String, Object> redisTemplate, String redisTopic) {
        this.redisTemplate = redisTemplate;
        this.topic = new ChannelTopic(redisTopic);
    }

    public void publish(T message) {
        redisTemplate.convertAndSend(topic.getTopic(), message);
    }
}
