package anagrafica.service.auth.impl;

import anagrafica.dto.user.UserResponse;
import anagrafica.entity.PermissionUser;
import anagrafica.entity.User;
import anagrafica.service.user.PermissionService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AuthMapper {

    private final PermissionService permissionService;

    public AuthMapper(PermissionService permissionService) {
        this.permissionService = permissionService;
    }

    public UserResponse entityToResponse(final User user){
        if(user != null){
            final UserResponse userResponse = new UserResponse();
            userResponse.setEmail(user.getEmail());
            final List<PermissionUser> permissionUsers = permissionService.findPermissionUserFromUser(user);
            if(!permissionUsers.isEmpty()){
                for(final PermissionUser permissionUser: permissionUsers){
                    userResponse.setRoutes(permissionService.findRouteFromTypeUser(permissionUser.getTypeUser()));
                }
            }
            return userResponse;
        }
        return null;
    }

}
