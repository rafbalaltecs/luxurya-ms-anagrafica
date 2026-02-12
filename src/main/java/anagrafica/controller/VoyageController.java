package anagrafica.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import anagrafica.dto.voyage.VoyageRequest;
import anagrafica.dto.voyage.VoyageResponse;
import anagrafica.service.voyage.VoyageService;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
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
	
	@GetMapping(value = "/{id}", produces = org.springframework.http.MediaType.APPLICATION_JSON_UTF8_VALUE)
	public ResponseEntity<VoyageResponse> findById(@PathVariable("id") Long id){
		return ResponseEntity.ok(voyageService.findById(id));
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
	
	
}
