package pk.edu.fast.cms.models;
import pk.edu.fast.cms.users.Student;
import java.time.LocalDate;
public class Submission {
    private Student student;
    private Assignment assignment;
    private String content, feedback = "";
    private double marks = -1; // -1 = not yet evaluated
    private LocalDate submittedOn;
    public Submission(Student student, Assignment assignment, String content) {
        this(student, assignment, content, LocalDate.now());
    }
    public Submission(Student student, Assignment assignment, String content, LocalDate submittedOn) {
        this.student = student; this.assignment = assignment;
        this.content = content; this.submittedOn = submittedOn;
    }
    public Student getStudent() { return student; }
    public Assignment getAssignment() { return assignment; }
    public String getContent() { return content; }
    public LocalDate getSubmittedOn() { return submittedOn; }
    public double getMarks() { return marks; }
    public void setMarks(double marks) { this.marks = marks; }
    public String getFeedback() { return feedback; }
    public void setFeedback(String feedback) { this.feedback = feedback; }
    public boolean isEvaluated() { return marks >= 0; }
    public boolean isLate() {
        LocalDate deadline = assignment.getDeadline();
        return deadline != null && submittedOn.isAfter(deadline);
    }
    @Override public String toString() {
        return student.getName() + " | " + (isLate() ? "LATE" : "on time") + " | "
                + (isEvaluated() ? "marks " + marks + "/" + assignment.getTotalMarks() : "not evaluated");
    }
}
