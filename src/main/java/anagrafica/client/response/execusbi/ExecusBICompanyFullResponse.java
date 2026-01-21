package anagrafica.client.response.execusbi;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.math.BigDecimal;
import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class ExecusBICompanyFullResponse {
    @JsonProperty("CODICEFISCALE")
    private String codiceFiscale;

    @JsonProperty("ATTO_COSTITUTIVO")
    private String attoCostitutivo;

    @JsonProperty("RAGIONE_SOCIALE")
    private String ragioneSociale;

    @JsonProperty("TOPONIMO")
    private String toponimo;

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
    private String codiceAteco2007;

    @JsonProperty("DESC_ATECO2007")
    private String descrizioneAteco2007;

    @JsonProperty("STATO_ATTIVITA_INFOCAMERE")
    private String statoAttivitaInfocamere;

    @JsonProperty("DATA_ISCRIZIONE")
    private String dataIscrizione;

    @JsonProperty("DATA_INIZIO_ATTIVITA")
    private String dataInizioAttivita;

    @JsonProperty("DATA_CESSAZIONE")
    private String dataCessazione;

    @JsonProperty("TIPO_SEDE")
    private String tipoSede;

    @JsonProperty("CODE_TIPO_SEDE")
    private String codiceTipoSede;

    @JsonProperty("DESCRIZIONE_SEDE")
    private String descrizioneSede;

    @JsonProperty("PROGRESSIVO_UL")
    private Integer progressivoUl;

    @JsonProperty("NUMERO_SEDI")
    private Integer numeroSedi;

    @JsonProperty("CAP_SOC_VERSATO")
    private BigDecimal capitaleVersato;

    @JsonProperty("CAP_SOC_SOTTOSCRITTO")
    private BigDecimal capitaleSottoscritto;

    @JsonProperty("CAP_SOC_DELIBERATO")
    private BigDecimal capitaleDeliberato;

    @JsonProperty("DIPENDENTI")
    private Integer dipendenti;

    @JsonProperty("INDIPENDENTI")
    private Integer indipendenti;

    @JsonProperty("TOTALE")
    private Integer totale;

    @JsonProperty("PAESE_ESTERO")
    private String paeseEstero;

    @JsonProperty("DATA_CHIUSURA_BILANCIO")
    private String dataChiusuraBilancio;

    @JsonProperty("FATTURATO_ULTIMO_BILANCIO")
    private BigDecimal fatturatoUltimoBilancio;

    // Collezioni annidate
    @JsonProperty("Esponenti")
    private List<ExecusBICompanyEsponenteResponse> esponenti;

    @JsonProperty("Soci")
    private List<ExecusBICompanySocioResponse> soci;

    @JsonProperty("BilanciEsercizio")
    private List<ExecusBICompanyBilancioResponse> bilanciEsercizio;

    @JsonProperty("StoricoDipendenti")
    private List<ExecusBIStoricoDipendentiResponse> storicoDipendenti;

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

    public String getCodiceAteco2007() {
        return codiceAteco2007;
    }

    public void setCodiceAteco2007(String codiceAteco2007) {
        this.codiceAteco2007 = codiceAteco2007;
    }

    public String getDescrizioneAteco2007() {
        return descrizioneAteco2007;
    }

    public void setDescrizioneAteco2007(String descrizioneAteco2007) {
        this.descrizioneAteco2007 = descrizioneAteco2007;
    }

    public String getStatoAttivitaInfocamere() {
        return statoAttivitaInfocamere;
    }

    public void setStatoAttivitaInfocamere(String statoAttivitaInfocamere) {
        this.statoAttivitaInfocamere = statoAttivitaInfocamere;
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

    public String getDataCessazione() {
        return dataCessazione;
    }

    public void setDataCessazione(String dataCessazione) {
        this.dataCessazione = dataCessazione;
    }

    public String getTipoSede() {
        return tipoSede;
    }

    public void setTipoSede(String tipoSede) {
        this.tipoSede = tipoSede;
    }

    public String getCodiceTipoSede() {
        return codiceTipoSede;
    }

    public void setCodiceTipoSede(String codiceTipoSede) {
        this.codiceTipoSede = codiceTipoSede;
    }

    public String getDescrizioneSede() {
        return descrizioneSede;
    }

    public void setDescrizioneSede(String descrizioneSede) {
        this.descrizioneSede = descrizioneSede;
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

    public BigDecimal getCapitaleVersato() {
        return capitaleVersato;
    }

    public void setCapitaleVersato(BigDecimal capitaleVersato) {
        this.capitaleVersato = capitaleVersato;
    }

    public BigDecimal getCapitaleSottoscritto() {
        return capitaleSottoscritto;
    }

    public void setCapitaleSottoscritto(BigDecimal capitaleSottoscritto) {
        this.capitaleSottoscritto = capitaleSottoscritto;
    }

    public BigDecimal getCapitaleDeliberato() {
        return capitaleDeliberato;
    }

    public void setCapitaleDeliberato(BigDecimal capitaleDeliberato) {
        this.capitaleDeliberato = capitaleDeliberato;
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

    public String getPaeseEstero() {
        return paeseEstero;
    }

    public void setPaeseEstero(String paeseEstero) {
        this.paeseEstero = paeseEstero;
    }

    public String getDataChiusuraBilancio() {
        return dataChiusuraBilancio;
    }

    public void setDataChiusuraBilancio(String dataChiusuraBilancio) {
        this.dataChiusuraBilancio = dataChiusuraBilancio;
    }

    public BigDecimal getFatturatoUltimoBilancio() {
        return fatturatoUltimoBilancio;
    }

    public void setFatturatoUltimoBilancio(BigDecimal fatturatoUltimoBilancio) {
        this.fatturatoUltimoBilancio = fatturatoUltimoBilancio;
    }

    public List<ExecusBICompanyEsponenteResponse> getEsponenti() {
        return esponenti;
    }

    public void setEsponenti(List<ExecusBICompanyEsponenteResponse> esponenti) {
        this.esponenti = esponenti;
    }

    public List<ExecusBICompanySocioResponse> getSoci() {
        return soci;
    }

    public void setSoci(List<ExecusBICompanySocioResponse> soci) {
        this.soci = soci;
    }

    public List<ExecusBICompanyBilancioResponse> getBilanciEsercizio() {
        return bilanciEsercizio;
    }

    public void setBilanciEsercizio(List<ExecusBICompanyBilancioResponse> bilanciEsercizio) {
        this.bilanciEsercizio = bilanciEsercizio;
    }

    public List<ExecusBIStoricoDipendentiResponse> getStoricoDipendenti() {
        return storicoDipendenti;
    }

    public void setStoricoDipendenti(List<ExecusBIStoricoDipendentiResponse> storicoDipendenti) {
        this.storicoDipendenti = storicoDipendenti;
    }

    public String getAttoCostitutivo() {
        return attoCostitutivo;
    }

    public void setAttoCostitutivo(String attoCostitutivo) {
        this.attoCostitutivo = attoCostitutivo;
    }
}
