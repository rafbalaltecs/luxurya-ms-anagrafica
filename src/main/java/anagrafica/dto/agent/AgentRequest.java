package anagrafica.dto.agent;

import lombok.Data;

@Data
public class AgentRequest {
    private String name;
    private String surname;
    private String telephone;
    private Long userId;
}
