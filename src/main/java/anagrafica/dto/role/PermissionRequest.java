package anagrafica.dto.role;

import lombok.Data;

@Data
public class PermissionRequest {
	private String name;
	private String description;
	private String route;
	private String code;
	private Long typeUserId;
}
