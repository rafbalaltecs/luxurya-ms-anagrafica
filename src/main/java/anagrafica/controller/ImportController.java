package anagrafica.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import anagrafica.dto.address.AddressRequest;
import anagrafica.dto.company.CompanyRequest;
import anagrafica.dto.zone.ZoneRequest;
import anagrafica.dto.zone.ZoneResponse;
import anagrafica.entity.Citta;
import anagrafica.exception.RestException;
import anagrafica.legacy.ClienteFileLegacyImportService;
import anagrafica.legacy.ClienteLegacyDTO;
import anagrafica.service.company.CompanyService;
import anagrafica.service.zone.ZoneService;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/api/ext/import")
public class ImportController {
	private final ClienteFileLegacyImportService service;
	private final CompanyService companyService;
	private final ZoneService zoneService;
	
	public ImportController(ClienteFileLegacyImportService service, CompanyService companyService, ZoneService zoneService) {
		this.service = service;
		this.companyService = companyService;
		this.zoneService = zoneService;
	}
	
	@PostMapping("/import-clients")
    public ResponseEntity<Integer> importComuni() {
        log.info("Richiesta importazione clienti ricevuta");
        try {
        	
    	  int[] totale = {0};
        	
    	  service.readClientiInBatches("/home/raffaele/Downloads/clienti_luxurya.csv", batch -> {
              totale[0] += batch.size();
              log.info("Batch ricevuto: " + batch.size()
                      + " record — totale finora: " + totale[0]);
              	for(final ClienteLegacyDTO dto: batch) {
              		
              		try {
              			final CompanyRequest cr = new CompanyRequest();
                  		cr.setPiva(dto.getPiva());
                  		cr.setCodeLegacy(dto.getCodice());
                  		cr.setTelephone(dto.getTelefono1());
                  		cr.setTelephoneTwo(dto.getTelefono2());
                  		cr.setName(dto.getRagioneSociale());
                  		
                  		final AddressRequest addressRequest = new AddressRequest();
                  		addressRequest.setAddress(dto.getIndirizzo());
                  		addressRequest.setCap(dto.getCap());
                  		addressRequest.setPr(dto.getProv());
                  		
                  		
                  		final ZoneResponse zoneResp = zoneService.findByName(dto.getZona());
                  		if(zoneResp == null) {
                  			final ZoneRequest zoneRequest = new ZoneRequest();
                  			zoneRequest.setName(dto.getZona());
                  			
                  			final Citta city = companyService.findCittaExistName(dto.getCitta());
                  			zoneRequest.setCityId(city != null ? city.getId() : null);
                  			
                  			ZoneResponse zoneResponseNew = zoneService.createForImport(zoneRequest);
                  			addressRequest.setZoneId(zoneResponseNew.getId());
                  		}else {
                  			addressRequest.setZoneId(zoneResp.getId());
                  		}
                  		
                  		cr.setAddress(addressRequest);
                  		
                  		log.info(dto.getPiva());
                  		
                  		companyService.createFromLegacy(cr);
              		}catch (Exception e) {
						log.error(e.getMessage());
					}
              	}
              // Qui inserisci la logica di persistenza, es:
              
          });
            return ResponseEntity.ok(totale[0]);
        } catch (Exception e) {
            log.error("Errore durante l'importazione", e);
            throw new RestException(e.getMessage());
        }
    }
}
