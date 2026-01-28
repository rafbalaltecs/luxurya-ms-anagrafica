package anagrafica.service.auth.impl;

import anagrafica.dto.shared.UserDataShared;
import anagrafica.dto.user.UserResponse;
import anagrafica.entity.PermissionUser;
import anagrafica.entity.User;
import anagrafica.service.user.PermissionService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

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
                final Set<String> typeUsers = new HashSet<>();
                for(final PermissionUser permissionUser: permissionUsers){
                    typeUsers.add(permissionUser.getTypeUser().getCode());
                    userResponse.setRoutes(permissionService.findRouteFromTypeUser(permissionUser.getTypeUser()));
                }
                userResponse.setTypeUsers(typeUsers);
            }
            return userResponse;
        }
        return null;
    }

    public UserDataShared getUserDataShared(
            final User user,
            final String token){
        if(user != null && StringUtils.isNotEmpty(token)){
            final UserDataShared userDataShared = new UserDataShared();
            userDataShared.setUserId(user.getId());
            userDataShared.setUsername(user.getEmail());
            userDataShared.setToken(token);
            userDataShared.setLoginAt(LocalDateTime.now().toString());
            return userDataShared;
        }
        return null;
    }

}
