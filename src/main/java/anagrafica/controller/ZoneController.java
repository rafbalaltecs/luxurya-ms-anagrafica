package anagrafica.controller;

import anagrafica.dto.agent.AgentRequest;
import anagrafica.dto.agent.AgentResponse;
import anagrafica.dto.company.CompanyResponse;
import anagrafica.dto.zone.ZoneRequest;
import anagrafica.dto.zone.ZoneResponse;
import anagrafica.service.zone.ZoneService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.repository.query.Param;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/zone")
@Tag(name = "Gestione Zone")
@Slf4j
public class ZoneController {
    private final ZoneService zoneService;

    public ZoneController(ZoneService zoneService) {
        this.zoneService = zoneService;
    }

    @GetMapping(value = "", produces = org.springframework.http.MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<List<ZoneResponse>> findAll(@Param("offset") Integer offset, @Param("limit") Integer limit){
        return ResponseEntity.ok(zoneService.findAll());
    }

    @GetMapping(value = "/{id}/companies", produces = org.springframework.http.MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<List<CompanyResponse>> findAllCompanyFromZoneId(@PathVariable("id") Long id){
        return ResponseEntity.ok(zoneService.findAllCompanyFromZoneId(id));
    }



    @PostMapping(value = "", produces = org.springframework.http.MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<ZoneResponse> save(@RequestBody final ZoneRequest request){
        return ResponseEntity.ok(zoneService.create(request));
    }

    @PutMapping(value = "/{id}", produces = org.springframework.http.MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<ZoneResponse> update(
            @PathVariable("id") Long id,
            @RequestBody final ZoneRequest request){
        return ResponseEntity.ok(zoneService.update(id, request));
    }

    @DeleteMapping(value = "/{id}", produces = org.springframework.http.MediaType.APPLICATION_JSON_UTF8_VALUE)
    public void delete(@PathVariable("id") Long id){
        zoneService.delete(id);
    }
}
