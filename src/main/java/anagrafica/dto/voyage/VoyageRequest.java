package anagrafica.dto.voyage;

import lombok.Data;

@Data
public class VoyageRequest {
	private Long agentId;
	private Long zoneId;
	private String startDate;
	private String endDate;
}
