package anagrafica.dto.agent;

import lombok.Data;

@Data
public class AgentProductResponse {
	private Long productId;
	private String productName;
	private Integer quantity;
	private String productDescription;
	private String productCode;
	private String productSku;
}
