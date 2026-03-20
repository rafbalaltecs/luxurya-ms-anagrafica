package anagrafica.service.auth;

import anagrafica.dto.auth.LoginRequest;
import anagrafica.dto.auth.LoginResponse;
import anagrafica.dto.auth.LoginTokenRequest;

public interface AuthService {
    LoginResponse login(final LoginRequest loginRequest);
    String encodePassword(final String pwd);
    LoginResponse loginWithToken(final LoginTokenRequest request);
}
