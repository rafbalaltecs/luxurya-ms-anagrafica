package anagrafica.service.cache.impl;

import anagrafica.dto.shared.UserDataShared;
import anagrafica.service.cache.RedisService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

@Service
@Slf4j
public class RedisServiceImpl implements RedisService {
    private final RedisTemplate<String, Object> redisTemplate;
    private final ObjectMapper objectMapper;

    public RedisServiceImpl(RedisTemplate<String, Object> redisTemplate) {
        this.redisTemplate = redisTemplate;
        this.objectMapper = new ObjectMapper();
        // Per gestire LocalDateTime
        this.objectMapper.registerModule(new JavaTimeModule());
        this.objectMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
    }

    @Override
    public void save(String key, Object value) {
        redisTemplate.opsForValue().set(key, value);
    }

    @Override
    public void saveWithExpiration(String key, Object value, long timeout, TimeUnit unit) {
        redisTemplate.opsForValue().set(key, value, timeout, unit);
    }

    @Override
    public Object get(String key) {
        return redisTemplate.opsForValue().get(key);
    }

    @Override
    public boolean exists(String key) {
        return redisTemplate.hasKey(key);
    }

    @Override
    public boolean delete(String key) {
        return redisTemplate.delete(key);
    }

    @Override
    public void saveUserData(String key, UserDataShared userDataShared) {
        try {
            // Serializza in JSON pulito (senza @class e annotazioni Java)
            String cleanJson = objectMapper.writeValueAsString(userDataShared);
            saveWithExpiration(key, cleanJson,3600, TimeUnit.SECONDS);
        } catch (Exception e) {
            throw new RuntimeException("Errore nel salvare userData su Redis", e);
        }
    }

    @Scheduled(fixedRate = 300000) // ogni 5 minuti
    public void keepAlive() {
        try {
            redisTemplate.execute((RedisCallback<Object>) connection -> {
                connection.ping();
                return null;
            });
            log.info("Connect Ping To Redis successful");
        }catch (Exception e){
            log.error("Error Connect Ping To Redis {}", e.getMessage());
        }

    }
}
