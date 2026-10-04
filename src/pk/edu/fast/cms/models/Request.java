package pk.edu.fast.cms.models;

import pk.edu.fast.cms.utils.RequestStatus;
import java.time.LocalDate;

/**
 * Abstract base class for all academic requests submitted by students.
 */
public abstract class Request {
    protected String requestId;
    protected LocalDate requestDate;
    protected String description;
    protected RequestStatus status;
    protected int priority; // Lower number = higher priority (e.g., 1 is urgent)

    public Request(String requestId, LocalDate requestDate, String description, int priority) {
        this.requestId = requestId;
        this.requestDate = requestDate;
        this.description = description;
        this.priority = priority;
        this.status = RequestStatus.PENDING;
    }

    public void submit() {
        this.status = RequestStatus.PENDING;
    }

    public abstract String getDetails();

    // --- Getters & Setters ---
    public RequestStatus getStatus() { return status; }
    public void setStatus(RequestStatus status) { this.status = status; }
    public int getPriority() { return priority; }
    public LocalDate getRequestDate() { return requestDate; }
    public String getRequestId() { return requestId; }
}