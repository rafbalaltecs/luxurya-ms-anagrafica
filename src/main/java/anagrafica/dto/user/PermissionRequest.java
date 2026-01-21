package anagrafica.dto.user;

import lombok.Data;

@Data
public class PermissionRequest {
    private String name;
    private String code;
    private Long typeUserId;
    private String route;
}
