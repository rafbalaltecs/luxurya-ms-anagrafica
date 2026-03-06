package anagrafica.dto.agent;

import java.util.List;

import anagrafica.dto.zone.ZoneResponse;
import lombok.Data;

@Data
public class AgentCurrentVoyageResponse {
	private Long id;
	private Integer currentVoyage;
	private List<ZoneResponse> zones;
	private Long idVoyage;
}
