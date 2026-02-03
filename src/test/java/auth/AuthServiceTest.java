package auth;

import anagrafica.dto.auth.LoginRequest;
import anagrafica.dto.auth.LoginResponse;
import anagrafica.dto.event.LoginEventDTO;
import anagrafica.dto.shared.UserDataShared;
import anagrafica.dto.user.UserResponse;
import anagrafica.entity.TypeUser;
import anagrafica.entity.User;
import anagrafica.publisher.LoginPublisher;
import anagrafica.repository.user.UserRepository;
import anagrafica.service.auth.impl.AuthMapper;
import anagrafica.service.auth.impl.AuthServiceImpl;
import anagrafica.service.cache.RedisService;
import anagrafica.utils.JwtUtil;
import anagrafica.utils.PasswordUtils;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;
import java.util.Set;

import static com.mongodb.assertions.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyMap;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class AuthServiceTest {

    @InjectMocks
    private AuthServiceImpl authService;

    @Mock
    private UserRepository userRepository;

    @Mock
    private AuthMapper authMapper;

    @Mock
    private JwtUtil jwtUtil;

    @Mock
    private RedisService redisService;

    @Mock
    private LoginPublisher loginPublisher;

    @Test
    void login_success() {

        LoginRequest request = new LoginRequest();
        request.setUsername("test@mail.com");
        request.setPassword("plainPwd");

        User user = new User();
        user.setId(1L);
        user.setEmail("test@mail.com");
        user.setIsDisabled(false);
        user.setPassword(PasswordUtils.encodePassword("plainPwd"));

        TypeUser adminType = new TypeUser();
        adminType.setCode("ADMIN");

        when(userRepository.findByEmail("test@mail.com"))
                .thenReturn(Optional.of(user));

        UserResponse userResponse = new UserResponse();
        userResponse.setTypeUsers(Set.of("ADMIN"));
        userResponse.setRoutes((Set.of("R1", "R2")));

        when(authMapper.entityToResponse(user)).thenReturn(userResponse);
        when(jwtUtil.generateToken(eq("test@mail.com"), anyMap()))
                .thenReturn("jwt-token");

        UserDataShared shared = new UserDataShared();
        when(authMapper.getUserDataShared(eq(user), eq("jwt-token")))
                .thenReturn(shared);

        LoginResponse response = authService.login(request);

        assertNotNull(response);
        assertEquals("jwt-token", response.getToken());
        assertNotNull(response.getUser());

    }

}

