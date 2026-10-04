package pk.edu.fast.cms.users;
import pk.edu.fast.cms.models.Assignment;
import pk.edu.fast.cms.models.Evaluator;
import pk.edu.fast.cms.models.Section;
import pk.edu.fast.cms.models.Submission;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class TeachingAssistant extends Student implements Evaluator {
    private Section assignedSection;
    public TeachingAssistant(String name, String email, String phone, String studentId, Section assignedSection) {
        super(name, email, phone, studentId);
        this.assignedSection = assignedSection;
    }
        //public TeachingAssistant(NormalStudent student) {
        //super(name,email,phone);
    public Section getAssignedSection() { return assignedSection; }
    private boolean ownsAssignment(Assignment a) {
        if (a == null || a.getSection() != assignedSection) {
            System.out.println("That assignment does not belong to your section (" + assignedSection + ").");
            return false;
        }
        return true;
    }
    private boolean ownsSubmission(Submission s) {
        return s != null && ownsAssignment(s.getAssignment());
    }
    public String viewAssignedSection() { return assignedSection.getDetails(); }
    public List<Student> viewEnrolledStudents() { return assignedSection.getStudents(); }
    public Assignment createAssignment(String title, String description, LocalDate deadline, double totalMarks) {
        if (title == null || title.isBlank()) {
            System.out.println("Assignment title cannot be empty."); return null;
        }
        Assignment a = new Assignment(title, description, null, 0);
        a.setSection(assignedSection); // lets the included use cases pass the ownership check
        if (!setTotalMarks(a, totalMarks) || !setAssignmentDeadline(a, deadline)) return null;
        assignedSection.addAssignment(a);
        System.out.println("Created assignment: " + a);
        return a;
    }
    public boolean setTotalMarks(Assignment assignment, double totalMarks) {
        if (!ownsAssignment(assignment)) return false;
        if (totalMarks <= 0) {
            System.out.println("Total marks must be greater than 0."); return false;
        }
        for (Submission s : assignment.getSubmissions()) {
            if (s.isEvaluated() && s.getMarks() > totalMarks) {
                System.out.println("Total marks cannot be lower than marks already awarded."); return false;
            }
        }
        assignment.setTotalMarks(totalMarks);
        return true;
    }
    public boolean setAssignmentDeadline(Assignment assignment, LocalDate deadline) {
        if (!ownsAssignment(assignment)) return false;
        if (deadline == null || deadline.isBefore(LocalDate.now())) {
            System.out.println("Deadline cannot be empty or in the past."); return false;
        }
        assignment.setDeadline(deadline);
        return true;
    }
    public List<Submission> viewSubmissions(Assignment assignment) {
        if (!ownsAssignment(assignment)) return new ArrayList<>();
        return assignment.getSubmissions();
    }
    public List<Submission> checkLateSubmissions(Assignment assignment) {
        List<Submission> late = new ArrayList<>();
        for (Submission s : viewSubmissions(assignment))
            if (s.isLate()) late.add(s);
        return late;  // late submission ki list bann gyi... we could alternatively just flag it.
    }
    // evaluation
    public boolean assignMarks(Submission submission, double marks) {
        if (!ownsSubmission(submission)) return false;
        double total = submission.getAssignment().getTotalMarks();
        if (marks < 0 || marks > total) {
            System.out.println("Marks must be between 0 and " + total + "."); return false;
        }
        submission.setMarks(marks);
        return true;
    }
    public void giveFeedback(Submission submission, String comments) {
        if (!ownsSubmission(submission)) return;
        if (comments == null || comments.isBlank()) { // check isBlank() ....
            System.out.println("Feedback cannot be empty."); return;
        }
        submission.setFeedback(comments); // yes
    }
    public void evaluateSubmission(Submission submission, double marks) {
        evaluateSubmission(submission, marks, null);
    }
    public void evaluateSubmission(Submission submission, double marks, String comments) {
        if (!assignMarks(submission, marks)) return;
        if (comments != null && !comments.isBlank()) giveFeedback(submission, comments);
    }
    @Override public void evaluate() { // overriding the method defined in Interface  Evaluator
        int pending = 0;
        for (Assignment a : assignedSection.getAssignments())
            for (Submission s : a.getSubmissions()) if (!s.isEvaluated()) pending++;
        System.out.println("TA " + getName() + ": " + pending + " submission(s) pending evaluation in " + assignedSection);
    }
    @Override public String getRole() { return "Teaching Assistant"; }
    @Override
    public String getDetails() {
        return "";
    }
}
