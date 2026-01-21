package anagrafica.service.user.impl;

import anagrafica.dto.user.PermissionResponse;
import anagrafica.dto.user.TypeUserRequest;
import anagrafica.dto.user.TypeUserResponse;
import anagrafica.entity.TypeUser;
import anagrafica.exception.RestException;
import anagrafica.repository.permission.PermissionRepository;
import anagrafica.repository.user.TypeUserRepository;
import anagrafica.service.user.TypeUserService;
import anagrafica.service.user.UserMapper;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class TypeUserServiceImpl implements TypeUserService {

    private final TypeUserRepository typeUserRepository;
    private final PermissionRepository permissionRepository;
    private final UserMapper userMapper;

    public TypeUserServiceImpl(TypeUserRepository typeUserRepository, PermissionRepository permissionRepository, UserMapper userMapper) {
        this.typeUserRepository = typeUserRepository;
        this.permissionRepository = permissionRepository;
        this.userMapper = userMapper;
    }

    @Override
    public List<TypeUserResponse> findAll(Integer offset, Integer limit) {
        return List.of();
    }

    @Override
    @Transactional
    public TypeUserResponse create(TypeUserRequest request) {
        TypeUser entity = userMapper.requestToEntityTypeUser(request);
        entity = typeUserRepository.save(entity);
        return userMapper.entityToDTOTypeUser(entity);
    }

    @Override
    @Transactional
    public TypeUserResponse update(Long id, TypeUserRequest request) {
        final Optional<TypeUser> optionalTypeUser = typeUserRepository.findById(id);
        if(optionalTypeUser.isEmpty()){
            throw new RestException("TypeUser Not Found");
        }

        return null;
    }

    @Override
    @Transactional
    public void delete(Long id) {

    }

    @Override
    public List<PermissionResponse> findAllPermissionFromTypeUserId(Long typeUserId) {
        return List.of();
    }
}
