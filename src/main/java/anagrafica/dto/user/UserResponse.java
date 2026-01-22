package anagrafica.dto.user;

import lombok.Data;

import java.util.Set;

@Data
public class UserResponse {
    private String email;
    private Set<String> routes;
}
