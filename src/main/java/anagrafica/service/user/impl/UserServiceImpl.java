package anagrafica.service.user.impl;

import anagrafica.dto.user.UserRequest;
import anagrafica.dto.user.UserResponse;
import anagrafica.entity.User;
import anagrafica.repository.user.UserRepository;
import anagrafica.service.user.UserService;
import anagrafica.utils.MethodUtils;
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

    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public List<UserResponse> findAll(final Integer offset, final Integer limit) {
        final List<UserResponse> userResponses = new ArrayList<>();
        final Page<User> userPage = userRepository.findAllNotDeleted(MethodUtils.getPagination(offset, limit));
        if(!userPage.isEmpty()){
            userPage.forEach(user -> {
                final UserResponse userResponse = new UserResponse();
                userResponse.setId(user.getId());
                userResponse.setEmail(user.getEmail());
                userResponses.add(userResponse);
            });
        }
        return userResponses;
    }

    @Override
    @Transactional
    public UserResponse update(Long id, UserRequest request) {
        return null;
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
		// TODO Auto-generated method stub
		return null;
	}
}
