package anagrafica.service.user;

import anagrafica.dto.user.PermissionResponse;
import anagrafica.dto.user.TypeUserRequest;
import anagrafica.dto.user.TypeUserResponse;

import java.util.List;

public interface TypeUserService {
    List<TypeUserResponse> findAll(final Integer offset, final Integer limit);
    TypeUserResponse create(final TypeUserRequest request);
    TypeUserResponse update(final Long id, final TypeUserRequest request);
    void delete(final Long id);

    //Find Permissions
    List<PermissionResponse> findAllPermissionFromTypeUserId(final Long typeUserId);
}
