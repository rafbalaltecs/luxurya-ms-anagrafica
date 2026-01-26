package anagrafica.controller;

import anagrafica.exception.RestException;
import anagrafica.service.geography.GeographyImportService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/api/admin/geography")
public class GeographyImportController {
    private final GeographyImportService geographyImportService;

    public GeographyImportController(GeographyImportService geographyImportService) {
        this.geographyImportService = geographyImportService;
    }

    @PostMapping("/import-comuni")
    public ResponseEntity<String> importComuni() {
        log.info("Richiesta importazione comuni ricevuta");
        try {
            String result = geographyImportService.importAllComuni();
            return ResponseEntity.ok(result);
        } catch (Exception e) {
            log.error("Errore durante l'importazione", e);
            throw new RestException(e.getMessage());
        }
    }

}
