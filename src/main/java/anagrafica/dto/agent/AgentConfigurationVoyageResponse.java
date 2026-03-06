package anagrafica.dto.agent;

import java.util.List;

import anagrafica.dto.voyage.VoyageConfigurationItemResponse;
import lombok.Data;

@Data
public class AgentConfigurationVoyageResponse {
	private Long id;
	private Integer week;
	private Long agentId;
	private String agentName;
	private String agentSurname;
	private List<VoyageConfigurationItemResponse> items;
}
