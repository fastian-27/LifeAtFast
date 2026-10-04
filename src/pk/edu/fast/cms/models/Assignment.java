package pk.edu.fast.cms.models;
import pk.edu.fast.cms.users.Student;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
public class Assignment {
    private String title, description;
    private LocalDate deadline;
    private double totalMarks;
    private Section section;
    private List<Submission> submissions = new ArrayList<>();
    public Assignment(String title, String description, LocalDate deadline, double totalMarks) {
        this.title = title; this.description = description;
        this.deadline = deadline; this.totalMarks = totalMarks;
    }
    public String getTitle() { return title; }
    public String getDescription() { return description; }
    public LocalDate getDeadline() { return deadline; }
    public void setDeadline(LocalDate deadline) { this.deadline = deadline; }
    public double getTotalMarks() { return totalMarks; }
    public void setTotalMarks(double totalMarks) { this.totalMarks = totalMarks; }
    public Section getSection() { return section; }
    public void setSection(Section section) { this.section = section; }
    public List<Submission> getSubmissions() { return submissions; }
    public void addSubmission(Submission s) { submissions.add(s); }
    public Submission getSubmissionBy(Student student) {
        for (Submission s : submissions) if (s.getStudent() == student) return s;
        return null;
    }
    @Override public String toString() {
        return title + " (due " + deadline + ", " + totalMarks + " marks)";
    }
}
