package anagrafica.service.voyage;

import java.util.List;

import anagrafica.dto.voyage.VoyageRequest;
import anagrafica.dto.voyage.VoyageResponse;

public interface VoyageService {
	
	List<VoyageResponse> findAll(final Integer offset, final Integer limit);
	VoyageResponse findById(final Long id);
	List<VoyageResponse> findByAgentId(final Long idAgent);
	VoyageResponse create(final VoyageRequest request);
	VoyageResponse update(final Long id, final VoyageRequest request);
	void delete(final Long id);
}
