package wolox.training.exceptions;

/**
 * This class permits generated custom exception with message and status response
 *
 * @author carolina.marulanda@wolox.co
 */
public class ConstraintViolationException extends RuntimeException{
    /**
     * Custom exception ConstraintViolationException
     * @param message: exception message
     */
    public ConstraintViolationException(String message) {
        super(message);
    }

}
