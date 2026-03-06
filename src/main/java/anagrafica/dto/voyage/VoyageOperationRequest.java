package anagrafica.dto.voyage;

import lombok.Data;

@Data
public class VoyageOperationRequest {
	private Long voyageId;
	private Long productIdExt;
	private Integer quantity;
	private Long typeOperationId;
	private Long companyId;
}
