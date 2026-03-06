package anagrafica.service.voyage;

import java.util.List;

import anagrafica.dto.voyage.VoyageCompanyOperationTypeRequest;
import anagrafica.dto.voyage.VoyageCompanyOperationTypeResponse;

public interface VoyageCompanyOperationTypeService {
	List<VoyageCompanyOperationTypeResponse> findAll();
	VoyageCompanyOperationTypeResponse save(final VoyageCompanyOperationTypeRequest request);
	VoyageCompanyOperationTypeResponse update(final Long id, final VoyageCompanyOperationTypeRequest request);
	void delete(final Long id);
}
