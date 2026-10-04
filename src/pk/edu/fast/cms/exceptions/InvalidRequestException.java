package pk.edu.fast.cms.exceptions;

/**
 * Thrown when an invalid operation is performed on a Request (e.g., approving an already processed request).
 */
public class InvalidRequestException extends CampusException {
    public InvalidRequestException(String message) {
        super(message);
        // adding further details
    }
}