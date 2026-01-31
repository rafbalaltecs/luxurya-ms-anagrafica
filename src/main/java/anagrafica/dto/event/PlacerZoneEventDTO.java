package anagrafica.dto.event;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class PlacerZoneEventDTO {
    private String placerId;
    private String zoneId;
    private String operationAudit;
    private LocalDateTime operationDate;
    private String operationBy;
}
