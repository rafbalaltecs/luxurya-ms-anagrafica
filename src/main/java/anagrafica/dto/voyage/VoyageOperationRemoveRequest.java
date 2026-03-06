package anagrafica.dto.voyage;

import lombok.Data;

@Data
public class VoyageOperationRemoveRequest {
	private Long voyageId;
	private Long companyId;
}
