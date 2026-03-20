package anagrafica.dto.voyage;

import java.util.List;

import lombok.Data;

@Data
public class VoyageCustomerFromZoneResponseItem {
	private Long zoneId;
	private String zoneName;
	private Integer totalClients;
	private List<VoyageClientResponse> clients;
}
