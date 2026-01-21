package anagrafica.dto.auth;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class LoginResponse {
    private String token;
    private LocalDateTime generatedDate;
    private LocalDateTime expireDate;
}
