package anagrafica.dto.event;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class AgentZoneEventDTO {
    private String agentId;
    private String zoneId;
    private String operationAudit;
    private String operationDate;
    private String operationBy;
}
