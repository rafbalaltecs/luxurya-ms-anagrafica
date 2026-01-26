package anagrafica.dto.geography;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ComuneImportDTO {
    @JsonProperty("nome")
    private String nome;

    @JsonProperty("codice")
    private String codice;

    @JsonProperty("codiceCatastale")
    private String codiceCatastale;

    @JsonProperty("cap")
    private List<String> cap; // Può avere più CAP

    @JsonProperty("popolazione")
    private Integer popolazione;

    @JsonProperty("provincia")
    private ProvinciaDTO provincia;

    @JsonProperty("regione")
    private RegioneDTO regione;
}
