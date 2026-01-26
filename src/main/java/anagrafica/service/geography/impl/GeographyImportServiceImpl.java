package anagrafica.service.geography.impl;

import anagrafica.dto.geography.ComuneImportDTO;
import anagrafica.entity.Citta;
import anagrafica.entity.Provincia;
import anagrafica.entity.Regione;
import anagrafica.repository.geography.CittaRepository;
import anagrafica.repository.geography.ProvinciaRepository;
import anagrafica.repository.geography.RegioneRepository;
import anagrafica.service.geography.GeographyImportService;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
@Slf4j
public class GeographyImportServiceImpl implements GeographyImportService {

    private final RegioneRepository regioneRepository;
    private final ProvinciaRepository provinciaRepository;
    private final CittaRepository cittaRepository;
    private final RestTemplate restTemplate;

    private static final String API_URL = "https://raw.githubusercontent.com/matteocontrini/comuni-json/master/comuni.json";

    public GeographyImportServiceImpl(RegioneRepository regioneRepository, ProvinciaRepository provinciaRepository, CittaRepository cittaRepository, RestTemplate restTemplate) {
        this.regioneRepository = regioneRepository;
        this.provinciaRepository = provinciaRepository;
        this.cittaRepository = cittaRepository;
        this.restTemplate = restTemplate;
    }

    @Override
    @Transactional
    public String importAllComuni() {
        try {
            log.info("Inizio importazione comuni da API esterna...");

            // Scarica i dati dall'API
            ComuneImportDTO[] comuni = restTemplate.getForObject(API_URL, ComuneImportDTO[].class);

            if (comuni == null || comuni.length == 0) {
                return "Nessun comune da importare";
            }

            int importati = 0;
            int saltati = 0;

            for (ComuneImportDTO comuneDTO : comuni) {
                try {
                    // Trova o crea la regione
                    Regione regione = regioneRepository.findByCodice(comuneDTO.getRegione().getCodice())
                            .orElseGet(() -> {
                                Regione newRegione = new Regione();
                                newRegione.setCodice(comuneDTO.getRegione().getCodice());
                                newRegione.setNome(comuneDTO.getRegione().getNome());
                                return regioneRepository.save(newRegione);
                            });

                    // Trova o crea la provincia
                    Provincia provincia = provinciaRepository.findBySigla(comuneDTO.getProvincia().getSigla())
                            .orElseGet(() -> {
                                Provincia newProvincia = new Provincia();
                                newProvincia.setCodice(comuneDTO.getProvincia().getCodice());
                                newProvincia.setNome(comuneDTO.getProvincia().getNome());
                                newProvincia.setSigla(comuneDTO.getProvincia().getSigla());
                                newProvincia.setRegione(regione);
                                return provinciaRepository.save(newProvincia);
                            });

                    // Verifica se il comune esiste già
                    if (cittaRepository.findByCodice(comuneDTO.getCodice()).isPresent()) {
                        saltati++;
                        continue;
                    }

                    // Crea la città
                    Citta citta = Citta.builder()
                            .codice(comuneDTO.getCodice())
                            .nome(comuneDTO.getNome())
                            .codiceCatastale(comuneDTO.getCodiceCatastale())
                            .cap(comuneDTO.getCap() != null && !comuneDTO.getCap().isEmpty()
                                    ? comuneDTO.getCap().get(0) : null)
                            .popolazione(comuneDTO.getPopolazione())
                            .provincia(provincia)
                            .regione(regione)
                            .build();

                    cittaRepository.save(citta);
                    importati++;

                    if (importati % 100 == 0) {
                        log.info("Importati {} comuni...", importati);
                    }

                } catch (Exception e) {
                    log.error("Errore importando comune {}: {}", comuneDTO.getNome(), e.getMessage());
                    saltati++;
                }
            }

            String result = String.format("Importazione completata: %d comuni importati, %d saltati",
                    importati, saltati);
            log.info(result);
            return result;

        } catch (Exception e) {
            log.error("Errore durante l'importazione: {}", e.getMessage());
            throw new RuntimeException("Errore durante l'importazione dei comuni", e);
        }
    }
}
