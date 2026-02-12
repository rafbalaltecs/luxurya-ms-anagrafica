package anagrafica.controller;

import java.util.List;

import org.springframework.data.repository.query.Param;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import anagrafica.dto.agent.AgentRequest;
import anagrafica.dto.agent.AgentResponse;
import anagrafica.dto.voyage.VoyageResponse;
import anagrafica.dto.zone.ZoneResponse;
import anagrafica.service.agent.AgentService;
import anagrafica.service.voyage.VoyageService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/api/agent")
@Tag(name = "Gestione Agenti")
@Slf4j
public class AgentController {
	
    private final AgentService agentService;
    private final VoyageService voyageService;

    public AgentController(AgentService agentService, VoyageService voyageService) {
        this.agentService = agentService;
        this.voyageService = voyageService;
    }

    @GetMapping(value = "", produces = org.springframework.http.MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<List<AgentResponse>> findAll(@Param("offset") Integer offset, @Param("limit") Integer limit){
        return ResponseEntity.ok(agentService.findAll(offset, limit));
    }

    @GetMapping(value = "/{id}/zones", produces = org.springframework.http.MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<List<ZoneResponse>> findAllZones(@PathVariable("id") Long id){
        return ResponseEntity.ok(agentService.findZonesFromAgent(id));
    }

    @PutMapping(value = "/{id}/zone/{zoneId}", produces = org.springframework.http.MediaType.APPLICATION_JSON_UTF8_VALUE)
    public void addZoneToAgent(
            @PathVariable("id") Long agentId,
            @PathVariable("zoneId") Long zoneId
    ){
        agentService.addZoneToAgent(agentId, zoneId);
    }


    @DeleteMapping(value = "/{id}/zone/{zoneId}", produces = org.springframework.http.MediaType.APPLICATION_JSON_UTF8_VALUE)
    public void deleteZoneToAgent(
            @PathVariable("id") Long agentId,
            @PathVariable("zoneId") Long zoneId
    ){
        agentService.removeZoneToAgent(agentId, zoneId);
    }

    @PostMapping(value = "", produces = org.springframework.http.MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<AgentResponse> save(@RequestBody final AgentRequest request){
        return ResponseEntity.ok(agentService.create(request));
    }

    @PutMapping(value = "/{id}", produces = org.springframework.http.MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<AgentResponse> update(
            @PathVariable("id") Long id,
            @RequestBody final AgentRequest request){
        return ResponseEntity.ok(agentService.update(id, request));
    }

    @DeleteMapping(value = "/{id}", produces = org.springframework.http.MediaType.APPLICATION_JSON_UTF8_VALUE)
    public void delete(@PathVariable("id") Long id){
        agentService.delete(id);
    }
    
    @GetMapping(value = "/{id}/voyages", produces = org.springframework.http.MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<List<VoyageResponse>> findAllVoyages(@PathVariable("id") Long id){
        return ResponseEntity.ok(voyageService.findByAgentId(id));
    }

}
