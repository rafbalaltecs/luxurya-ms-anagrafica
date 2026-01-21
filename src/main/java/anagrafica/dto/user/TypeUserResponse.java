package anagrafica.dto.user;

import lombok.Data;

@Data
public class TypeUserResponse {
    private Long id;
    private String code;
    private String name;
    private String description;
}
