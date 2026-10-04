package pk.edu.fast.cms.models;
import pk.edu.fast.cms.utils.RequestStatus;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * Abstract base class for all academic requests submitted by students.
 */
public abstract class Request {
    protected String requestId;
    protected LocalDate requestDate;
    protected String description;
    public RequestStatus status;
    private static List<Request> allRequests = new ArrayList<>();
    protected int priority; // Lower number = higher priority (e.g., 1 is urgent)
    public Request(String requestId, LocalDate requestDate, String description, int priority) {
        this.requestId = requestId;
        this.requestDate = requestDate;
        this.description = description;
        this.priority = priority;
    }
    //  A request only becomes visible to the admin once it is submitted. */
    public static void submit(Request request) {
        if (!allRequests.contains(request)) allRequests.add(request);
    }
    public abstract String getDetails();
    public int getPriority() { return priority; }
    public LocalDate getRequestDate() { return requestDate; }
    public String getRequestId() { return requestId; }
    public static List<Request> getAllRequests() { return allRequests; }
    public boolean isApproved() { return status.equals("APPROVED"); }
}

