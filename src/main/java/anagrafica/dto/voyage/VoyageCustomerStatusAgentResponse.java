package anagrafica.dto.voyage;

import java.util.List;

import lombok.Data;

@Data
public class VoyageCustomerStatusAgentResponse {
	private Integer counterCustomersToVisit;
	private List<VoyageClientResponse> customersToVisit;
	private Integer counterCustomersVisited;
	private List<VoyageClientResponse> customersToVisited;
}
