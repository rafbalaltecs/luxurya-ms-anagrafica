package anagrafica.controller;


import anagrafica.client.response.geo.GeocodingResult;
import anagrafica.dto.agent.AgentResponse;
import anagrafica.dto.company.CompanyInfoResponse;
import anagrafica.dto.company.CompanyRequest;
import anagrafica.dto.company.CompanyResponse;
import anagrafica.dto.company.CompanyTypeResponse;
import anagrafica.service.company.CompanyService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;

import org.apache.commons.lang3.StringUtils;
import org.springframework.data.repository.query.Param;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/company")
@Tag(name = "Gestione Aziende Clienti")
@Slf4j
public class CompanyController {

    private final CompanyService companyService;

    public CompanyController(CompanyService companyService) {
        this.companyService = companyService;
    }

    @GetMapping(value = "", produces = org.springframework.http.MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<List<CompanyResponse>> findAll(@Param("offset") Integer offset, @Param("limit") Integer limit,
    		@RequestParam(name = "name", required = false) final String name){
    	if(StringUtils.isEmpty(name)) {
    		return ResponseEntity.ok(companyService.findAll(offset, limit));
    	}
    	return ResponseEntity.ok(companyService.findAll(offset, limit, name));
    }

    @GetMapping(value = "/{id}/agents", produces = org.springframework.http.MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<List<AgentResponse>> findAllAgentsFromCompanyId(
            @PathVariable("id") Long id
    ){
        return ResponseEntity.ok(companyService.findAllAgentsFromCompanyId(id));
    }
    
    @GetMapping(value = "/{id}/stock-movement", produces = org.springframework.http.MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<CompanyInfoResponse> findAllStockFromCompanyId(
            @PathVariable("id") Long id
    ){
        return ResponseEntity.ok(companyService.findAllStockFromCompany(id));
    }
    
    @GetMapping(value = "/{id}/geo", produces = org.springframework.http.MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<GeocodingResult> latlnt(
            @PathVariable("id") Long id
    ){
        return ResponseEntity.ok(companyService.findGeofromcompanyId(id));
    }
    
    @GetMapping(value = "/types", produces = org.springframework.http.MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<List<CompanyTypeResponse>> findTypes(){
        return ResponseEntity.ok(companyService.findAllType());
    }
    
    @PostMapping(value = "/sync-geo", produces = org.springframework.http.MediaType.APPLICATION_JSON_UTF8_VALUE)
    public void syncGeo(){
      companyService.syncGeoCompany();
    }

    @PostMapping(value = "", produces = org.springframework.http.MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<CompanyResponse> save(@RequestBody final CompanyRequest request){
        return ResponseEntity.ok(companyService.create(request));
    }

    @PutMapping(value = "/{id}", produces = org.springframework.http.MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<CompanyResponse> update(
            @PathVariable("id") Long id,
            @RequestBody final CompanyRequest request){
        return ResponseEntity.ok(companyService.update(id, request));
    }

    @DeleteMapping(value = "/{id}", produces = org.springframework.http.MediaType.APPLICATION_JSON_UTF8_VALUE)
    public void delete(@PathVariable("id") Long id){
        companyService.delete(id);
    }

}
