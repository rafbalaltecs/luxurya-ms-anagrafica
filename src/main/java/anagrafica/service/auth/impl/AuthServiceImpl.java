package anagrafica.service.auth.impl;

import anagrafica.dto.auth.LoginRequest;
import anagrafica.dto.auth.LoginResponse;
import anagrafica.dto.auth.LoginTokenRequest;
import anagrafica.dto.event.LoginEventDTO;
import anagrafica.dto.shared.UserDataShared;
import anagrafica.entity.Agent;
import anagrafica.entity.ExternalSystem;
import anagrafica.entity.User;
import anagrafica.exception.BusinessError;
import anagrafica.exception.RestException;
import anagrafica.publisher.LoginPublisher;
import anagrafica.repository.agent.AgentRepository;
import anagrafica.repository.externalsystem.ExternalSystemRepository;
import anagrafica.repository.user.UserRepository;
import anagrafica.service.auth.AuthService;
import anagrafica.service.cache.RedisService;
import anagrafica.utils.JwtUtil;
import anagrafica.utils.MethodUtils;
import anagrafica.utils.PasswordUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

@Service
@Slf4j
public class AuthServiceImpl implements AuthService {

    private final JwtUtil jwtUtil;
    private final RedisService redisService;
    private final UserRepository userRepository;

    private final AuthMapper authMapper;

    private final LoginPublisher loginPublisher;
    private final AgentRepository agentRepository;
    private final ExternalSystemRepository externalSystemRepository;

    public AuthServiceImpl(JwtUtil jwtUtil, RedisService redisService, 
    		UserRepository userRepository, 
    		AuthMapper authMapper, 
    		LoginPublisher loginPublisher, 
    		AgentRepository agentRepository,
    		ExternalSystemRepository externalSystemRepository,
    		ObjectMapper objectMapper) {
        this.jwtUtil = jwtUtil;
        this.redisService = redisService;
        this.userRepository = userRepository;
        this.authMapper = authMapper;
        this.loginPublisher = loginPublisher;
        this.agentRepository = agentRepository;
        this.externalSystemRepository = externalSystemRepository;
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
        authResponse.setUser(authMapper.entityToResponse(optionalUser.get()));

        final Boolean isAdmin = authResponse.getUser()
                .getTypeUsers()
                .stream()
                .anyMatch(type -> "ADMIN".equals(type));

        claims.put("isAdmin", isAdmin);
        claims.put("iduser", optionalUser.get().getId());
        claims.put("routes", compactRoutes(authResponse.getUser().getRoutes()));
        authResponse.setToken(jwtUtil.generateToken(loginRequest.getUsername(), claims));
        
        final Optional<Agent> optionalAgent = agentRepository.findAgentFromUserId(optionalUser.get().getId());
        if(optionalAgent.isPresent()) {
        	authResponse.setIdAgent(optionalAgent.get().getId());
        	authResponse.setIsAgent(Boolean.TRUE);
        	authResponse.setName(optionalAgent.get().getName());
        	authResponse.setSurname(optionalAgent.get().getSurname());
        }

        final UserDataShared userDataShared = authMapper.getUserDataShared(optionalUser.get(), authResponse.getToken());
        userDataShared.setRoutes(authResponse.getUser().getRoutes());

        redisService.saveUserData(authResponse.getToken(), userDataShared);
        loginPublisher.publish(new LoginEventDTO(optionalUser.get().getEmail(), LocalDateTime.now().toString()));

        return authResponse ;
    }

    private String compactRoutes(final Set<String> routes) {

        if (routes == null || routes.isEmpty()) {
            return "";
        }

        return routes.stream()
                .sorted()
                .collect(Collectors.joining("_"));
    }


    @Override
    public String encodePassword(String pwd) {
        return PasswordUtils.encodePassword(pwd);
    }

	@Override
	public LoginResponse loginWithToken(LoginTokenRequest request) {
		final Optional<User> optionalUser = userRepository.findByAuthToken(request.getToken());
		if(optionalUser.isEmpty()) {
			throw BusinessError.INVALID_AUTH_TOKEN.toExceptionEntity(request.getToken());
		}
		
		final List<ExternalSystem> findAllExternalSystem = externalSystemRepository.findByUserId(optionalUser.get().getId());
		if(findAllExternalSystem.isEmpty()) {
			throw BusinessError.INVALID_EXT_SYSTEM_USER.toException();
		}
		
		final ExternalSystem externalSystem = MethodUtils.firstElement(findAllExternalSystem);
		
		if(!externalSystem.getIsEnabled()) {
			throw BusinessError.EXTERNAL_SYSYEM_NOT_ENABLED.toException();
		}
		
		final LoginResponse authResponse = new LoginResponse();
		 
		final Map<String, Object> claims = new HashMap<>();
		claims.put("isExternalSystem", Boolean.TRUE);
		claims.put("username", optionalUser.get().getTokenAuth());
		claims.put("externalSystem", externalSystem.getName());
		claims.put("iduser", optionalUser.get().getId());
		
		authResponse.setUser(authMapper.entityToResponse(optionalUser.get()));
		authResponse.setToken(jwtUtil.generateToken(optionalUser.get().getTokenAuth(), claims));
		
		authResponse.getUser().setSystem("EXT_SYSTEM");
		authResponse.setName(externalSystem.getName());
		
		final UserDataShared userDataShared = authMapper.getUserDataShared(optionalUser.get(), authResponse.getToken());
	    userDataShared.setRoutes(authResponse.getUser().getRoutes());

	    redisService.saveUserData(authResponse.getToken(), userDataShared);
	    
		return authResponse;
	}
	
	
}
