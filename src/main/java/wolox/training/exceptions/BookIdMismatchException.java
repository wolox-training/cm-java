package wolox.training.exceptions;

/**
 * This class permits generated custom exception with message and status response
 *
 * @author carolina.marulanda@wolox.co
 */
public class BookIdMismatchException extends RuntimeException{
    /**
     * Custom exception BookIdMismatchException
     * @param message: exception message
     */
    public BookIdMismatchException(String message) {
        super(message);
    }
}
