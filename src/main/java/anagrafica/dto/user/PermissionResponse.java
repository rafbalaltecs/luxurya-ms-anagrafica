package anagrafica.dto.user;

import lombok.Data;

@Data
public class PermissionResponse {
    private Long id;
    private String name;
    private String code;
    private String route;
    private TypeUserResponse typeUser;
}
