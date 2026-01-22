package anagrafica.service.user;

import anagrafica.entity.PermissionUser;
import anagrafica.entity.TypeUser;
import anagrafica.entity.User;

import java.util.List;
import java.util.Set;

public interface PermissionService {
    Set<String> findRouteFromTypeUser(TypeUser typeUser);
    List<PermissionUser> findPermissionUserFromUser(User user);
}
