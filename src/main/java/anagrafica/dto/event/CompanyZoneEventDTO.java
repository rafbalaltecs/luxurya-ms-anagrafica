package anagrafica.dto.event;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class CompanyZoneEventDTO {
    private String companyId;
    private String zoneId;
    private String operationAudit;
    private LocalDateTime operationDate;
    private String operationBy;
}
