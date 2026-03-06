package anagrafica.dto.voyage;

import java.util.List;

import anagrafica.dto.company.CompanyResponse;
import anagrafica.dto.zone.ZoneResponse;
import lombok.Data;

@Data
public class VoyageResponse {
	private Long id;
	private String code;
	private Long agentId;
	private Long zoneId;
	private String startDate;
	private String endDate;
	private ZoneResponse zone;
	private List<CompanyResponse> company;
	private Integer totalCompany;
	private Integer totalOperations;
	private Integer totalZones;
	private String amount;
	private String agentName;
	private String agentSurname;
}
