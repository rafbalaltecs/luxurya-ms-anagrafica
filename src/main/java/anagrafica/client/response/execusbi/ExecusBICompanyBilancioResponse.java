package anagrafica.client.response.execusbi;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class ExecusBICompanyBilancioResponse {
    @JsonProperty("dt_chiusura_bil")
    public String dtChiusuraBil;

    @JsonProperty("Totale immobilizzazioni materiali")
    public Double totaleImmobilizzazioniMateriali;

    @JsonProperty("Totale immobilizzazioni immateriali")
    public Double totaleImmobilizzazioniImmateriali;

    @JsonProperty("Costi di impianto / ampliamento")
    public Double costiDiImpiantoAmpliamento;

    @JsonProperty("Costi di sviluppo")
    public Double costiDiSviluppo;

    @JsonProperty("Brevetti")
    public Double brevetti;

    @JsonProperty("Concessioni / Licenze")
    public Double concessioniLicenze;

    @JsonProperty("Avviamento")
    public Double avviamento;

    @JsonProperty("Immobilizzazioni in corso / acconti immateriali")
    public Double immobilizzazioniInCorsoAccontiImmateriali;

    @JsonProperty("Altre")
    public Double altre;

    @JsonProperty("Totale immobilizzazioni finanziarie")
    public Double totaleImmobilizzazioniFinanziarie;

    @JsonProperty("Partecipazioni")
    public Double partecipazioni;

    @JsonProperty("Crediti Imm Fin")
    public Double creditiImmFin;

    @JsonProperty("Altri titoli")
    public Double altriTitoli;

    @JsonProperty("Azioni proprie")
    public Double azioniProprie;

    @JsonProperty("Strumenti finanziari derivati attivi")
    public Double strumentiFinanziariDerivatiAttivi;

    @JsonProperty("ATTIVO IMMOBILIZZATO")
    public Double attivoImmobilizzato;

    @JsonProperty("Totale Rimanenze")
    public Double totaleRimanenze;

    @JsonProperty("Materie prime")
    public Double materiePrime;

    @JsonProperty("Prodotti semilav/in corso")
    public Double prodottiSemilavInCorso;

    @JsonProperty("Lavori in corso")
    public Double lavoriInCorso;

    @JsonProperty("Prodotti finiti")
    public Double prodottiFiniti;

    @JsonProperty("Acconti")
    public Double acconti;

    @JsonProperty("Crediti verso clienti di breve termine")
    public Double creditiVersoClientiBreveTermine;

    @JsonProperty("Crediti verso clienti di lungo termine")
    public Double creditiVersoClientiLungoTermine;

    @JsonProperty("Crediti tributari di breve termine")
    public Double creditiTributariBreveTermine;

    @JsonProperty("Crediti tributari di lungo termine")
    public Double creditiTributariLungoTermine;

    @JsonProperty("Crediti per imposte anticipate di breve termine")
    public Double creditiImposteAnticipateBreveTermine;

    @JsonProperty("Crediti per imposte anticipate di lungo termine")
    public Double creditiImposteAnticipateLungoTermine;

    @JsonProperty("Crediti vs soci per versamenti ancora dovuti")
    public Double creditiVsSociVersamenti;

    @JsonProperty("Altri crediti di breve termine")
    public Double altriCreditiBreveTermine;

    @JsonProperty("Altri crediti di lungo termine")
    public Double altriCreditiLungoTermine;

    @JsonProperty("Liquidità attivo")
    public Double liquiditaAttivo;

    @JsonProperty("Ratei e risconti attivi")
    public Double rateiERiscontiAttivi;

    @JsonProperty("Attività finanziarie")
    public Double attivitaFinanziarie;

    @JsonProperty("ATTIVO CIRCOLANTE")
    public Double attivoCircolante;

    @JsonProperty("TOTALE ATTIVO")
    public Double totaleAttivo;

    @JsonProperty("Capitale sociale")
    public Double capitaleSociale;

    @JsonProperty("Riserva legale")
    public Double riservaLegale;

    @JsonProperty("Riserva da sovrapprezzo")
    public Double riservaDaSovrapprezzo;

    @JsonProperty("Altre riserve")
    public Double altreRiserve;

    @JsonProperty("Utile/perdita es a riserva")
    public Double utilePerditaEsARiserva;

    @JsonProperty("Utile/perdita di esercizio")
    public Double utilePerditaDiEsercizio;

    @JsonProperty("PATRIMONIO NETTO")
    public Double patrimonioNetto;

    @JsonProperty("Ratei e risconti passivi")
    public Double rateiERiscontiPassivi;

    @JsonProperty("Altre passività a breve termine")
    public Double altrePassivitaBreveTermine;

    @JsonProperty("PASSIVO CORRENTE")
    public Double passivoCorrente;

    @JsonProperty("Debiti verso fornitori di lungo termine")
    public Double debitiVersoFornitoriLungoTermine;

    @JsonProperty("Debiti tributari di lungo termine")
    public Double debitiTributariLungoTermine;

    @JsonProperty("Debiti verso istituti di previdenza di lungo termine")
    public Double debitiVersoIstitutiPrevidenzaLungoTermine;

    @JsonProperty("Debiti verso banche di lungo termine")
    public Double debitiVersoBancheLungoTermine;

    @JsonProperty("Fondi rischi")
    public Double fondiRischi;

    @JsonProperty("TFR")
    public Double tfr;

    @JsonProperty("Altre passività a lungo termine")
    public Double altrePassivitaLungoTermine;

    @JsonProperty("PASSIVO NON CORRENTE")
    public Double passivoNonCorrente;

    @JsonProperty("TOTALE PASSIVO")
    public Double totalePassivo;

    @JsonProperty("Liquidità passivo")
    public Double liquiditaPassivo;

    @JsonProperty("PFN")
    public Double pfn;

    @JsonProperty("Ricavi delle vendite")
    public Double ricaviVendite;

    @JsonProperty("Variazione rimanenze prodotti")
    public Double variazioneRimanenzeProdotti;

    @JsonProperty("Variazione lavori in corso su ordinazione")
    public Double variazioneLavoriInCorsoOrdinazione;

    @JsonProperty("Incrementi di immobilizzazioni per lavori interni")
    public Double incrementiImmobilizzazioniLavoriInterni;

    @JsonProperty("Contributi in conto esercizio")
    public Double contributiContoEsercizio;

    @JsonProperty("Altri Ricavi")
    public Double altriRicavi;

    @JsonProperty("Valore della Produzione (VdP)")
    public Double valoreProduzione;

    @JsonProperty("Acquisti")
    public Double acquisti;

    @JsonProperty("Variazione delle materie prime")
    public Double variazioneMateriePrime;

    @JsonProperty("Valore Aggiunto")
    public Double valoreAggiunto;

    @JsonProperty("Costi del personale")
    public Double costiDelPersonale;

    @JsonProperty("Costi per servizi")
    public Double costiPerServizi;

    @JsonProperty("Godimento beni di terzi")
    public Double godimentoBeniTerzi;

    @JsonProperty("Oneri diversi di gestione")
    public Double oneriDiversiGestione;

    @JsonProperty("EBITDA")
    public Double ebitda;

    @JsonProperty("EBITDA margin (sul VdP)")
    public Double ebitdaMargin;

    @JsonProperty("Ammortamento immobilizzazioni materiali")
    public Double ammortamentoImmobilizzazioniMateriali;

    @JsonProperty("Ammortamento immobilizzazioni immateriali")
    public Double ammortamentoImmobilizzazioniImmateriali;

    @JsonProperty("Svalutazione delle immobilizzazioni")
    public Double svalutazioneImmobilizzazioni;

    @JsonProperty("Svalutazione crediti")
    public Double svalutazioneCrediti;

    @JsonProperty("Altre svalutazioni")
    public Double altreSvalutazioni;

    @JsonProperty("Rivalutazioni")
    public Double rivalutazioni;

    @JsonProperty("Accantonamenti per rischi")
    public Double accantonamentiPerRischi;

    @JsonProperty("Altri accantonamenti")
    public Double altriAccantonamenti;

    @JsonProperty("Proventi straordinari")
    public Double proventiStraordinari;

    @JsonProperty("Oneri straordinari")
    public Double oneriStraordinari;

    @JsonProperty("EBIT - Risultato operativo")
    public Double ebit;

    @JsonProperty("EBIT margin (sul VdP)")
    public Double ebitMargin;

    @JsonProperty("Altri proventi")
    public Double altriProventi;

    @JsonProperty("Oneri finanziari")
    public Double oneriFinanziari;

    @JsonProperty("Utili e perdite su cambi")
    public Double utiliPerditeSuCambi;

    @JsonProperty("EBT - Risultato ante imposte")
    public Double ebt;

    @JsonProperty("EBT margin (sul VdP)")
    public Double ebtMargin;

    @JsonProperty("Imposte sul reddito")
    public Double imposteSulReddito;

    @JsonProperty("Risultato di esercizio")
    public Double risultatoDiEsercizio;

    @JsonProperty("ROI (ritorno sul capitale investito)")
    public Double roi;

    @JsonProperty("ROE (ritorno sul capitale netto)")
    public Double roe;

    @JsonProperty("ROS (ritorno sulle vendite)")
    public Double ros;

    @JsonProperty("MOL margin / EBITDA margin")
    public Double molMargin;

    @JsonProperty("Oneri finanziari sul Valore delle Produzione")
    public Double oneriFinanziariVdP;

    @JsonProperty("Indipendenza finanziaria")
    public Double indipendenzaFinanziaria;

    @JsonProperty("Margine di struttura primario")
    public Double margineStrutturaPrimario;

    @JsonProperty("Margine di struttura secondario")
    public Double margineStrutturaSecondario;

    @JsonProperty("Current Ratio")
    public Double currentRatio;

    @JsonProperty("Acid Test")
    public Double acidTest;

    @JsonProperty("Rotazione capitale circolante netto")
    public Double rotazioneCapitaleCircolante;

    @JsonProperty("DSO (giorni medi di incasso)")
    public Double dso;

    @JsonProperty("DPO (giorni medi di pagamento)")
    public Double dpo;

    @JsonProperty("DIO (giorni di giacenza media del magazzino)")
    public Double dio;

    public String getDtChiusuraBil() {
        return dtChiusuraBil;
    }

    public void setDtChiusuraBil(String dtChiusuraBil) {
        this.dtChiusuraBil = dtChiusuraBil;
    }

    public Double getTotaleImmobilizzazioniMateriali() {
        return totaleImmobilizzazioniMateriali;
    }

    public void setTotaleImmobilizzazioniMateriali(Double totaleImmobilizzazioniMateriali) {
        this.totaleImmobilizzazioniMateriali = totaleImmobilizzazioniMateriali;
    }

    public Double getTotaleImmobilizzazioniImmateriali() {
        return totaleImmobilizzazioniImmateriali;
    }

    public void setTotaleImmobilizzazioniImmateriali(Double totaleImmobilizzazioniImmateriali) {
        this.totaleImmobilizzazioniImmateriali = totaleImmobilizzazioniImmateriali;
    }

    public Double getCostiDiImpiantoAmpliamento() {
        return costiDiImpiantoAmpliamento;
    }

    public void setCostiDiImpiantoAmpliamento(Double costiDiImpiantoAmpliamento) {
        this.costiDiImpiantoAmpliamento = costiDiImpiantoAmpliamento;
    }

    public Double getCostiDiSviluppo() {
        return costiDiSviluppo;
    }

    public void setCostiDiSviluppo(Double costiDiSviluppo) {
        this.costiDiSviluppo = costiDiSviluppo;
    }

    public Double getBrevetti() {
        return brevetti;
    }

    public void setBrevetti(Double brevetti) {
        this.brevetti = brevetti;
    }

    public Double getConcessioniLicenze() {
        return concessioniLicenze;
    }

    public void setConcessioniLicenze(Double concessioniLicenze) {
        this.concessioniLicenze = concessioniLicenze;
    }

    public Double getAvviamento() {
        return avviamento;
    }

    public void setAvviamento(Double avviamento) {
        this.avviamento = avviamento;
    }

    public Double getImmobilizzazioniInCorsoAccontiImmateriali() {
        return immobilizzazioniInCorsoAccontiImmateriali;
    }

    public void setImmobilizzazioniInCorsoAccontiImmateriali(Double immobilizzazioniInCorsoAccontiImmateriali) {
        this.immobilizzazioniInCorsoAccontiImmateriali = immobilizzazioniInCorsoAccontiImmateriali;
    }

    public Double getAltre() {
        return altre;
    }

    public void setAltre(Double altre) {
        this.altre = altre;
    }

    public Double getTotaleImmobilizzazioniFinanziarie() {
        return totaleImmobilizzazioniFinanziarie;
    }

    public void setTotaleImmobilizzazioniFinanziarie(Double totaleImmobilizzazioniFinanziarie) {
        this.totaleImmobilizzazioniFinanziarie = totaleImmobilizzazioniFinanziarie;
    }

    public Double getPartecipazioni() {
        return partecipazioni;
    }

    public void setPartecipazioni(Double partecipazioni) {
        this.partecipazioni = partecipazioni;
    }

    public Double getCreditiImmFin() {
        return creditiImmFin;
    }

    public void setCreditiImmFin(Double creditiImmFin) {
        this.creditiImmFin = creditiImmFin;
    }

    public Double getAltriTitoli() {
        return altriTitoli;
    }

    public void setAltriTitoli(Double altriTitoli) {
        this.altriTitoli = altriTitoli;
    }

    public Double getAzioniProprie() {
        return azioniProprie;
    }

    public void setAzioniProprie(Double azioniProprie) {
        this.azioniProprie = azioniProprie;
    }

    public Double getStrumentiFinanziariDerivatiAttivi() {
        return strumentiFinanziariDerivatiAttivi;
    }

    public void setStrumentiFinanziariDerivatiAttivi(Double strumentiFinanziariDerivatiAttivi) {
        this.strumentiFinanziariDerivatiAttivi = strumentiFinanziariDerivatiAttivi;
    }

    public Double getAttivoImmobilizzato() {
        return attivoImmobilizzato;
    }

    public void setAttivoImmobilizzato(Double attivoImmobilizzato) {
        this.attivoImmobilizzato = attivoImmobilizzato;
    }

    public Double getTotaleRimanenze() {
        return totaleRimanenze;
    }

    public void setTotaleRimanenze(Double totaleRimanenze) {
        this.totaleRimanenze = totaleRimanenze;
    }

    public Double getMateriePrime() {
        return materiePrime;
    }

    public void setMateriePrime(Double materiePrime) {
        this.materiePrime = materiePrime;
    }

    public Double getProdottiSemilavInCorso() {
        return prodottiSemilavInCorso;
    }

    public void setProdottiSemilavInCorso(Double prodottiSemilavInCorso) {
        this.prodottiSemilavInCorso = prodottiSemilavInCorso;
    }

    public Double getLavoriInCorso() {
        return lavoriInCorso;
    }

    public void setLavoriInCorso(Double lavoriInCorso) {
        this.lavoriInCorso = lavoriInCorso;
    }

    public Double getProdottiFiniti() {
        return prodottiFiniti;
    }

    public void setProdottiFiniti(Double prodottiFiniti) {
        this.prodottiFiniti = prodottiFiniti;
    }

    public Double getAcconti() {
        return acconti;
    }

    public void setAcconti(Double acconti) {
        this.acconti = acconti;
    }

    public Double getCreditiVersoClientiBreveTermine() {
        return creditiVersoClientiBreveTermine;
    }

    public void setCreditiVersoClientiBreveTermine(Double creditiVersoClientiBreveTermine) {
        this.creditiVersoClientiBreveTermine = creditiVersoClientiBreveTermine;
    }

    public Double getCreditiVersoClientiLungoTermine() {
        return creditiVersoClientiLungoTermine;
    }

    public void setCreditiVersoClientiLungoTermine(Double creditiVersoClientiLungoTermine) {
        this.creditiVersoClientiLungoTermine = creditiVersoClientiLungoTermine;
    }

    public Double getCreditiTributariBreveTermine() {
        return creditiTributariBreveTermine;
    }

    public void setCreditiTributariBreveTermine(Double creditiTributariBreveTermine) {
        this.creditiTributariBreveTermine = creditiTributariBreveTermine;
    }

    public Double getCreditiTributariLungoTermine() {
        return creditiTributariLungoTermine;
    }

    public void setCreditiTributariLungoTermine(Double creditiTributariLungoTermine) {
        this.creditiTributariLungoTermine = creditiTributariLungoTermine;
    }

    public Double getCreditiImposteAnticipateBreveTermine() {
        return creditiImposteAnticipateBreveTermine;
    }

    public void setCreditiImposteAnticipateBreveTermine(Double creditiImposteAnticipateBreveTermine) {
        this.creditiImposteAnticipateBreveTermine = creditiImposteAnticipateBreveTermine;
    }

    public Double getCreditiImposteAnticipateLungoTermine() {
        return creditiImposteAnticipateLungoTermine;
    }

    public void setCreditiImposteAnticipateLungoTermine(Double creditiImposteAnticipateLungoTermine) {
        this.creditiImposteAnticipateLungoTermine = creditiImposteAnticipateLungoTermine;
    }

    public Double getCreditiVsSociVersamenti() {
        return creditiVsSociVersamenti;
    }

    public void setCreditiVsSociVersamenti(Double creditiVsSociVersamenti) {
        this.creditiVsSociVersamenti = creditiVsSociVersamenti;
    }

    public Double getAltriCreditiBreveTermine() {
        return altriCreditiBreveTermine;
    }

    public void setAltriCreditiBreveTermine(Double altriCreditiBreveTermine) {
        this.altriCreditiBreveTermine = altriCreditiBreveTermine;
    }

    public Double getAltriCreditiLungoTermine() {
        return altriCreditiLungoTermine;
    }

    public void setAltriCreditiLungoTermine(Double altriCreditiLungoTermine) {
        this.altriCreditiLungoTermine = altriCreditiLungoTermine;
    }

    public Double getLiquiditaAttivo() {
        return liquiditaAttivo;
    }

    public void setLiquiditaAttivo(Double liquiditaAttivo) {
        this.liquiditaAttivo = liquiditaAttivo;
    }

    public Double getRateiERiscontiAttivi() {
        return rateiERiscontiAttivi;
    }

    public void setRateiERiscontiAttivi(Double rateiERiscontiAttivi) {
        this.rateiERiscontiAttivi = rateiERiscontiAttivi;
    }

    public Double getAttivitaFinanziarie() {
        return attivitaFinanziarie;
    }

    public void setAttivitaFinanziarie(Double attivitaFinanziarie) {
        this.attivitaFinanziarie = attivitaFinanziarie;
    }

    public Double getAttivoCircolante() {
        return attivoCircolante;
    }

    public void setAttivoCircolante(Double attivoCircolante) {
        this.attivoCircolante = attivoCircolante;
    }

    public Double getTotaleAttivo() {
        return totaleAttivo;
    }

    public void setTotaleAttivo(Double totaleAttivo) {
        this.totaleAttivo = totaleAttivo;
    }

    public Double getCapitaleSociale() {
        return capitaleSociale;
    }

    public void setCapitaleSociale(Double capitaleSociale) {
        this.capitaleSociale = capitaleSociale;
    }

    public Double getRiservaLegale() {
        return riservaLegale;
    }

    public void setRiservaLegale(Double riservaLegale) {
        this.riservaLegale = riservaLegale;
    }

    public Double getRiservaDaSovrapprezzo() {
        return riservaDaSovrapprezzo;
    }

    public void setRiservaDaSovrapprezzo(Double riservaDaSovrapprezzo) {
        this.riservaDaSovrapprezzo = riservaDaSovrapprezzo;
    }

    public Double getAltreRiserve() {
        return altreRiserve;
    }

    public void setAltreRiserve(Double altreRiserve) {
        this.altreRiserve = altreRiserve;
    }

    public Double getUtilePerditaEsARiserva() {
        return utilePerditaEsARiserva;
    }

    public void setUtilePerditaEsARiserva(Double utilePerditaEsARiserva) {
        this.utilePerditaEsARiserva = utilePerditaEsARiserva;
    }

    public Double getUtilePerditaDiEsercizio() {
        return utilePerditaDiEsercizio;
    }

    public void setUtilePerditaDiEsercizio(Double utilePerditaDiEsercizio) {
        this.utilePerditaDiEsercizio = utilePerditaDiEsercizio;
    }

    public Double getPatrimonioNetto() {
        return patrimonioNetto;
    }

    public void setPatrimonioNetto(Double patrimonioNetto) {
        this.patrimonioNetto = patrimonioNetto;
    }

    public Double getRateiERiscontiPassivi() {
        return rateiERiscontiPassivi;
    }

    public void setRateiERiscontiPassivi(Double rateiERiscontiPassivi) {
        this.rateiERiscontiPassivi = rateiERiscontiPassivi;
    }

    public Double getAltrePassivitaBreveTermine() {
        return altrePassivitaBreveTermine;
    }

    public void setAltrePassivitaBreveTermine(Double altrePassivitaBreveTermine) {
        this.altrePassivitaBreveTermine = altrePassivitaBreveTermine;
    }

    public Double getPassivoCorrente() {
        return passivoCorrente;
    }

    public void setPassivoCorrente(Double passivoCorrente) {
        this.passivoCorrente = passivoCorrente;
    }

    public Double getDebitiVersoFornitoriLungoTermine() {
        return debitiVersoFornitoriLungoTermine;
    }

    public void setDebitiVersoFornitoriLungoTermine(Double debitiVersoFornitoriLungoTermine) {
        this.debitiVersoFornitoriLungoTermine = debitiVersoFornitoriLungoTermine;
    }

    public Double getDebitiTributariLungoTermine() {
        return debitiTributariLungoTermine;
    }

    public void setDebitiTributariLungoTermine(Double debitiTributariLungoTermine) {
        this.debitiTributariLungoTermine = debitiTributariLungoTermine;
    }

    public Double getDebitiVersoIstitutiPrevidenzaLungoTermine() {
        return debitiVersoIstitutiPrevidenzaLungoTermine;
    }

    public void setDebitiVersoIstitutiPrevidenzaLungoTermine(Double debitiVersoIstitutiPrevidenzaLungoTermine) {
        this.debitiVersoIstitutiPrevidenzaLungoTermine = debitiVersoIstitutiPrevidenzaLungoTermine;
    }

    public Double getDebitiVersoBancheLungoTermine() {
        return debitiVersoBancheLungoTermine;
    }

    public void setDebitiVersoBancheLungoTermine(Double debitiVersoBancheLungoTermine) {
        this.debitiVersoBancheLungoTermine = debitiVersoBancheLungoTermine;
    }

    public Double getFondiRischi() {
        return fondiRischi;
    }

    public void setFondiRischi(Double fondiRischi) {
        this.fondiRischi = fondiRischi;
    }

    public Double getTfr() {
        return tfr;
    }

    public void setTfr(Double tfr) {
        this.tfr = tfr;
    }

    public Double getAltrePassivitaLungoTermine() {
        return altrePassivitaLungoTermine;
    }

    public void setAltrePassivitaLungoTermine(Double altrePassivitaLungoTermine) {
        this.altrePassivitaLungoTermine = altrePassivitaLungoTermine;
    }

    public Double getPassivoNonCorrente() {
        return passivoNonCorrente;
    }

    public void setPassivoNonCorrente(Double passivoNonCorrente) {
        this.passivoNonCorrente = passivoNonCorrente;
    }

    public Double getTotalePassivo() {
        return totalePassivo;
    }

    public void setTotalePassivo(Double totalePassivo) {
        this.totalePassivo = totalePassivo;
    }

    public Double getLiquiditaPassivo() {
        return liquiditaPassivo;
    }

    public void setLiquiditaPassivo(Double liquiditaPassivo) {
        this.liquiditaPassivo = liquiditaPassivo;
    }

    public Double getPfn() {
        return pfn;
    }

    public void setPfn(Double pfn) {
        this.pfn = pfn;
    }

    public Double getRicaviVendite() {
        return ricaviVendite;
    }

    public void setRicaviVendite(Double ricaviVendite) {
        this.ricaviVendite = ricaviVendite;
    }

    public Double getVariazioneRimanenzeProdotti() {
        return variazioneRimanenzeProdotti;
    }

    public void setVariazioneRimanenzeProdotti(Double variazioneRimanenzeProdotti) {
        this.variazioneRimanenzeProdotti = variazioneRimanenzeProdotti;
    }

    public Double getVariazioneLavoriInCorsoOrdinazione() {
        return variazioneLavoriInCorsoOrdinazione;
    }

    public void setVariazioneLavoriInCorsoOrdinazione(Double variazioneLavoriInCorsoOrdinazione) {
        this.variazioneLavoriInCorsoOrdinazione = variazioneLavoriInCorsoOrdinazione;
    }

    public Double getIncrementiImmobilizzazioniLavoriInterni() {
        return incrementiImmobilizzazioniLavoriInterni;
    }

    public void setIncrementiImmobilizzazioniLavoriInterni(Double incrementiImmobilizzazioniLavoriInterni) {
        this.incrementiImmobilizzazioniLavoriInterni = incrementiImmobilizzazioniLavoriInterni;
    }

    public Double getContributiContoEsercizio() {
        return contributiContoEsercizio;
    }

    public void setContributiContoEsercizio(Double contributiContoEsercizio) {
        this.contributiContoEsercizio = contributiContoEsercizio;
    }

    public Double getAltriRicavi() {
        return altriRicavi;
    }

    public void setAltriRicavi(Double altriRicavi) {
        this.altriRicavi = altriRicavi;
    }

    public Double getValoreProduzione() {
        return valoreProduzione;
    }

    public void setValoreProduzione(Double valoreProduzione) {
        this.valoreProduzione = valoreProduzione;
    }

    public Double getAcquisti() {
        return acquisti;
    }

    public void setAcquisti(Double acquisti) {
        this.acquisti = acquisti;
    }

    public Double getVariazioneMateriePrime() {
        return variazioneMateriePrime;
    }

    public void setVariazioneMateriePrime(Double variazioneMateriePrime) {
        this.variazioneMateriePrime = variazioneMateriePrime;
    }

    public Double getValoreAggiunto() {
        return valoreAggiunto;
    }

    public void setValoreAggiunto(Double valoreAggiunto) {
        this.valoreAggiunto = valoreAggiunto;
    }

    public Double getCostiDelPersonale() {
        return costiDelPersonale;
    }

    public void setCostiDelPersonale(Double costiDelPersonale) {
        this.costiDelPersonale = costiDelPersonale;
    }

    public Double getCostiPerServizi() {
        return costiPerServizi;
    }

    public void setCostiPerServizi(Double costiPerServizi) {
        this.costiPerServizi = costiPerServizi;
    }

    public Double getGodimentoBeniTerzi() {
        return godimentoBeniTerzi;
    }

    public void setGodimentoBeniTerzi(Double godimentoBeniTerzi) {
        this.godimentoBeniTerzi = godimentoBeniTerzi;
    }

    public Double getOneriDiversiGestione() {
        return oneriDiversiGestione;
    }

    public void setOneriDiversiGestione(Double oneriDiversiGestione) {
        this.oneriDiversiGestione = oneriDiversiGestione;
    }

    public Double getEbitda() {
        return ebitda;
    }

    public void setEbitda(Double ebitda) {
        this.ebitda = ebitda;
    }

    public Double getEbitdaMargin() {
        return ebitdaMargin;
    }

    public void setEbitdaMargin(Double ebitdaMargin) {
        this.ebitdaMargin = ebitdaMargin;
    }

    public Double getAmmortamentoImmobilizzazioniMateriali() {
        return ammortamentoImmobilizzazioniMateriali;
    }

    public void setAmmortamentoImmobilizzazioniMateriali(Double ammortamentoImmobilizzazioniMateriali) {
        this.ammortamentoImmobilizzazioniMateriali = ammortamentoImmobilizzazioniMateriali;
    }

    public Double getAmmortamentoImmobilizzazioniImmateriali() {
        return ammortamentoImmobilizzazioniImmateriali;
    }

    public void setAmmortamentoImmobilizzazioniImmateriali(Double ammortamentoImmobilizzazioniImmateriali) {
        this.ammortamentoImmobilizzazioniImmateriali = ammortamentoImmobilizzazioniImmateriali;
    }

    public Double getSvalutazioneImmobilizzazioni() {
        return svalutazioneImmobilizzazioni;
    }

    public void setSvalutazioneImmobilizzazioni(Double svalutazioneImmobilizzazioni) {
        this.svalutazioneImmobilizzazioni = svalutazioneImmobilizzazioni;
    }

    public Double getSvalutazioneCrediti() {
        return svalutazioneCrediti;
    }

    public void setSvalutazioneCrediti(Double svalutazioneCrediti) {
        this.svalutazioneCrediti = svalutazioneCrediti;
    }

    public Double getAltreSvalutazioni() {
        return altreSvalutazioni;
    }

    public void setAltreSvalutazioni(Double altreSvalutazioni) {
        this.altreSvalutazioni = altreSvalutazioni;
    }

    public Double getRivalutazioni() {
        return rivalutazioni;
    }

    public void setRivalutazioni(Double rivalutazioni) {
        this.rivalutazioni = rivalutazioni;
    }

    public Double getAccantonamentiPerRischi() {
        return accantonamentiPerRischi;
    }

    public void setAccantonamentiPerRischi(Double accantonamentiPerRischi) {
        this.accantonamentiPerRischi = accantonamentiPerRischi;
    }

    public Double getAltriAccantonamenti() {
        return altriAccantonamenti;
    }

    public void setAltriAccantonamenti(Double altriAccantonamenti) {
        this.altriAccantonamenti = altriAccantonamenti;
    }

    public Double getProventiStraordinari() {
        return proventiStraordinari;
    }

    public void setProventiStraordinari(Double proventiStraordinari) {
        this.proventiStraordinari = proventiStraordinari;
    }

    public Double getOneriStraordinari() {
        return oneriStraordinari;
    }

    public void setOneriStraordinari(Double oneriStraordinari) {
        this.oneriStraordinari = oneriStraordinari;
    }

    public Double getEbit() {
        return ebit;
    }

    public void setEbit(Double ebit) {
        this.ebit = ebit;
    }

    public Double getEbitMargin() {
        return ebitMargin;
    }

    public void setEbitMargin(Double ebitMargin) {
        this.ebitMargin = ebitMargin;
    }

    public Double getAltriProventi() {
        return altriProventi;
    }

    public void setAltriProventi(Double altriProventi) {
        this.altriProventi = altriProventi;
    }

    public Double getOneriFinanziari() {
        return oneriFinanziari;
    }

    public void setOneriFinanziari(Double oneriFinanziari) {
        this.oneriFinanziari = oneriFinanziari;
    }

    public Double getUtiliPerditeSuCambi() {
        return utiliPerditeSuCambi;
    }

    public void setUtiliPerditeSuCambi(Double utiliPerditeSuCambi) {
        this.utiliPerditeSuCambi = utiliPerditeSuCambi;
    }

    public Double getEbt() {
        return ebt;
    }

    public void setEbt(Double ebt) {
        this.ebt = ebt;
    }

    public Double getEbtMargin() {
        return ebtMargin;
    }

    public void setEbtMargin(Double ebtMargin) {
        this.ebtMargin = ebtMargin;
    }

    public Double getImposteSulReddito() {
        return imposteSulReddito;
    }

    public void setImposteSulReddito(Double imposteSulReddito) {
        this.imposteSulReddito = imposteSulReddito;
    }

    public Double getRisultatoDiEsercizio() {
        return risultatoDiEsercizio;
    }

    public void setRisultatoDiEsercizio(Double risultatoDiEsercizio) {
        this.risultatoDiEsercizio = risultatoDiEsercizio;
    }

    public Double getRoi() {
        return roi;
    }

    public void setRoi(Double roi) {
        this.roi = roi;
    }

    public Double getRoe() {
        return roe;
    }

    public void setRoe(Double roe) {
        this.roe = roe;
    }

    public Double getRos() {
        return ros;
    }

    public void setRos(Double ros) {
        this.ros = ros;
    }

    public Double getMolMargin() {
        return molMargin;
    }

    public void setMolMargin(Double molMargin) {
        this.molMargin = molMargin;
    }

    public Double getOneriFinanziariVdP() {
        return oneriFinanziariVdP;
    }

    public void setOneriFinanziariVdP(Double oneriFinanziariVdP) {
        this.oneriFinanziariVdP = oneriFinanziariVdP;
    }

    public Double getIndipendenzaFinanziaria() {
        return indipendenzaFinanziaria;
    }

    public void setIndipendenzaFinanziaria(Double indipendenzaFinanziaria) {
        this.indipendenzaFinanziaria = indipendenzaFinanziaria;
    }

    public Double getMargineStrutturaPrimario() {
        return margineStrutturaPrimario;
    }

    public void setMargineStrutturaPrimario(Double margineStrutturaPrimario) {
        this.margineStrutturaPrimario = margineStrutturaPrimario;
    }

    public Double getMargineStrutturaSecondario() {
        return margineStrutturaSecondario;
    }

    public void setMargineStrutturaSecondario(Double margineStrutturaSecondario) {
        this.margineStrutturaSecondario = margineStrutturaSecondario;
    }

    public Double getCurrentRatio() {
        return currentRatio;
    }

    public void setCurrentRatio(Double currentRatio) {
        this.currentRatio = currentRatio;
    }

    public Double getAcidTest() {
        return acidTest;
    }

    public void setAcidTest(Double acidTest) {
        this.acidTest = acidTest;
    }

    public Double getRotazioneCapitaleCircolante() {
        return rotazioneCapitaleCircolante;
    }

    public void setRotazioneCapitaleCircolante(Double rotazioneCapitaleCircolante) {
        this.rotazioneCapitaleCircolante = rotazioneCapitaleCircolante;
    }

    public Double getDso() {
        return dso;
    }

    public void setDso(Double dso) {
        this.dso = dso;
    }

    public Double getDpo() {
        return dpo;
    }

    public void setDpo(Double dpo) {
        this.dpo = dpo;
    }

    public Double getDio() {
        return dio;
    }

    public void setDio(Double dio) {
        this.dio = dio;
    }
}
