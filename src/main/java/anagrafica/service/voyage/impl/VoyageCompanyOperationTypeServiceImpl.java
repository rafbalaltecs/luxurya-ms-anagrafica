package anagrafica.service.voyage.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import anagrafica.dto.voyage.VoyageCompanyOperationTypeRequest;
import anagrafica.dto.voyage.VoyageCompanyOperationTypeResponse;
import anagrafica.entity.TypeVoyageOperation;
import anagrafica.repository.voyage.TypeVoyageOperationRepository;
import anagrafica.service.voyage.VoyageCompanyOperationTypeService;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class VoyageCompanyOperationTypeServiceImpl implements VoyageCompanyOperationTypeService{
	
	private final TypeVoyageOperationRepository typeVoyageOperationRepository;
	
	public VoyageCompanyOperationTypeServiceImpl(TypeVoyageOperationRepository typeVoyageOperationRepository) {
		this.typeVoyageOperationRepository = typeVoyageOperationRepository;
	}
	
	@Override
	public List<VoyageCompanyOperationTypeResponse> findAll() {
		final List<TypeVoyageOperation> findEntities = typeVoyageOperationRepository.findAll();
		
		if(!findEntities.isEmpty()) {
			final List<VoyageCompanyOperationTypeResponse> result = new ArrayList<>();
			for(final TypeVoyageOperation entity: findEntities) {
				final VoyageCompanyOperationTypeResponse resp = new VoyageCompanyOperationTypeResponse();
				resp.setCode(entity.getCode());
				resp.setDescription(entity.getDescription());
				resp.setName(entity.getName());
				resp.setId(entity.getId());
				result.add(resp);
			}
			return result;
		}
		
		return new ArrayList<>();
	}

	@Override
	@Transactional
	public VoyageCompanyOperationTypeResponse save(VoyageCompanyOperationTypeRequest request) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	@Transactional
	public VoyageCompanyOperationTypeResponse update(Long id, VoyageCompanyOperationTypeRequest request) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	@Transactional
	public void delete(Long id) {
		// TODO Auto-generated method stub
		
	}

}
