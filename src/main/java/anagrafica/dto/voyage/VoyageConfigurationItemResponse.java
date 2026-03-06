package anagrafica.dto.voyage;

import lombok.Data;

@Data
public class VoyageConfigurationItemResponse {
	private Long zoneId;
	private String zoneName;
	private Integer totalClients;
	private String provinceName;
}
