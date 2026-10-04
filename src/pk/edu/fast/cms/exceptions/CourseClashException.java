package pk.edu.fast.cms.exceptions;

/**
 * Thrown when a schedule overlap is detected during registration or assignment.
 */
public class CourseClashException extends CampusException {
    public CourseClashException(String message) {
        super(message);
        // further logic..
    }

}