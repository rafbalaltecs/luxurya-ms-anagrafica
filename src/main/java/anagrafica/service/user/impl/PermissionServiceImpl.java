package anagrafica.service.user.impl;

import anagrafica.entity.Permission;
import anagrafica.entity.PermissionUser;
import anagrafica.entity.TypeUser;
import anagrafica.entity.User;
import anagrafica.repository.permission.PermissionRepository;
import anagrafica.repository.permission.PermissionUserRepository;
import anagrafica.service.user.PermissionService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
@Slf4j
public class PermissionServiceImpl implements PermissionService {

    private final PermissionRepository permissionRepository;
    private final PermissionUserRepository permissionUserRepository;

    public PermissionServiceImpl(PermissionRepository permissionRepository, PermissionUserRepository permissionUserRepository) {
        this.permissionRepository = permissionRepository;
        this.permissionUserRepository = permissionUserRepository;
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
}
