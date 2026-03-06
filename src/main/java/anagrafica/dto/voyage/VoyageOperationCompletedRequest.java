package anagrafica.dto.voyage;

import lombok.Data;

@Data
public class VoyageOperationCompletedRequest {
	private Long voyageId;
	private Long companyId;
}
