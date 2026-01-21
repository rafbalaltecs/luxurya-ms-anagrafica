package anagrafica.client.response.execusbi;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;


@JsonIgnoreProperties(ignoreUnknown = true)
public class ExecusBICompanySocioResponse {
    @JsonProperty("chiave_socio")
    private String chiaveSocio;

    @JsonProperty("cd_valuta_cs")
    private String cdValutaCs;

    @JsonProperty("ammontare_cs")
    private Double ammontareCs;

    @JsonProperty("codice_prop")
    private String codiceProp;

    @JsonProperty("id_quota")
    private Long idQuota;

    @JsonProperty("valore_nominale")
    private Double valoreNominale;

    @JsonProperty("valore_versato")
    private Double valoreVersato;

    @JsonProperty("percentuale_quota_parziale")
    private String percentualeQuotaParziale;

    @JsonProperty("codicefiscale")
    private String codiceFiscale;

    @JsonProperty("denominazione")
    private String denominazione;

    @JsonProperty("cognome")
    private String cognome;

    @JsonProperty("nome")
    private String nome;

    @JsonProperty("comune")
    private String comune;

    @JsonProperty("provincia")
    private String provincia;

    @JsonProperty("via")
    private String via;

    @JsonProperty("civico")
    private String civico;

    @JsonProperty("cap")
    private String cap;

    @JsonProperty("flag_rappresentante")
    private String flagRappresentante;

    @JsonProperty("flag_riconferma_es")
    private String flagRiconfermaEs;

    @JsonProperty("dt_deposito_es")
    private String dtDepositoEs;

    @JsonProperty("flag_anomalia")
    private String flagAnomalia;

    @JsonProperty("flag_proprieta")
    private String flagProprieta;

    @JsonProperty("flag_protesti")
    private String flagProtesti;

    @JsonProperty("flag_pregiudizievoli")
    private String flagPregiudizievoli;

    @JsonProperty("flag_procedure")
    private String flagProcedure;

    @JsonProperty("flag_domande_concordato")
    private String flagDomandeConcordato;

    public String getChiaveSocio() {
        return chiaveSocio;
    }

    public void setChiaveSocio(String chiaveSocio) {
        this.chiaveSocio = chiaveSocio;
    }

    public String getCdValutaCs() {
        return cdValutaCs;
    }

    public void setCdValutaCs(String cdValutaCs) {
        this.cdValutaCs = cdValutaCs;
    }

    public Double getAmmontareCs() {
        return ammontareCs;
    }

    public void setAmmontareCs(Double ammontareCs) {
        this.ammontareCs = ammontareCs;
    }

    public String getCodiceProp() {
        return codiceProp;
    }

    public void setCodiceProp(String codiceProp) {
        this.codiceProp = codiceProp;
    }

    public Long getIdQuota() {
        return idQuota;
    }

    public void setIdQuota(Long idQuota) {
        this.idQuota = idQuota;
    }

    public Double getValoreNominale() {
        return valoreNominale;
    }

    public void setValoreNominale(Double valoreNominale) {
        this.valoreNominale = valoreNominale;
    }

    public Double getValoreVersato() {
        return valoreVersato;
    }

    public void setValoreVersato(Double valoreVersato) {
        this.valoreVersato = valoreVersato;
    }

    public String getPercentualeQuotaParziale() {
        return percentualeQuotaParziale;
    }

    public void setPercentualeQuotaParziale(String percentualeQuotaParziale) {
        this.percentualeQuotaParziale = percentualeQuotaParziale;
    }

    public String getCodiceFiscale() {
        return codiceFiscale;
    }

    public void setCodiceFiscale(String codiceFiscale) {
        this.codiceFiscale = codiceFiscale;
    }

    public String getDenominazione() {
        return denominazione;
    }

    public void setDenominazione(String denominazione) {
        this.denominazione = denominazione;
    }

    public String getCognome() {
        return cognome;
    }

    public void setCognome(String cognome) {
        this.cognome = cognome;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getComune() {
        return comune;
    }

    public void setComune(String comune) {
        this.comune = comune;
    }

    public String getProvincia() {
        return provincia;
    }

    public void setProvincia(String provincia) {
        this.provincia = provincia;
    }

    public String getVia() {
        return via;
    }

    public void setVia(String via) {
        this.via = via;
    }

    public String getCivico() {
        return civico;
    }

    public void setCivico(String civico) {
        this.civico = civico;
    }

    public String getCap() {
        return cap;
    }

    public void setCap(String cap) {
        this.cap = cap;
    }

    public String getFlagRappresentante() {
        return flagRappresentante;
    }

    public void setFlagRappresentante(String flagRappresentante) {
        this.flagRappresentante = flagRappresentante;
    }

    public String getFlagRiconfermaEs() {
        return flagRiconfermaEs;
    }

    public void setFlagRiconfermaEs(String flagRiconfermaEs) {
        this.flagRiconfermaEs = flagRiconfermaEs;
    }

    public String getDtDepositoEs() {
        return dtDepositoEs;
    }

    public void setDtDepositoEs(String dtDepositoEs) {
        this.dtDepositoEs = dtDepositoEs;
    }

    public String getFlagAnomalia() {
        return flagAnomalia;
    }

    public void setFlagAnomalia(String flagAnomalia) {
        this.flagAnomalia = flagAnomalia;
    }

    public String getFlagProprieta() {
        return flagProprieta;
    }

    public void setFlagProprieta(String flagProprieta) {
        this.flagProprieta = flagProprieta;
    }

    public String getFlagProtesti() {
        return flagProtesti;
    }

    public void setFlagProtesti(String flagProtesti) {
        this.flagProtesti = flagProtesti;
    }

    public String getFlagPregiudizievoli() {
        return flagPregiudizievoli;
    }

    public void setFlagPregiudizievoli(String flagPregiudizievoli) {
        this.flagPregiudizievoli = flagPregiudizievoli;
    }

    public String getFlagProcedure() {
        return flagProcedure;
    }

    public void setFlagProcedure(String flagProcedure) {
        this.flagProcedure = flagProcedure;
    }

    public String getFlagDomandeConcordato() {
        return flagDomandeConcordato;
    }

    public void setFlagDomandeConcordato(String flagDomandeConcordato) {
        this.flagDomandeConcordato = flagDomandeConcordato;
    }
}
