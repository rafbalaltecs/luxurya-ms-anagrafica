package anagrafica.subscriber;

import anagrafica.dto.event.LoginEventDTO;
import anagrafica.service.user.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.connection.MessageListener;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
@Slf4j
public class LoginSubscriber implements MessageListener {

    private final ObjectMapper objectMapper;
    private final UserService userService;

    public LoginSubscriber(ObjectMapper objectMapper, UserService userService) {
        this.objectMapper = objectMapper;
        this.userService = userService;
    }

    @Override
    public void onMessage(Message message, byte[] pattern) {
        final LocalDateTime now = LocalDateTime.now();
        try{
            final LoginEventDTO loginEventDTO = objectMapper.readValue(
                    message.getBody(),
                    LoginEventDTO.class
            );
            userService.lastLoginUpdate(loginEventDTO.getEmail(), LocalDateTime.parse(loginEventDTO.getEventDate()));
        }catch (Exception e){
            log.error("Message Date: {}", now);
            log.error(e.getMessage());
        }
    }
}
