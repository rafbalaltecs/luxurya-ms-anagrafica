package anagrafica.service.voyage;

import java.util.List;

import anagrafica.dto.agent.AgentConfigurationVoyageResponse;
import anagrafica.dto.typedocument.TypeDocumentVoyageResponse;
import anagrafica.dto.typepayment.TypePaymentResponse;
import anagrafica.dto.voyage.VoyageClientResponse;
import anagrafica.dto.voyage.VoyageCompanyResponse;
import anagrafica.dto.voyage.VoyageConfigurationRequest;
import anagrafica.dto.voyage.VoyageCustomerFromZoneResponse;
import anagrafica.dto.voyage.VoyageCustomerStatusAgentResponse;
import anagrafica.dto.voyage.VoyageGeoRequest;
import anagrafica.dto.voyage.VoyageOperationCompletedRequest;
import anagrafica.dto.voyage.VoyageOperationRemoveRequest;
import anagrafica.dto.voyage.VoyageOperationRequest;
import anagrafica.dto.voyage.VoyageOperationResponse;
import anagrafica.dto.voyage.VoyageRequest;
import anagrafica.dto.voyage.VoyageResponse;

public interface VoyageService {
	
	List<VoyageResponse> findAll(final Integer offset, final Integer limit);
	VoyageResponse findById(final Long id);
	List<VoyageResponse> findByAgentId(final Long idAgent);
	VoyageResponse create(final VoyageRequest request);
	VoyageResponse update(final Long id, final VoyageRequest request);
	void delete(final Long id);
	
	List<VoyageClientResponse> findListClientsFromVoyageId(final Long voyageId);
	
	VoyageCustomerStatusAgentResponse customerVisitFromVoyageId(final Long voyageId);
	
	VoyageCustomerFromZoneResponse customerToVisitFromCurrentVoyage();
	
	void voyageOperation(final VoyageOperationRequest request);
	void voyageOperationCompleted(final VoyageOperationCompletedRequest request);
	void removevoyageOperation(final VoyageOperationRemoveRequest request);
	
	void createConfigurationVoyage(final VoyageConfigurationRequest request);
	void updateConfigurationVoyage(final Long id, final VoyageConfigurationRequest request);
	
	List<VoyageOperationResponse> findAllOperationFormVoyageId(final Long voyageId, final Integer offset, final Integer limit);
	
	VoyageCompanyResponse findVoyageCompany(final Long voyageId, final Long companyId);
	void addToCompanyToVoyage(final Long voyageId, final Long companyId);
	
	List<TypePaymentResponse> findAllTypePayment();
	List<TypeDocumentVoyageResponse> findAllDocuments();
	
<<<<<<< HEAD
	void addDocumentToVoyage(final Long voyageId, final Long documentId, final Long typeDocumentVoyage);
=======
	List<AgentConfigurationVoyageResponse> generateGeoZones(final VoyageGeoRequest request);
	
	List<AgentConfigurationVoyageResponse> generateGeoZones(final VoyageGeoRequest request, final Boolean persistence);
	void changeConfigurationVoyage(final VoyageConfigurationRequest request);
	
>>>>>>> 3d94d6e872c0e01f7a595079a94e92c1f6b5b9f4
}
