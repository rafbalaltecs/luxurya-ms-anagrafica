package anagrafica.service.voyage.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import anagrafica.dto.voyage.VoyageCompanyOperationRequest;
import anagrafica.dto.voyage.VoyageCompanyOperationResponse;
import anagrafica.entity.VoyageCompanyOperation;
import anagrafica.repository.voyage.VoyageCompanyOperationRepository;
import anagrafica.service.voyage.VoyageCompanyOperationService;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class VoyageCompanyOperationServiceImpl implements VoyageCompanyOperationService{
	
	private final VoyageCompanyOperationRepository voyageCompanyOperationRepository;
	
	public VoyageCompanyOperationServiceImpl(VoyageCompanyOperationRepository voyageCompanyOperationRepository) {
		this.voyageCompanyOperationRepository = voyageCompanyOperationRepository;
	}

	@Override
	public List<VoyageCompanyOperationResponse> findAllOperations() {
		final List<VoyageCompanyOperation> list = voyageCompanyOperationRepository.findAll();
		if(!list.isEmpty()) {
			List<VoyageCompanyOperationResponse> result = new ArrayList<VoyageCompanyOperationResponse>();
			
			for(final VoyageCompanyOperation itemEntity: list) {
				final VoyageCompanyOperationResponse resp = new VoyageCompanyOperationResponse();
			//	resp.setCode(itemEntity.get);
			}
			
			return result;
		}
		return new ArrayList<VoyageCompanyOperationResponse>();
	}

	@Override
	public VoyageCompanyOperationResponse save(VoyageCompanyOperationRequest request) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public VoyageCompanyOperationResponse update(Long id, VoyageCompanyOperationRequest request) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void delete(Long id) {
		// TODO Auto-generated method stub
		
	}

}
