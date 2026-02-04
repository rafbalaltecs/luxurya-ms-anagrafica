package anagrafica.service.user;

import anagrafica.dto.user.UserRequest;
import anagrafica.dto.user.UserResponse;

import java.time.LocalDateTime;
import java.util.List;

public interface UserService {
    List<UserResponse> findAll(final Integer offset, final Integer limit);
    UserResponse update(final Long id, final UserRequest request);

    //Update LastLogin
    void lastLoginUpdate(final String email, final LocalDateTime eventDate);
}
