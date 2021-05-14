package wolox.training.exceptions;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

/**
 * This class permits managed custom exceptions with message and status response
 *
 * @author carolina.marulanda@wolox.co
 */
@ControllerAdvice
public class RestExceptionHandler extends ResponseEntityExceptionHandler {

    /**
     * Intercepts custom exception BookNotFoundException and sends exception´s details
     *
     * @param ex:      BookNotFoundException, exception received
     * @param request: request received
     *
     * @return {@link ResponseEntity} response with object that contains exception´s details
     */
    @ExceptionHandler({BookNotFoundException.class})
    protected ResponseEntity<Object> handleNotFound(
            Exception ex, WebRequest request) {
        return handleExceptionInternal(ex, "Book not found",
                new HttpHeaders(), HttpStatus.NOT_FOUND, request);
    }

    /**
     * Intercepts custom exception BookIdMismatchException and sends exception´s details
     *
     * @param ex:      BookIdMismatchException, exception received
     * @param request: request received
     *
     * @return {@link ResponseEntity} response with object that contains exception´s details
     */
    @ExceptionHandler({BookIdMismatchException.class,
            ConstraintViolationException.class,
            DataIntegrityViolationException.class})
    public ResponseEntity<Object> handleBadRequest(
            Exception ex, WebRequest request) {
        return handleExceptionInternal(ex, ex.getLocalizedMessage(),
                new HttpHeaders(), HttpStatus.BAD_REQUEST, request);
    }

    /**
     * Intercepts custom exception BookAlreadyOwnedException and sends exception´s details
     *
     * @param ex:      BookAlreadyOwnedException, exception received
     * @param request: request received
     *
     * @return {@link ResponseEntity} response with object that contains exception´s details
     */
    @ExceptionHandler({BookAlreadyOwnedException.class})
    public ResponseEntity<Object> handleAlreadyOwnedBook(
            Exception ex, WebRequest request) {
        return handleExceptionInternal(ex, ex.getLocalizedMessage(),
                new HttpHeaders(), HttpStatus.BAD_REQUEST, request);
    }

    /**
     * Intercepts custom exception UserNotFoundException and sends exception´s details
     *
     * @param ex:      UserNotFoundException, exception received
     * @param request: request received
     *
     * @return {@link ResponseEntity} response with object that contains exception´s details
     */
    @ExceptionHandler({UserNotFoundException.class})
    public ResponseEntity<Object> handleUserNotFoundException(
            Exception ex, WebRequest request) {
        return handleExceptionInternal(ex, ex.getLocalizedMessage(),
                new HttpHeaders(), HttpStatus.NOT_FOUND, request);
    }

}
