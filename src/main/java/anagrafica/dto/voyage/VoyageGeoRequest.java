package anagrafica.dto.voyage;

import lombok.Data;

@Data
public class VoyageGeoRequest {
	private Long agentId;
	private Long zoneId;
	private int numberOfWeeks;
	private int maxClientsPerWeek;
}
