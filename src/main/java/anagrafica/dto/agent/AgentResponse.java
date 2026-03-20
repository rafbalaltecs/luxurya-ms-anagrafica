package anagrafica.dto.agent;

import java.util.List;

import anagrafica.dto.user.UserResponse;
import anagrafica.dto.zone.ZoneResponse;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class AgentResponse {
    private Long id;
    private String name;
    private String surname;
    private String telephone;
    private ZoneResponse zona;
    private UserResponse user;
    private Integer totalClients;
    private List<ZoneResponse> zone;
}
