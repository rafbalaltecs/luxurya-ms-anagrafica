package anagrafica.service.user.impl;

import anagrafica.dto.role.PermissionRequest;
import anagrafica.dto.role.PermissionResponse;
import anagrafica.entity.Permission;
import anagrafica.entity.PermissionUser;
import anagrafica.entity.TypeUser;
import anagrafica.entity.User;
import anagrafica.exception.RestException;
import anagrafica.repository.permission.PermissionRepository;
import anagrafica.repository.permission.PermissionUserRepository;
import anagrafica.repository.user.TypeUserRepository;
import anagrafica.service.user.PermissionService;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

@Service
@Slf4j
public class PermissionServiceImpl implements PermissionService {

    private final PermissionRepository permissionRepository;
    private final PermissionUserRepository permissionUserRepository;
    private final TypeUserRepository typeUserRepository;

    public PermissionServiceImpl(PermissionRepository permissionRepository, PermissionUserRepository permissionUserRepository, TypeUserRepository typeUserRepository) {
        this.permissionRepository = permissionRepository;
        this.permissionUserRepository = permissionUserRepository;
        this.typeUserRepository = typeUserRepository;
    }

    @Override
    public Set<String> findRouteFromTypeUser(TypeUser typeUser) {
        final List<Permission> permissions = permissionRepository.findPermissionFromTypeUserId(typeUser.getId());

        if(!permissions.isEmpty()){
            final Set<String> routes = new HashSet<>();
            for(final Permission permission: permissions){
                routes.add(permission.getRoute());
            }
            return routes;
        }
        return new HashSet<>();
    }

    @Override
    public List<PermissionUser> findPermissionUserFromUser(User user) {
        return permissionUserRepository.findByUserId(user.getId());
    }

	@Override
	public List<PermissionResponse> findAll(Integer offset, Integer limit) {
		final List<PermissionResponse> response = new ArrayList<PermissionResponse>();
		
		final List<Permission> permissions = permissionRepository.findAllNotDeleted();
		
		if(!permissions.isEmpty()) {
			permissions.forEach(p -> {
				final PermissionResponse permissionResponse = new PermissionResponse();
				permissionResponse.setCode(p.getCode());
				permissionResponse.setId(p.getId());
				permissionResponse.setName(p.getName());
				permissionResponse.setRoute(p.getRoute());
				permissionResponse.setTypeUserId(p.getTypeUser().getId());
				permissionResponse.setTypeUserName(p.getTypeUser().getName());
				response.add(permissionResponse);
			});
		}
		
		return response;
	}

	@Override
	@Transactional
	public PermissionResponse create(PermissionRequest request) {
		
		final Optional<Permission> optionalPermission = permissionRepository.findPermissionFromCode(request.getCode());
		
		if(optionalPermission.isPresent()) {
			throw new RestException("Permission exist with code: " + request.getCode());
		}
		
		final Optional<TypeUser> optionalTypeUser = typeUserRepository.findById(request.getTypeUserId());
		
		if(optionalTypeUser.isEmpty()) {
			throw new RestException("TypeUser not exist with code: " + request.getTypeUserId());
		}
		
		Permission permission = new Permission();
		permission.setCode(request.getCode());
		permission.setName(request.getName());
		permission.setDeleted(Boolean.FALSE);
		permission.setRoute(request.getRoute());
		permission.setTypeUser(optionalTypeUser.get());
		
		permission = permissionRepository.save(permission);
		
		final PermissionResponse permissionResponse = new PermissionResponse();
		permissionResponse.setCode(permission.getCode());
		permissionResponse.setId(permission.getId());
		permissionResponse.setName(permission.getName());
		permissionResponse.setRoute(permission.getRoute());
		permissionResponse.setTypeUserId(permission.getTypeUser().getId());
		permissionResponse.setTypeUserName(permission.getTypeUser().getName());
		
		return permissionResponse;
	}

	@Override
	@Transactional
	public PermissionResponse update(Long id, PermissionRequest request) {
		final Optional<Permission> optionalPermission = permissionRepository.findById(id);
		if(optionalPermission.isEmpty()) {
			throw new RestException("Permisision not exist with ID: " + id);
		}
		
		final Optional<Permission> optionalPermissionCode = permissionRepository.findPermissionFromCode(request.getCode());
		
		if(optionalPermissionCode.isPresent()) {
			if(!optionalPermissionCode.get().getId().equals(id)) {
				throw new RestException("Permission exist with code: " + request.getCode());
			}
		}
		
		final Optional<TypeUser> optionalTypeUser = typeUserRepository.findById(request.getTypeUserId());
		
		if(optionalTypeUser.isEmpty()) {
			throw new RestException("TypeUser not exist with code: " + request.getTypeUserId());
		}
		
		optionalPermissionCode.get().setCode(request.getCode());
		optionalPermissionCode.get().setName(request.getName());
		optionalPermissionCode.get().setDeleted(Boolean.FALSE);
		optionalPermissionCode.get().setRoute(request.getRoute());
		optionalPermissionCode.get().setTypeUser(optionalTypeUser.get());
		
		permissionRepository.save(optionalPermissionCode.get());
		
		final PermissionResponse permissionResponse = new PermissionResponse();
		permissionResponse.setCode(optionalPermissionCode.get().getCode());
		permissionResponse.setId(optionalPermissionCode.get().getId());
		permissionResponse.setName(optionalPermissionCode.get().getName());
		permissionResponse.setRoute(optionalPermissionCode.get().getRoute());
		permissionResponse.setTypeUserId(optionalPermissionCode.get().getTypeUser().getId());
		permissionResponse.setTypeUserName(optionalPermissionCode.get().getTypeUser().getName());
		
		return permissionResponse;
	}

	@Override
	@Transactional
	public void delete(Long id) {
		final Optional<Permission> optionalPermission = permissionRepository.findById(id);
		if(optionalPermission.isEmpty()) {
			throw new RestException("Permisision not exist with ID: " + id);
		}
		
		optionalPermission.get().setDeleted(Boolean.TRUE);
		permissionRepository.save(optionalPermission.get());
		
	}
}
