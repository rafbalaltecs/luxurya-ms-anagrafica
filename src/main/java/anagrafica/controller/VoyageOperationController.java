package anagrafica.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import anagrafica.dto.voyage.VoyageCompanyOperationTypeResponse;
import anagrafica.service.voyage.VoyageCompanyOperationTypeService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/api/voyage-operation")
@Tag(name = "Gestione Operazione Viaggi")
@Slf4j
public class VoyageOperationController {
	private final VoyageCompanyOperationTypeService voyageCompanyOperationService;
	
	public VoyageOperationController(VoyageCompanyOperationTypeService voyageCompanyOperationService) {
		this.voyageCompanyOperationService = voyageCompanyOperationService;
	}
	
	@GetMapping(value = "/type-operation", produces = org.springframework.http.MediaType.APPLICATION_JSON_UTF8_VALUE)
	public ResponseEntity<List<VoyageCompanyOperationTypeResponse>> findAllTypeOperation(){
		return ResponseEntity.ok(voyageCompanyOperationService.findAll());
	}
}
