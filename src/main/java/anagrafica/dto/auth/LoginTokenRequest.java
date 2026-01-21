package anagrafica.dto.auth;

import lombok.Data;

@Data
public class LoginTokenRequest {
    private String token;
    private String password;
}
