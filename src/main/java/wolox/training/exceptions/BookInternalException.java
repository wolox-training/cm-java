package wolox.training.exceptions;

/**
 * This class permits generated custom exception with message and status response
 *
 * @author carolina.marulanda@wolox.co
 */
public class BookInternalException extends RuntimeException {

    /**
     * Custom exception BookInternalException
     *
     * @param message: exception message
     */
    public BookInternalException(String message) {
        super(message);
    }

}
