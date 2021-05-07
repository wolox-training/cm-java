package wolox.training.exceptions;

/**
 * This class permits generated custom exception with message and status response
 *
 * @author carolina.marulanda@wolox.co
 */
public class BookNotFoundException extends RuntimeException{

    /**
     * Custom exception BookNotFoundException
     * @param message: exception message
     */
    public BookNotFoundException(String message) {
        super(message);
    }
}
