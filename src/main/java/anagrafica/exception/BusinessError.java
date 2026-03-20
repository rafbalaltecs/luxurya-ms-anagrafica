package anagrafica.exception;

public enum BusinessError {
	PRODUCT_NOT_FOUND("001", "Product not found"),
    ORDER_NOT_FOUND("002", "Order not found"),
    INVALID_QUANTITY("003", "Invalid quantity"),
    USER_NOT_AGENT("010", "Nessun agente collegato a questo utente"),
    NOT_EXIST_VOYAGE_FOR_THIS_AGENT("010", "Non esiste alcun viaggio per questo agente"),
    NOT_EXIST_PRODUCTS_FOR_VOYAGE("004", "Non esistono prodotti per questo viaggio"),
    EXIST_OPERATION_FOR_VOYAGE("005", "Esiste operazione per il seguente viaggio e per il cliente selezionato"),
    NOT_EXIST_ENTITY("000", "Non abbiamo trovato la seguente: {entity} "),
    INVALID_AUTH_TOKEN("999", "Invalid Auth Token : {entity} "),
    INVALID_EXT_SYSTEM_USER("006", "Not Exist System External For This User"),
    EXTERNAL_SYSYEM_NOT_ENABLED("007", "External System Not Enabled"),
    EXIST_USER_WITH_AGENT("008", "Esiste un agente per l'utente selezionato"),
    EXIST_VOYAGE_FOR_COMPANY_USAGE("009", "Esiste un viaggio in corso per il cliente selezionato");

    private final String code;
    private final String description;

    BusinessError(String code, String description) {
        this.code = code;
        this.description = description;
    }

    public BusinessException toException() {
        return new BusinessException(this.code, this.description);
    }
    
    public BusinessException toExceptionEntity(final String entity) {
        return new BusinessException(this.code, this.description.replace("{entity}", entity));
    }
    
    public BusinessException toException(final String message) {
        return new BusinessException(this.code, message);
    }

    public String getCode() { return code; }
    public String getDescription() { return description; }
}
