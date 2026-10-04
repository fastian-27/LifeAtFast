package pk.edu.fast.cms.utils;

import pk.edu.fast.cms.models.Request;
import java.util.Comparator;

/**
 * Sorts requests by priority (lowest integer first), falling back to date if priorities match.
 */
public class RequestPriorityComparator implements Comparator<Request> {
    @Override
    public int compare(Request r1, Request r2) {
        int priorityComparison = Integer.compare(r1.getPriority(), r2.getPriority());
        if (priorityComparison != 0) {
            return priorityComparison;
        }
        // If priority is the same, older requests get processed first
        return r1.getRequestDate().compareTo(r2.getRequestDate());
    }
}