package pk.edu.fast.cms.exceptions;

/**
 * Base exception class for the Campus Management System.
 * All custom domain exceptions inherit from this to allow centralized error handling.
 * All custom domain exceptions inherit from this to allow centralized error handling.
 */
public abstract class CampusException extends RuntimeException {

    /**
     * Constructs a new CampusException with the specified detail message.
     *
     * @param message The detail message explaining the error.
     */
    public CampusException(String message) {
        super(message);
    }
}
