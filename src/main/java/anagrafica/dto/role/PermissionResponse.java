package anagrafica.dto.role;

import lombok.Data;

@Data
public class PermissionResponse {
	private Long id;
	private String code;
	private String name;
	private String route;
	private String typeUserName;
	private Long typeUserId;
}
