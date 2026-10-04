package pk.edu.fast.cms.models;

import pk.edu.fast.cms.utils.RequestCategory;
import java.time.LocalDate;
/**
 * Handles standard requests categorized by type (e.g., Grading, Professor).
 */
public class GenericRequest extends Request {
    private RequestCategory category;
    public GenericRequest(String requestId, LocalDate requestDate, String description, int priority, RequestCategory category) {
        super(requestId, requestDate, description, priority);
        this.category = category;
    }
    public RequestCategory getCategory() {
        return category;
    }
    @Override
    public String getDetails() {
        return "Generic Request [" + category.name() + "]: " + description;
    }
}