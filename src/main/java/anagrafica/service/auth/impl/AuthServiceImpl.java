package anagrafica.service.auth.impl;

import anagrafica.dto.auth.LoginRequest;
import anagrafica.dto.auth.LoginResponse;
import anagrafica.dto.event.LoginEventDTO;
import anagrafica.entity.User;
import anagrafica.exception.RestException;
import anagrafica.publisher.LoginPublisher;
import anagrafica.repository.user.UserRepository;
import anagrafica.service.auth.AuthService;
import anagrafica.utils.JwtUtil;
import anagrafica.utils.PasswordUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Service
@Slf4j
public class AuthServiceImpl implements AuthService {

    private final JwtUtil jwtUtil;
    private final UserRepository userRepository;

    private final AuthMapper authMapper;

    private final LoginPublisher loginPublisher;

    public AuthServiceImpl(JwtUtil jwtUtil, UserRepository userRepository, AuthMapper authMapper, LoginPublisher loginPublisher) {
        this.jwtUtil = jwtUtil;
        this.userRepository = userRepository;
        this.authMapper = authMapper;
        this.loginPublisher = loginPublisher;
    }

    @Override
    public LoginResponse login(LoginRequest loginRequest) {
        final Optional<User> optionalUser = userRepository.findByEmail(loginRequest.getUsername());
        if(optionalUser.isEmpty()){
            throw new RestException("User Not Found");
        }

        if(optionalUser.get().getIsDisabled()){
            throw new RestException("User Is Disabled");
        }

        final String pwd = PasswordUtils.encodePassword(loginRequest.getPassword());

        if(StringUtils.isEmpty(pwd)){
            throw new RestException("Generation Password Error");
        }

        if(!pwd.equals(optionalUser.get().getPassword())){
            throw new RestException("Password Not Valid");
        }

        final Map<String, Object> claims = new HashMap<>();
        claims.put("username", loginRequest.getUsername());

        final LoginResponse authResponse = new LoginResponse();
        authResponse.setToken(jwtUtil.generateToken(loginRequest.getUsername(), claims));
        authResponse.setUser(authMapper.entityToResponse(optionalUser.get()));

        loginPublisher.publish(new LoginEventDTO(optionalUser.get().getEmail(), LocalDateTime.now().toString()));

        return authResponse ;
    }

    @Override
    public String encodePassword(String pwd) {
        return PasswordUtils.encodePassword(pwd);
    }
}
