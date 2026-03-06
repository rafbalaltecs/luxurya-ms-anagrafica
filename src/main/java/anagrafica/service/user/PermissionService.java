package anagrafica.service.user;

import anagrafica.dto.role.PermissionRequest;
import anagrafica.dto.role.PermissionResponse;
import anagrafica.entity.PermissionUser;
import anagrafica.entity.TypeUser;
import anagrafica.entity.User;

import java.util.List;
import java.util.Set;

public interface PermissionService {
    Set<String> findRouteFromTypeUser(TypeUser typeUser);
    List<PermissionUser> findPermissionUserFromUser(User user);
    
    List<PermissionResponse> findAll(final Integer offset, final Integer limit);
    PermissionResponse create(final PermissionRequest request);
    PermissionResponse update(final Long id, final PermissionRequest request);
    void delete(final Long id);
}
