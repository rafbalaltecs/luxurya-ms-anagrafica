package anagrafica.dto.user;

import lombok.Data;

import java.util.Set;

@Data
public class UserResponse {
    private Long id;
    private String email;
    private String system;
    private Set<String> routes;
    private Set<String> typeUsers;
}
