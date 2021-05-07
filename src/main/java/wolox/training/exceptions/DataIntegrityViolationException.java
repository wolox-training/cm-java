package wolox.training.exceptions;

/**
 * This class permits generated custom exception with message and status response
 *
 * @author carolina.marulanda@wolox.co
 */
public class DataIntegrityViolationException extends RuntimeException{

    /**
     * Custom exception DataIntegrityViolationException
     * @param message: exception message
     */
    public DataIntegrityViolationException(String message){
        super(message);
    }

}
