package anagrafica.service.user;

import anagrafica.dto.user.UserRequest;
import anagrafica.dto.user.UserResponse;

import java.time.LocalDateTime;

public interface UserService {
    UserResponse findAll();
    UserResponse update(final Long id, final UserRequest request);

    //Update LastLogin
    void lastLoginUpdate(final String email, final LocalDateTime eventDate);
}
