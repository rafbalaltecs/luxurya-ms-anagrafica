package anagrafica.dto.company;

import lombok.Data;

@Data
public class CompanyStockResponse {
	private Long companyId;
	private Long companyStockId;
	private String productName;
	private Long quantity;
	private String productCode;
}
