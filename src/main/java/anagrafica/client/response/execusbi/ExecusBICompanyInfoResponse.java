package anagrafica.client.response.execusbi;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class ExecusBICompanyInfoResponse {

    public ExecusBICompanyInfoResponse() {
    }

    @JsonProperty("CODICEFISCALE")
    public String codiceFiscale;

    @JsonProperty("RAGIONE_SOCIALE")
    public String ragioneSociale;

    @JsonProperty("TOPONIMO")
    public String toponimo;

    @JsonProperty("VIA")
    private String via;

    @JsonProperty("CIVICO")
    private String civico;

    @JsonProperty("CAP")
    private String cap;

    @JsonProperty("LOCALITA")
    private String localita;

    @JsonProperty("SIGLA_PROVINCIA")
    private String siglaProvincia;

    @JsonProperty("REGIONE")
    private String regione;

    @JsonProperty("INDIRIZZO_COMPLETO")
    private String indirizzoCompleto;

    @JsonProperty("COMUNE")
    private String comune;

    @JsonProperty("FORMA_GIURIDICA")
    private String formaGiuridica;

    @JsonProperty("PARTITA_IVA")
    private String partitaIva;

    @JsonProperty("COD_MERCEOLOGICO_ATECO2007")
    private String codAteco2007;

    @JsonProperty("DESC_ATECO2007")
    private String descAteco2007;

    @JsonProperty("STATO_ATTIVITA_INFOCAMERE")
    private String statoAttivita;

    @JsonProperty("DATA_ISCRIZIONE")
    private String dataIscrizione;

    @JsonProperty("DATA_INIZIO_ATTIVITA")
    private String dataInizioAttivita;

    @JsonProperty("TIPO_SEDE")
    private String tipoSede;

    @JsonProperty("CODE_TIPO_SEDE")
    private String codeTipoSede;

    @JsonProperty("PROGRESSIVO_UL")
    private Integer progressivoUl;

    @JsonProperty("NUMERO_SEDI")
    private Integer numeroSedi;

    @JsonProperty("DIPENDENTI")
    private Integer dipendenti;

    @JsonProperty("INDIPENDENTI")
    private Integer indipendenti;

    @JsonProperty("TOTALE")
    private Integer totale;

    @JsonProperty("DATA_CHIUSURA_BILANCIO")
    private String dataChiusuraBilancio;

    @JsonProperty("EBITDA")
    private Long ebitda;

    @JsonProperty("TELEFONO")
    private Boolean telefono;

    @JsonProperty("DESC_PREFISSO")
    private String descPrefisso;

    @JsonProperty("DESC_NUMERO")
    private String descNumero;

    @JsonProperty("URL")
    private String url;

    @JsonProperty("EMAIL_GENERICA")
    private String emailGenerica;

    @JsonProperty("FLAG_PROTESTI")
    private String flagProtesti;

    @JsonProperty("FLAG_PREGIUDIZIEVOLI")
    private String flagPregiudizievoli;

    @JsonProperty("FLAG_PROCEDURE")
    private String flagProcedure;

    @JsonProperty("FLAG_DOMANDE_DI_CONCORDATO")
    private String flagDomandeConcordato;

    @JsonProperty("PARTECIPAZIONI")
    private Integer partecipazioni;

    @JsonProperty("PA")
    private Boolean pa;

    @JsonProperty("PEC")
    private String pec;

    @JsonProperty("ESPONENTE_1")
    private String esponente1;

    @JsonProperty("CARICA_ESPONENTE_1")
    private String caricaEsponente1;

    @JsonProperty("ESPONENTE_2")
    private String esponente2;

    @JsonProperty("CARICA_ESPONENTE_2")
    private String caricaEsponente2;

    @JsonProperty("ESPONENTE_3")
    private String esponente3;

    @JsonProperty("CARICA_ESPONENTE_3")
    private String caricaEsponente3;

    @JsonProperty("ESPONENTE_4")
    private String esponente4;

    @JsonProperty("CARICA_ESPONENTE_4")
    private String caricaEsponente4;

    @JsonProperty("ESPONENTE_5")
    private String esponente5;

    @JsonProperty("CARICA_ESPONENTE_5")
    private String caricaEsponente5;

    @JsonProperty("FLAG_STARTUPINNOVATIVA")
    private String flagStartupInnovativa;

    @JsonProperty("CAP_SOC_VERSATO")
    private String capSocVersato;

    public String getCodiceFiscale() {
        return codiceFiscale;
    }

    public void setCodiceFiscale(String codiceFiscale) {
        this.codiceFiscale = codiceFiscale;
    }

    public String getRagioneSociale() {
        return ragioneSociale;
    }

    public void setRagioneSociale(String ragioneSociale) {
        this.ragioneSociale = ragioneSociale;
    }

    public String getToponimo() {
        return toponimo;
    }

    public void setToponimo(String toponimo) {
        this.toponimo = toponimo;
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

    public String getLocalita() {
        return localita;
    }

    public void setLocalita(String localita) {
        this.localita = localita;
    }

    public String getSiglaProvincia() {
        return siglaProvincia;
    }

    public void setSiglaProvincia(String siglaProvincia) {
        this.siglaProvincia = siglaProvincia;
    }

    public String getRegione() {
        return regione;
    }

    public void setRegione(String regione) {
        this.regione = regione;
    }

    public String getIndirizzoCompleto() {
        return indirizzoCompleto;
    }

    public void setIndirizzoCompleto(String indirizzoCompleto) {
        this.indirizzoCompleto = indirizzoCompleto;
    }

    public String getComune() {
        return comune;
    }

    public void setComune(String comune) {
        this.comune = comune;
    }

    public String getFormaGiuridica() {
        return formaGiuridica;
    }

    public void setFormaGiuridica(String formaGiuridica) {
        this.formaGiuridica = formaGiuridica;
    }

    public String getPartitaIva() {
        return partitaIva;
    }

    public void setPartitaIva(String partitaIva) {
        this.partitaIva = partitaIva;
    }

    public String getCodAteco2007() {
        return codAteco2007;
    }

    public void setCodAteco2007(String codAteco2007) {
        this.codAteco2007 = codAteco2007;
    }

    public String getDescAteco2007() {
        return descAteco2007;
    }

    public void setDescAteco2007(String descAteco2007) {
        this.descAteco2007 = descAteco2007;
    }

    public String getStatoAttivita() {
        return statoAttivita;
    }

    public void setStatoAttivita(String statoAttivita) {
        this.statoAttivita = statoAttivita;
    }

    public String getDataIscrizione() {
        return dataIscrizione;
    }

    public void setDataIscrizione(String dataIscrizione) {
        this.dataIscrizione = dataIscrizione;
    }

    public String getDataInizioAttivita() {
        return dataInizioAttivita;
    }

    public void setDataInizioAttivita(String dataInizioAttivita) {
        this.dataInizioAttivita = dataInizioAttivita;
    }

    public String getTipoSede() {
        return tipoSede;
    }

    public void setTipoSede(String tipoSede) {
        this.tipoSede = tipoSede;
    }

    public String getCodeTipoSede() {
        return codeTipoSede;
    }

    public void setCodeTipoSede(String codeTipoSede) {
        this.codeTipoSede = codeTipoSede;
    }

    public Integer getProgressivoUl() {
        return progressivoUl;
    }

    public void setProgressivoUl(Integer progressivoUl) {
        this.progressivoUl = progressivoUl;
    }

    public Integer getNumeroSedi() {
        return numeroSedi;
    }

    public void setNumeroSedi(Integer numeroSedi) {
        this.numeroSedi = numeroSedi;
    }

    public Integer getDipendenti() {
        return dipendenti;
    }

    public void setDipendenti(Integer dipendenti) {
        this.dipendenti = dipendenti;
    }

    public Integer getIndipendenti() {
        return indipendenti;
    }

    public void setIndipendenti(Integer indipendenti) {
        this.indipendenti = indipendenti;
    }

    public Integer getTotale() {
        return totale;
    }

    public void setTotale(Integer totale) {
        this.totale = totale;
    }

    public String getDataChiusuraBilancio() {
        return dataChiusuraBilancio;
    }

    public void setDataChiusuraBilancio(String dataChiusuraBilancio) {
        this.dataChiusuraBilancio = dataChiusuraBilancio;
    }

    public Long getEbitda() {
        return ebitda;
    }

    public void setEbitda(Long ebitda) {
        this.ebitda = ebitda;
    }

    public Boolean getTelefono() {
        return telefono;
    }

    public void setTelefono(Boolean telefono) {
        this.telefono = telefono;
    }

    public String getDescPrefisso() {
        return descPrefisso;
    }

    public void setDescPrefisso(String descPrefisso) {
        this.descPrefisso = descPrefisso;
    }

    public String getDescNumero() {
        return descNumero;
    }

    public void setDescNumero(String descNumero) {
        this.descNumero = descNumero;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getEmailGenerica() {
        return emailGenerica;
    }

    public void setEmailGenerica(String emailGenerica) {
        this.emailGenerica = emailGenerica;
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

    public Integer getPartecipazioni() {
        return partecipazioni;
    }

    public void setPartecipazioni(Integer partecipazioni) {
        this.partecipazioni = partecipazioni;
    }

    public Boolean getPa() {
        return pa;
    }

    public void setPa(Boolean pa) {
        this.pa = pa;
    }

    public String getPec() {
        return pec;
    }

    public void setPec(String pec) {
        this.pec = pec;
    }

    public String getEsponente1() {
        return esponente1;
    }

    public void setEsponente1(String esponente1) {
        this.esponente1 = esponente1;
    }

    public String getCaricaEsponente1() {
        return caricaEsponente1;
    }

    public void setCaricaEsponente1(String caricaEsponente1) {
        this.caricaEsponente1 = caricaEsponente1;
    }

    public String getEsponente2() {
        return esponente2;
    }

    public void setEsponente2(String esponente2) {
        this.esponente2 = esponente2;
    }

    public String getCaricaEsponente2() {
        return caricaEsponente2;
    }

    public void setCaricaEsponente2(String caricaEsponente2) {
        this.caricaEsponente2 = caricaEsponente2;
    }

    public String getEsponente3() {
        return esponente3;
    }

    public void setEsponente3(String esponente3) {
        this.esponente3 = esponente3;
    }

    public String getCaricaEsponente3() {
        return caricaEsponente3;
    }

    public void setCaricaEsponente3(String caricaEsponente3) {
        this.caricaEsponente3 = caricaEsponente3;
    }

    public String getEsponente4() {
        return esponente4;
    }

    public void setEsponente4(String esponente4) {
        this.esponente4 = esponente4;
    }

    public String getCaricaEsponente4() {
        return caricaEsponente4;
    }

    public void setCaricaEsponente4(String caricaEsponente4) {
        this.caricaEsponente4 = caricaEsponente4;
    }

    public String getEsponente5() {
        return esponente5;
    }

    public void setEsponente5(String esponente5) {
        this.esponente5 = esponente5;
    }

    public String getCaricaEsponente5() {
        return caricaEsponente5;
    }

    public void setCaricaEsponente5(String caricaEsponente5) {
        this.caricaEsponente5 = caricaEsponente5;
    }

    public String getFlagStartupInnovativa() {
        return flagStartupInnovativa;
    }

    public void setFlagStartupInnovativa(String flagStartupInnovativa) {
        this.flagStartupInnovativa = flagStartupInnovativa;
    }

    public String getCapSocVersato() {
        return capSocVersato;
    }

    public void setCapSocVersato(String capSocVersato) {
        this.capSocVersato = capSocVersato;
    }
}
