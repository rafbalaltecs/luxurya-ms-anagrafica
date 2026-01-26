package anagrafica.entity.audit;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Document("agent_zone")
@Data
public class AgentZoneAudit {
    @Id
    private String id;
    private Long idAgent;
    private Long idZone;
    private OperationAuditEnum operation;
    private Long userOperationId;
    private LocalDateTime createdAt;
}
