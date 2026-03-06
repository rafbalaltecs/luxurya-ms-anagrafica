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
import anagrafica.utils.JwtUtil;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class TypeUserServiceImpl implements TypeUserService {

    private final TypeUserRepository typeUserRepository;
    private final PermissionRepository permissionRepository;
    private final UserMapper userMapper;
    private final JwtUtil jwtUtil;

    public TypeUserServiceImpl(TypeUserRepository typeUserRepository, PermissionRepository permissionRepository, UserMapper userMapper, JwtUtil jwtUtil) {
        this.typeUserRepository = typeUserRepository;
        this.permissionRepository = permissionRepository;
        this.userMapper = userMapper;
        this.jwtUtil = jwtUtil;
    }

    @Override
    public List<TypeUserResponse> findAll(Integer offset, Integer limit) {
    	final List<TypeUserResponse> response = new ArrayList<TypeUserResponse>();
    	final List<TypeUser> list = typeUserRepository.findAllNotDeleted();
    	list.forEach(tp -> {
    		response.add(userMapper.entityToDTOTypeUser(tp));
    	});
        return response;
    }

    @Override
    @Transactional
    public TypeUserResponse create(TypeUserRequest request) {
    	
    	final Optional<TypeUser> optionalTypeUser = typeUserRepository.findByCode(request.getCode());
    	if(optionalTypeUser.isPresent()) {
    		throw new RestException("TypeUser Exist");
    	}
    	
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
        
        final Optional<TypeUser> optionalTypeUserCode = typeUserRepository.findByCode(request.getCode());
    	if(optionalTypeUserCode.isPresent()) {
    		if(!optionalTypeUserCode.get().getId().equals(id)) {
    			throw new RestException("TypeUser Exist");
    		}
    	}
    	
    	optionalTypeUser.get().setCode(request.getCode());
    	optionalTypeUser.get().setName(request.getName());
    	optionalTypeUser.get().setDescription(request.getDescription());
    	optionalTypeUser.get().setUpdatedBy(jwtUtil.getIdProfileLogged());
    	
    	typeUserRepository.save(optionalTypeUser.get());

        return userMapper.entityToDTOTypeUser(optionalTypeUser.get());
    }

    @Override
    @Transactional
    public void delete(Long id) {
    	 final Optional<TypeUser> optionalTypeUser = typeUserRepository.findById(id);
         if(optionalTypeUser.isEmpty()){
             throw new RestException("TypeUser Not Found");
         }
         optionalTypeUser.get().setDeleted(Boolean.TRUE);
         typeUserRepository.save(optionalTypeUser.get());
    }

    @Override
    public List<PermissionResponse> findAllPermissionFromTypeUserId(Long typeUserId) {
        return List.of();
    }
}
