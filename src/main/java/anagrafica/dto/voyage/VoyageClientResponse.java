package anagrafica.dto.voyage;

import java.util.List;

import anagrafica.dto.company.CompanyStockResponse;
import lombok.Data;

@Data
public class VoyageClientResponse {
	private Long clientId;
	private String clientName;
	private String piva;
	private String lat;
	private String lon;
	private Boolean visitCarriedOut;
	private String zoneName;
	private String address;
	private String telephone;
	List<CompanyStockResponse> stocks;
	private Long voyageId;
	private Integer total;
}
