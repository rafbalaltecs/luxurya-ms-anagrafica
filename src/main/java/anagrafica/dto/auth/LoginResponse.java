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
    private Long idAgent;
    private Boolean isAgent;
    private String name;
    private String surname;
}
