package anagrafica.dto.auth;

import anagrafica.dto.user.UserResponse;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class LoginResponse {
    private String token;
    private UserResponse user;
    private LocalDateTime generatedDate;
    private LocalDateTime expireDate;
}
