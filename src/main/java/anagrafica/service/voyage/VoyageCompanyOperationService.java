package anagrafica.service.voyage;

import java.util.List;

import anagrafica.dto.voyage.VoyageCompanyOperationRequest;
import anagrafica.dto.voyage.VoyageCompanyOperationResponse;

public interface VoyageCompanyOperationService {
	List<VoyageCompanyOperationResponse> findAllOperations();
	VoyageCompanyOperationResponse save(final VoyageCompanyOperationRequest request);
	VoyageCompanyOperationResponse update(final Long id, final VoyageCompanyOperationRequest request);
	void delete(final Long id);
}
