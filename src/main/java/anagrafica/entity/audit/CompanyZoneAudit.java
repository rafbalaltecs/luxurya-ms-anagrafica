package anagrafica.entity.audit;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Document("company_zone")
@Data
public class CompanyZoneAudit {
    @Id
    private String id;
    private Long idCompany;
    private Long idZone;
    private OperationAuditEnum operation;
    private Long userOperationId;
    private LocalDateTime createdAt;
}
