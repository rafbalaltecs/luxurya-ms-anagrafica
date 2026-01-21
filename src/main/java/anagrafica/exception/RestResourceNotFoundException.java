package anagrafica.exception;

public class RestResourceNotFoundException extends RuntimeException {
    public RestResourceNotFoundException(String message) {
        super(message);
    }
}
