package anagrafica.service.user.impl;

import anagrafica.dto.user.UserRequest;
import anagrafica.dto.user.UserResponse;
import anagrafica.entity.User;
import anagrafica.repository.user.UserRepository;
import anagrafica.service.user.UserService;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
@Slf4j
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserResponse findAll() {
        return null;
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
}
