package pk.edu.fast.cms.models;
import pk.edu.fast.cms.models.Request;
import pk.edu.fast.cms.users.Student;

    import java.time.LocalDate;
 //A specific request to resolve scheduling conflicts between two sections.

public class CourseClashRequest extends Request {
    private Student student;
    private Section section;
    private String reason; // reason must be concrete...
    // ensure reason NOT empty.
    public CourseClashRequest(Student student, Section section, String reason) {
        super(requestId, requestDate, description, priority);
        this.student = student;
        this.section = section;
        this.reason = reason;
    }
    public Student getStudent() { return student; }
    public Section getSection() { return section; }
    public String getReason() {
        return reason;
    }
    @Override public String toString() {
        return "ClashRequest[" + student.getName() + " -> " + section + ", " + getStatus() + "]";
    }
    @Override
    public String getDetails() {
        return "";
    }
}
 /*
public class CourseClashRequest extends Request {
    private Section conflictingSection;
    private Section requestedSection;

    public CourseClashRequest(String requestId, LocalDate requestDate, String description, int priority,
                              Section conflictingSection, Section requestedSection) {
        super(requestId, requestDate, description, priority);
        this.conflictingSection = conflictingSection;
        this.requestedSection = requestedSection;
    }
    // Priority comes here.... how much...
    public String getConflictDetails() {
        return String.format("Clash between %s and requested %s",
                conflictingSection.getSectionId(), requestedSection.getSectionId());
    }
    @Override
    public String getDetails() {
        return "Course Clash Request: " + description + " | Details: " + getConflictDetails();
    }
    public class CourseClashRequest extends Request {
        private Student student;
        private Section section;
        private String reason; // reason must be concrete...
        // ensure reason NOT empty.
        public CourseClashRequest(Student student, Section section, String reason) {
            super();
            this.student = student;
            this.section = section;
            this.reason = reason;
        }
        public Student getStudent() { return student; }
        public Section getSection() { return section; }
        public String getReason() {
            return reason;
        }
        @Override public String toString() {
            return "ClashRequest[" + student.getName() + " -> " + section + ", " + getStatus() + "]";
        }
        */