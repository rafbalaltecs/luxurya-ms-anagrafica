package anagrafica.dto.voyage;

import java.util.List;

import lombok.Data;

@Data
public class VoyageConfigurationRequest {
	private Integer week;
	private List<Long> zones;
	private Long agentId;
}
