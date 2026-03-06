package anagrafica.exception;

public enum BusinessError {
	PRODUCT_NOT_FOUND("001", "Product not found"),
    ORDER_NOT_FOUND("002", "Order not found"),
    INVALID_QUANTITY("003", "Invalid quantity"),
    NOT_EXIST_PRODUCTS_FOR_VOYAGE("004", "Non esistono prodotti per questo viaggio"),
    EXIST_OPERATION_FOR_VOYAGE("005", "Esiste operazione per il seguente viaggio e per il cliente selezionato"),
    NOT_EXIST_ENTITY("000", "Non abbiamo trovato la seguente: {entity}");

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
