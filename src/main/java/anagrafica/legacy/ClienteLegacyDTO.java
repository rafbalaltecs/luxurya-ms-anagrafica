package anagrafica.legacy;

public class ClienteLegacyDTO {
	private Integer recId;
    private String codice;
    private String tipoCliente;
    private String ragioneSociale;
    private String indirizzo;
    private String citta;
    private String prov;
    private String cap;
    private String piva;
    private String zona;
    private String catListino;
    private String telefono1;
    private String telefono2;
    
    public ClienteLegacyDTO() {}

    public ClienteLegacyDTO(Integer recId, String codice, String tipoCliente, String ragioneSociale,
                      String indirizzo, String citta, String prov, String cap,
                      String piva, String zona, String catListino,
                      String telefono1, String telefono2) {
        this.recId = recId;
        this.codice = codice;
        this.tipoCliente = tipoCliente;
        this.ragioneSociale = ragioneSociale;
        this.indirizzo = indirizzo;
        this.citta = citta;
        this.prov = prov;
        this.cap = cap;
        this.piva = piva;
        this.zona = zona;
        this.catListino = catListino;
        this.telefono1 = telefono1;
        this.telefono2 = telefono2;
    }

	public Integer getRecId() {
		return recId;
	}

	public void setRecId(Integer recId) {
		this.recId = recId;
	}

	public String getCodice() {
		return codice;
	}

	public void setCodice(String codice) {
		this.codice = codice;
	}

	public String getTipoCliente() {
		return tipoCliente;
	}

	public void setTipoCliente(String tipoCliente) {
		this.tipoCliente = tipoCliente;
	}

	public String getRagioneSociale() {
		return ragioneSociale;
	}

	public void setRagioneSociale(String ragioneSociale) {
		this.ragioneSociale = ragioneSociale;
	}

	public String getIndirizzo() {
		return indirizzo;
	}

	public void setIndirizzo(String indirizzo) {
		this.indirizzo = indirizzo;
	}

	public String getCitta() {
		return citta;
	}

	public void setCitta(String citta) {
		this.citta = citta;
	}

	public String getProv() {
		return prov;
	}

	public void setProv(String prov) {
		this.prov = prov;
	}

	public String getCap() {
		return cap;
	}

	public void setCap(String cap) {
		this.cap = cap;
	}

	public String getPiva() {
		return piva;
	}

	public void setPiva(String piva) {
		this.piva = piva;
	}

	public String getZona() {
		return zona;
	}

	public void setZona(String zona) {
		this.zona = zona;
	}

	public String getCatListino() {
		return catListino;
	}

	public void setCatListino(String catListino) {
		this.catListino = catListino;
	}

	public String getTelefono1() {
		return telefono1;
	}

	public void setTelefono1(String telefono1) {
		this.telefono1 = telefono1;
	}

	public String getTelefono2() {
		return telefono2;
	}

	public void setTelefono2(String telefono2) {
		this.telefono2 = telefono2;
	}
    
	 @Override
	    public String toString() {
	        return "ClienteLegacyDTO{" +
	                "recId=" + recId +
	                ", codice='" + codice + '\'' +
	                ", tipoCliente='" + tipoCliente + '\'' +
	                ", ragioneSociale='" + ragioneSociale + '\'' +
	                ", citta='" + citta + '\'' +
	                ", prov='" + prov + '\'' +
	                '}';
	    }

}
