package wolox.training.exceptions;

/**
 * This class permits generated custom exception with message and status response
 *
 * @author carolina.marulanda@wolox.co
 */
public class UserNotFoundException extends RuntimeException{
    /**
     * Custom exception UserNotFoundException
     * @param message: exception message
     */
    public UserNotFoundException(String message) {
        super(message);
    }

}
