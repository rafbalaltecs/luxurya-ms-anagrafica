package anagrafica.client.response.execusbi;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class ExecusBIStoricoDipendentiResponse {
    @JsonProperty("DataAggiornamento")
    public String dataAggiornamento;

    @JsonProperty("Dipendenti")
    public Integer dipendenti;

    @JsonProperty("Collaboratori")
    public Integer collaboratori;

    public String getDataAggiornamento() {
        return dataAggiornamento;
    }

    public void setDataAggiornamento(String dataAggiornamento) {
        this.dataAggiornamento = dataAggiornamento;
    }

    public Integer getDipendenti() {
        return dipendenti;
    }

    public void setDipendenti(Integer dipendenti) {
        this.dipendenti = dipendenti;
    }

    public Integer getCollaboratori() {
        return collaboratori;
    }

    public void setCollaboratori(Integer collaboratori) {
        this.collaboratori = collaboratori;
    }
}
