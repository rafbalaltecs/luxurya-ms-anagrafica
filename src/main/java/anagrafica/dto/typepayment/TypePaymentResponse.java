package anagrafica.dto.typepayment;

import lombok.Data;

@Data
public class TypePaymentResponse {
	private Long id;
	private String name;
	private String code;
	private String description;
}
