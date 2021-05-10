package wolox.training.exceptions;

/**
 * This class permits generated custom exception with message and status response
 *
 * @author carolina.marulanda@wolox.co
 */
public class BookAlreadyOwnedException extends RuntimeException{
    /**
     * Custom exception BookAlreadyOwnedException
     * @param message: exception message
     */
    public BookAlreadyOwnedException(String message) {
        super(message);
    }

}
