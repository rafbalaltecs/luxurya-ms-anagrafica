package anagrafica.dto.agent;

import java.util.List;

import anagrafica.dto.voyage.VoyageClientResponse;
import lombok.Data;

@Data
public class AgentCurrentVoyageExternalResponse {
	private Integer currentVoyage;
	private Long idVoyage;
	private String codeVoyage;
	private List<VoyageClientResponse> customers;
}
