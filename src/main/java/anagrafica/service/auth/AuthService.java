package anagrafica.service.auth;

import anagrafica.dto.auth.LoginRequest;
import anagrafica.dto.auth.LoginResponse;

public interface AuthService {
    LoginResponse login(final LoginRequest loginRequest);
    String encodePassword(final String pwd);
}
