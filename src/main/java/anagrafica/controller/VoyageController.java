package anagrafica.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

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
import anagrafica.service.voyage.VoyageService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/api/voyage")
@Tag(name = "Gestione Viaggi")
@Slf4j
public class VoyageController {
	private final VoyageService voyageService;

	public VoyageController(VoyageService voyageService) {
		this.voyageService = voyageService;
	}
	
	@GetMapping(value = "", produces = org.springframework.http.MediaType.APPLICATION_JSON_UTF8_VALUE)
	public ResponseEntity<List<VoyageResponse>> findAll(@RequestParam("offset") Integer offset, @RequestParam("limit") Integer limit){
		return ResponseEntity.ok(voyageService.findAll(offset, limit));
	}
	
	@GetMapping(value = "/{id}/operations", produces = org.springframework.http.MediaType.APPLICATION_JSON_UTF8_VALUE)
	public ResponseEntity<List<VoyageOperationResponse>> findAllOperations(
			@PathVariable("id") Long id,
			@RequestParam("offset") Integer offset, 
			@RequestParam("limit") Integer limit){
		return ResponseEntity.ok(voyageService.findAllOperationFormVoyageId(id, offset, limit));
	}
	
	@GetMapping(value = "/{id}", produces = org.springframework.http.MediaType.APPLICATION_JSON_UTF8_VALUE)
	public ResponseEntity<VoyageResponse> findById(@PathVariable("id") Long id){
		return ResponseEntity.ok(voyageService.findById(id));
	}
	
	@GetMapping(value = "/{id}/clients", produces = org.springframework.http.MediaType.APPLICATION_JSON_UTF8_VALUE)
	public ResponseEntity<List<VoyageClientResponse>> findClientsFromVoyageId(@PathVariable("id") Long id){
		return ResponseEntity.ok(voyageService.findListClientsFromVoyageId(id));
	}
	
	@GetMapping(value = "/{id}/customer-visit", produces = org.springframework.http.MediaType.APPLICATION_JSON_UTF8_VALUE)
	public ResponseEntity<VoyageCustomerStatusAgentResponse> findCustomersVisit(@PathVariable("id") Long id){
		return ResponseEntity.ok(voyageService.customerVisitFromVoyageId(id));
	}
	
	@GetMapping(value = "/customer-to-visit", produces = org.springframework.http.MediaType.APPLICATION_JSON_UTF8_VALUE)
	public ResponseEntity<VoyageCustomerFromZoneResponse> findCustomersToVisit(){
		return ResponseEntity.ok(voyageService.customerToVisitFromCurrentVoyage());
	}
	
	@PostMapping(value ="/operation", produces = org.springframework.http.MediaType.APPLICATION_JSON_UTF8_VALUE)
	public void voyageOperationSave(@RequestBody final VoyageOperationRequest request) {
		voyageService.voyageOperation(request);
	}
	
	@PostMapping(value ="/configuration", produces = org.springframework.http.MediaType.APPLICATION_JSON_UTF8_VALUE)
	public void voyageConfiguration(@RequestBody final VoyageConfigurationRequest request) {
		voyageService.createConfigurationVoyage(request);
	}
	
	@PutMapping(value ="/configuration-modify", produces = org.springframework.http.MediaType.APPLICATION_JSON_UTF8_VALUE)
	public void changeConfigurationVoyage(
			@RequestBody final VoyageConfigurationRequest request
			) {
		voyageService.changeConfigurationVoyage(request);
	}
	
	@PostMapping(value ="/configuration/geo", produces = org.springframework.http.MediaType.APPLICATION_JSON_UTF8_VALUE)
	public ResponseEntity<List<AgentConfigurationVoyageResponse>> voyageConfigurationgeo(
			@RequestBody final VoyageGeoRequest request,
			@RequestParam(name = "save", required = false) Boolean save) {
			if(save != null && save.equals(Boolean.TRUE)) {
				return ResponseEntity.ok(voyageService.generateGeoZones(request, save));
			}else {
				return ResponseEntity.ok(voyageService.generateGeoZones(request));
			}
	}
	
	@PutMapping(value ="/configuration/{id}", produces = org.springframework.http.MediaType.APPLICATION_JSON_UTF8_VALUE)
	public void voyageUpdateConfiguration(@PathVariable("id") Long id, @RequestBody final VoyageConfigurationRequest request) {
		voyageService.updateConfigurationVoyage(id, request);
	}
	
	@PostMapping(value ="/operation/completed", produces = org.springframework.http.MediaType.APPLICATION_JSON_UTF8_VALUE)
	public void voyageOperationCompletedSave(@RequestBody final VoyageOperationCompletedRequest request) {
		voyageService.voyageOperationCompleted(request);
	}
	
	@PostMapping(value ="/operation/remove", produces = org.springframework.http.MediaType.APPLICATION_JSON_UTF8_VALUE)
	public void voyageOperationRemove(@RequestBody final VoyageOperationRemoveRequest request) {
		voyageService.removevoyageOperation(request);
	}
	
	
	@PostMapping(value = "", produces = org.springframework.http.MediaType.APPLICATION_JSON_UTF8_VALUE)
	public ResponseEntity<VoyageResponse> create(@RequestBody final VoyageRequest voyageRequest){
		return ResponseEntity.ok(voyageService.create(voyageRequest));
	}
	
	@PutMapping(value = "/{id}", produces = org.springframework.http.MediaType.APPLICATION_JSON_UTF8_VALUE)
	public ResponseEntity<VoyageResponse> update(
			@PathVariable("id") Long id,
			@RequestBody final VoyageRequest voyageRequest){
		return ResponseEntity.ok(voyageService.update(id, voyageRequest));
	}
	
	@DeleteMapping(value = "/{id}", produces = org.springframework.http.MediaType.APPLICATION_JSON_UTF8_VALUE)
	public void delete(@PathVariable("id") Long id){
		voyageService.delete(id);
	}
	
	@GetMapping(value = "/{id}/company/{idCompany}/voyage", produces = org.springframework.http.MediaType.APPLICATION_JSON_UTF8_VALUE)
	public ResponseEntity<VoyageCompanyResponse> findVoyageCompany(
			@PathVariable("id") Long id,
			@PathVariable("idCompany") Long idCompany){
		return ResponseEntity.ok(voyageService.findVoyageCompany(id, idCompany));
	}
	
	@PostMapping(value = "/{id}/company/{idCompany}/voyage", produces = org.springframework.http.MediaType.APPLICATION_JSON_UTF8_VALUE)
	public void addCompanyToVoyageCompany(@PathVariable("id") Long id,
			@PathVariable("idCompany") Long idCompany){
		voyageService.addToCompanyToVoyage(id, idCompany);
	}
	
	//Find Select For VoyageCompany
	@GetMapping(value = "/type-payment", produces = org.springframework.http.MediaType.APPLICATION_JSON_UTF8_VALUE)
	public ResponseEntity<List<TypePaymentResponse>> findAllTypePayment(){
		return ResponseEntity.ok(voyageService.findAllTypePayment());
	}
	
	@GetMapping(value = "/type-document", produces = org.springframework.http.MediaType.APPLICATION_JSON_UTF8_VALUE)
	public ResponseEntity<List<TypeDocumentVoyageResponse>> findAllTypeDocuments(){
		return ResponseEntity.ok(voyageService.findAllDocuments());
	}
	
}
