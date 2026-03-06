package anagrafica.dto.voyage;

import lombok.Data;

@Data
public class VoyageOperationResponse {
	private Long operationId;
	private String companyCode;
	private String companyName;
	private String companyAddress;
	private String nameZone;
	private String companyTelephone;
	private String companyTelephoneTwo;
	private String companyPiva;
	private String typePaymentName;
	private Integer quantity;
	private Boolean totalWithdrawal;
	private Boolean isNewCompany;
	private String price;
	
	private Long voyageId;
}
