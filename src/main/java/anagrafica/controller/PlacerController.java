package anagrafica.controller;

import anagrafica.dto.placer.PlacerRequest;
import anagrafica.dto.placer.PlacerResponse;
import anagrafica.dto.zone.ZoneResponse;
import anagrafica.service.placer.PlacerService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/placer")
@Tag(name = "Gestione Piazzatori")
@Slf4j
public class PlacerController {
    private final PlacerService placerService;

    public PlacerController(PlacerService placerService) {
        this.placerService = placerService;
    }

    @GetMapping(value = "", produces = org.springframework.http.MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<List<PlacerResponse>> findAll(
            @RequestParam("offset") Integer offset,
            @RequestParam("limit") Integer limit
    ){
        return ResponseEntity.ok(placerService.findAll(offset, limit));
    }

    @GetMapping(value = "/{id}", produces = org.springframework.http.MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<List<ZoneResponse>> findAllZoneFromPlacerId(
            @PathVariable("id") Long id
    ){
        return ResponseEntity.ok(
                placerService.findAllZonesFromPlacer(id)
        );
    }

    @PutMapping(value = "/{id}/zone/{zoneId}", produces = org.springframework.http.MediaType.APPLICATION_JSON_UTF8_VALUE)
    public void addZoneToPlacer(
            @PathVariable("id") Long placerId,
            @PathVariable("zoneId") Long zoneId
    ){
        placerService.addZoneToPlacer(placerId, zoneId);
    }

    @DeleteMapping(value = "/{id}/zone/{zoneId}", produces = org.springframework.http.MediaType.APPLICATION_JSON_UTF8_VALUE)
    public void deleteZoneToAgent(
            @PathVariable("id") Long placerId,
            @PathVariable("zoneId") Long zoneId
    ){
        placerService.removeZoneToPlacer(placerId, zoneId);
    }

    @PostMapping(value = "", produces = org.springframework.http.MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<PlacerResponse> create(
            @RequestBody final PlacerRequest placerRequest
            ){
        return ResponseEntity.ok(placerService.create(placerRequest));
    }

    @PutMapping(value = "/{id}", produces = org.springframework.http.MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<PlacerResponse> update(
            @PathVariable("id") Long id,
            @RequestBody final PlacerRequest placerRequest
    ){
        return ResponseEntity.ok(placerService.update(id, placerRequest));
    }

    @DeleteMapping(value = "/{id}", produces = org.springframework.http.MediaType.APPLICATION_JSON_UTF8_VALUE)
    public void delete(@PathVariable("id") Long id){
        placerService.delete(id);
    }

}
