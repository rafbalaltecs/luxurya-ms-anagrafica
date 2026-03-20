package anagrafica.dto.voyage;

import java.util.List;

import lombok.Data;

@Data
public class VoyageCustomerFromZoneResponse {
	private Long currentVoyageId;
	private Long agentId;
	private List<VoyageCustomerFromZoneResponseItem> items;
}
