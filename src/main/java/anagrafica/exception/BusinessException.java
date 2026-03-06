package anagrafica.exception;

public class BusinessException extends RuntimeException {

    private final String code;
    private final String description;

    public BusinessException(String code, String description) {
        super(description);
        this.code = code;
        this.description = description;
    }

    public String getCode() {
        return code;
    }

    public String getDescription() {
        return description;
    }

    @Override
    public String toString() {
        return "BusinessException{code='" + code + "', description='" + description + "'}";
    }
}