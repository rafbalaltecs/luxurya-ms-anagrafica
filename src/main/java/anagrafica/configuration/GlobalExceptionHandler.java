package anagrafica.configuration;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import anagrafica.exception.RestException;
import anagrafica.exception.RestResourceNotFoundException;
import lombok.extern.slf4j.Slf4j;

@ControllerAdvice
@Slf4j
public class GlobalExceptionHandler {
	
	@ExceptionHandler(RestException.class)
    public ResponseEntity<Object> handleRestException(RestException ex) {
    	final String tid = UUID.randomUUID().toString();
        Map<String, Object> body = new HashMap<>();
        body.put("timestamp", LocalDateTime.now().toString());
        body.put("error", ex.getMessage());
        body.put("tid", tid);
        log.error("error: " + ex.getMessage() + "\n tid:" + tid + " \n httpStatus: " + HttpStatus.BAD_REQUEST.toString());
        return new ResponseEntity<>(body, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(RestResourceNotFoundException.class)
    public ResponseEntity<Object> handleRestResourceNotFoundException(RestResourceNotFoundException ex) {
        final String tid = UUID.randomUUID().toString();
        Map<String, Object> body = new HashMap<>();
        body.put("timestamp", LocalDateTime.now().toString());
        body.put("error", ex.getMessage());
        body.put("tid", tid);
        log.error("error: " + ex.getMessage() + "\n tid:" + tid + " \n httpStatus: " + HttpStatus.NOT_FOUND.toString());
        return new ResponseEntity<>(body, HttpStatus.NOT_FOUND);
    }
	
	@ExceptionHandler(Exception.class)
    public ResponseEntity<Object> handleGenericException(Exception ex) {
    	final String tid = UUID.randomUUID().toString();
        Map<String, Object> body = new HashMap<>();
        body.put("timestamp", LocalDateTime.now().toString());
        body.put("error", "Internal Server Error");
        body.put("details", ex.getMessage());
        body.put("tid", tid);
        log.error("error: " + ex.getMessage() + "\n tid:" + tid + " \n httpStatus: " + HttpStatus.INTERNAL_SERVER_ERROR.toString());
        return new ResponseEntity<>(body, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
