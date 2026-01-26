package anagrafica.dto.geography;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProvinciaDTO {
    @JsonProperty("nome")
    private String nome;

    @JsonProperty("codice")
    private String codice;

    @JsonProperty("sigla")
    private String sigla;
}
