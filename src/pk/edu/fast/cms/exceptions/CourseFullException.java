package pk.edu.fast.cms.exceptions;

/**
 * Thrown when an attempt is made to enroll a student in a section that has reached its capacity.
 */
public class CourseFullException extends CampusException {
    public CourseFullException(String message) {
        super(message);
    }
}