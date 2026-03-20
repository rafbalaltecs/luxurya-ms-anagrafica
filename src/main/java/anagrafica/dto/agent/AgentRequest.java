package anagrafica.dto.agent;

import java.util.List;

import lombok.Data;

@Data
public class AgentRequest {
    private String name;
    private String surname;
    private String telephone;
    private Long userId;
    private List<Long> zoneId;
}
