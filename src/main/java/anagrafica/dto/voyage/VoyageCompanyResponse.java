package anagrafica.dto.voyage;

import lombok.Data;

@Data
public class VoyageCompanyResponse {
	private Long id;
	private Long idVoyage;
	private Long idCompany;
	private String voyageCode;
	private String companyName;
	private String companyFiscalCode;
	private String companyAddress;
	private Boolean isShipping;
	private Boolean isBusinessClosure;
	private Boolean isTravel;
	private Boolean isTotalPickup;
}
