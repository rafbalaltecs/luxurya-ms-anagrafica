package anagrafica.entity.audit;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Document("placer_zone")
@Data
public class PlacerZoneAudit {
    @Id
    private String id;
    private Long idPlacer;
    private Long idZone;
    private OperationAuditEnum operation;
    private Long userOperationId;
    private LocalDateTime createdAt;
}
