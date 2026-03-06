package anagrafica.dto.ext;

import lombok.Data;

@Data
public class LoadVoyageProductItemResponse {
	private Long productId;
	private Integer quantity;
	private String productName;
	private String productSku;
}