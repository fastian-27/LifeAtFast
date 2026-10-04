package pk.edu.fast.cms.models;

import java.time.LocalDate;

/**
 * A specific request to resolve scheduling conflicts between two sections.
 */
public class CourseClashRequest extends Request {
    private Section conflictingSection;
    private Section requestedSection;

    public CourseClashRequest(String requestId, LocalDate requestDate, String description, int priority,
                              Section conflictingSection, Section requestedSection) {
        super(requestId, requestDate, description, priority);
        this.conflictingSection = conflictingSection;
        this.requestedSection = requestedSection;
    }

    public String getConflictDetails() {
        return String.format("Clash between %s and requested %s",
                conflictingSection.getSectionId(), requestedSection.getSectionId());
    }

    @Override
    public String getDetails() {
        return "Course Clash Request: " + description + " | Details: " + getConflictDetails();
    }
}