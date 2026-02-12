package anagrafica.dto.voyage;

import java.util.List;

import anagrafica.dto.company.CompanyResponse;
import lombok.Data;

@Data
public class VoyageResponse {
	private Long id;
	private String code;
	private Long agentId;
	private Long zoneId;
	private String startDate;
	private String endDate;
	private List<CompanyResponse> company;
}
