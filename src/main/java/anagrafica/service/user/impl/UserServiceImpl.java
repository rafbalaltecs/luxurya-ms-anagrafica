package anagrafica.service.user.impl;

import anagrafica.dto.user.UserRequest;
import anagrafica.dto.user.UserResponse;
import anagrafica.entity.PermissionUser;
import anagrafica.entity.TypeUser;
import anagrafica.entity.User;
import anagrafica.exception.RestException;
import anagrafica.repository.permission.PermissionUserRepository;
import anagrafica.repository.user.TypeUserRepository;
import anagrafica.repository.user.UserRepository;
import anagrafica.service.auth.impl.AuthMapper;
import anagrafica.service.user.UserService;
import anagrafica.utils.JwtUtil;
import anagrafica.utils.MethodUtils;
import anagrafica.utils.PasswordUtils;
import io.micrometer.common.util.StringUtils;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final TypeUserRepository typeUserRepository;
    private final PermissionUserRepository permissionUserRepository;
    private final JwtUtil jwtUtil;
    private final AuthMapper authMapper;

    public UserServiceImpl(UserRepository userRepository, 
    		TypeUserRepository typeUserRepository, 
    		PermissionUserRepository permissionUserRepository, 
    		JwtUtil jwtUtil,
    		AuthMapper authMapper) {
        this.userRepository = userRepository;
        this.typeUserRepository = typeUserRepository;
        this.permissionUserRepository = permissionUserRepository;
        this.jwtUtil = jwtUtil;
        this.authMapper = authMapper;
    }

    @Override
    public List<UserResponse> findAll(final Integer offset, final Integer limit) {
        final List<UserResponse> userResponses = new ArrayList<>();
        final Page<User> userPage = userRepository.findAllNotDeleted(MethodUtils.getPagination(offset, limit));
        if(!userPage.isEmpty()){
            userPage.forEach(user -> {
                userResponses.add(authMapper.entityToResponse(user));
            });
        }
        return userResponses;
    }

    @Override
    @Transactional
    public UserResponse update(Long id, UserRequest request) {
    	if(request.getCodeTypeUser() != null && !request.getCodeTypeUser().isEmpty()) {
    		
    		final Optional<User> optionalUserId = userRepository.findById(id);
    		
    		if(optionalUserId.isEmpty()) {
				throw new RestException("Not Exist User With ID: " + id);
			}
    		
    		final Optional<User> optionalUser = userRepository.findByEmail(request.getEmail());
			
			if(optionalUser.isPresent()) {
				if(!optionalUser.get().getId().equals(id)) {
					throw new RestException("Exist User With email: " + request.getEmail());
				}
			}
			
			optionalUserId.get().setDeleted(Boolean.FALSE);
			optionalUserId.get().setIsDisabled(Boolean.FALSE);
			if(StringUtils.isNotEmpty(request.getPassword())) {
				optionalUserId.get().setPassword(PasswordUtils.encodePassword(request.getPassword()));
			}
			optionalUserId.get().setEmail(request.getEmail());
			
			
			final List<PermissionUser> permissionUsers = permissionUserRepository.findByUserId(id);
			
			if(!permissionUsers.isEmpty()) {
				for(final PermissionUser permissionUser: permissionUsers) {
					permissionUser.setDeleted(Boolean.TRUE);
					permissionUser.setUpdatedBy(jwtUtil.getIdProfileLogged());
					permissionUser.setDeleted(Boolean.TRUE);
					permissionUserRepository.save(permissionUser);
				}
			}
			
			userRepository.save(optionalUserId.get());
			
			
			for(final String codeTypeUser: request.getCodeTypeUser()) {
				if(StringUtils.isNotEmpty(codeTypeUser)) {
					final Optional<TypeUser> optionalTypeUser = typeUserRepository.findByCode(codeTypeUser);
					if(optionalTypeUser.isEmpty()) {
						throw new RestException("codeTypeUser not found: " + codeTypeUser);
					}
					
					final PermissionUser permissionUser = new PermissionUser();
					permissionUser.setTypeUser(optionalTypeUser.get());
					permissionUser.setDeleted(Boolean.FALSE);
					permissionUser.setUser(optionalUserId.get());
					permissionUser.setCreatedBy(jwtUtil.getIdProfileLogged());
					permissionUserRepository.save(permissionUser);
					
				}else {
					log.warn("Not Exist This Code {} For Type User", codeTypeUser);
				}
			}
			
			return authMapper.entityToResponse(optionalUserId.get());
			
			
    	}else {
			throw new RestException("codeTypeUser is mandatory");
		}
    }

    @Override
    @Transactional
    public void lastLoginUpdate(String email, LocalDateTime eventDate) {
        final Optional<User> optionalUser = userRepository.findByEmail(email);
        if(!optionalUser.isEmpty()){
            optionalUser.get().setLastLogin(eventDate);
            userRepository.save(optionalUser.get());
        }
    }

	@Override
	@Transactional
	public UserResponse create(UserRequest request) {
		if(request.getCodeTypeUser() != null && !request.getCodeTypeUser().isEmpty()) {
			
			final Optional<User> optionalUser = userRepository.findByEmail(request.getEmail());
			
			if(optionalUser.isPresent()) {
				throw new RestException("Exist User With email: " + request.getEmail());
			}
			
			User user = new User();
			user.setEmail(request.getEmail());
			user.setIsDisabled(Boolean.FALSE);
			user.setIsDeleted(Boolean.FALSE);
			user.setPassword(PasswordUtils.encodePassword(request.getPassword()));
			
			user = userRepository.save(user);
			
			for(final String codeTypeUser: request.getCodeTypeUser()) {
				if(StringUtils.isNotEmpty(codeTypeUser)) {
					final Optional<TypeUser> optionalTypeUser = typeUserRepository.findByCode(codeTypeUser);
					if(optionalTypeUser.isEmpty()) {
						throw new RestException("codeTypeUser not found: " + codeTypeUser);
					}
					
					final PermissionUser permissionUser = new PermissionUser();
					permissionUser.setTypeUser(optionalTypeUser.get());
					permissionUser.setDeleted(Boolean.FALSE);
					permissionUser.setUser(user);
					permissionUser.setCreatedBy(jwtUtil.getIdProfileLogged());
					permissionUserRepository.save(permissionUser);
					
				}else {
					log.warn("Not Exist This Code {} For Type User", codeTypeUser);
				}
			}
			
			return authMapper.entityToResponse(user);
			
		}else {
			throw new RestException("codeTypeUser is mandatory");
		}
	}

	@Override
	@Transactional
	public void delete(Long id) {

		final Optional<User> optionalUserId = userRepository.findById(id);
		
		if(optionalUserId.isEmpty()) {
			throw new RestException("Not Exist User With ID: " + id);
		}
		optionalUserId.get().setDeleted(Boolean.TRUE);
		optionalUserId.get().setIsDeleted(Boolean.TRUE);
		userRepository.saveAndFlush(optionalUserId.get());
	}
}
