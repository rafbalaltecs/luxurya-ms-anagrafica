package anagrafica.client.response.execusbi;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class ExecusBICompanyEsponenteResponse {
    @JsonProperty("chiave_esponente")
    private String chiaveEsponente;

    @JsonProperty("nome")
    private String nome;

    @JsonProperty("cognome")
    private String cognome;

    @JsonProperty("denominazione")
    private String denominazione;

    @JsonProperty("code_carica")
    private String codeCarica;

    @JsonProperty("codfiscale_esponente")
    private String codfiscaleEsponente;

    @JsonProperty("data_atto_nomina")
    private String dataAttoNomina;

    @JsonProperty("durata")
    private String durata;

    @JsonProperty("flag_legale_rapp")
    private String flagLegaleRapp;

    @JsonProperty("cciaa")
    private String cciaa;

    @JsonProperty("rea")
    private Integer rea;

    @JsonProperty("sesso")
    private String sesso;

    @JsonProperty("data_nascita")
    private String dataNascita;

    @JsonProperty("comune_nascita")
    private String comuneNascita;

    @JsonProperty("codice_pcor_recente")
    private String codicePcorRecente;

    public String getChiaveEsponente() {
        return chiaveEsponente;
    }

    public void setChiaveEsponente(String chiaveEsponente) {
        this.chiaveEsponente = chiaveEsponente;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getCognome() {
        return cognome;
    }

    public void setCognome(String cognome) {
        this.cognome = cognome;
    }

    public String getDenominazione() {
        return denominazione;
    }

    public void setDenominazione(String denominazione) {
        this.denominazione = denominazione;
    }

    public String getCodeCarica() {
        return codeCarica;
    }

    public void setCodeCarica(String codeCarica) {
        this.codeCarica = codeCarica;
    }

    public String getCodfiscaleEsponente() {
        return codfiscaleEsponente;
    }

    public void setCodfiscaleEsponente(String codfiscaleEsponente) {
        this.codfiscaleEsponente = codfiscaleEsponente;
    }

    public String getDataAttoNomina() {
        return dataAttoNomina;
    }

    public void setDataAttoNomina(String dataAttoNomina) {
        this.dataAttoNomina = dataAttoNomina;
    }

    public String getDurata() {
        return durata;
    }

    public void setDurata(String durata) {
        this.durata = durata;
    }

    public String getFlagLegaleRapp() {
        return flagLegaleRapp;
    }

    public void setFlagLegaleRapp(String flagLegaleRapp) {
        this.flagLegaleRapp = flagLegaleRapp;
    }

    public String getCciaa() {
        return cciaa;
    }

    public void setCciaa(String cciaa) {
        this.cciaa = cciaa;
    }

    public Integer getRea() {
        return rea;
    }

    public void setRea(Integer rea) {
        this.rea = rea;
    }

    public String getSesso() {
        return sesso;
    }

    public void setSesso(String sesso) {
        this.sesso = sesso;
    }

    public String getDataNascita() {
        return dataNascita;
    }

    public void setDataNascita(String dataNascita) {
        this.dataNascita = dataNascita;
    }

    public String getComuneNascita() {
        return comuneNascita;
    }

    public void setComuneNascita(String comuneNascita) {
        this.comuneNascita = comuneNascita;
    }

    public String getCodicePcorRecente() {
        return codicePcorRecente;
    }

    public void setCodicePcorRecente(String codicePcorRecente) {
        this.codicePcorRecente = codicePcorRecente;
    }
}
