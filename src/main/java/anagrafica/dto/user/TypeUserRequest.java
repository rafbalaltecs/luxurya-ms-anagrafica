package anagrafica.dto.user;

import java.util.List;

import lombok.Data;

@Data
public class TypeUserRequest {
    private String code;
    private String name;
    private String description;
}
