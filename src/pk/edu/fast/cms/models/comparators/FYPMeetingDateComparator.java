package pk.edu.fast.cms.models.comparators;
import org.example.models.FYPMeeting;
import java.util.Comparator; // Comparator class ko extend...
public class FYPMeetingDateComparator implements Comparator<FYPMeeting> {
    @Override
    public int compare(FYPMeeting m1, FYPMeeting m2) {
        return m1.getMeetingDate().compareTo(m2.getMeetingDate());
    }
}