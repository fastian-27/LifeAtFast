package pk.edu.fast.cms.models;
import java.time.LocalDate; // import java.time.LocalDate;
public class FYPEvaluation {
    private String evaluationId;
    private LocalDate evaluationDate;
    private double score;
    private String feedback;
    public FYPEvaluation(String evaluationId) {
        this.evaluationId = evaluationId;
        this.evaluationDate = LocalDate.now();
    }
    public void evaluate(double score) {
        this.score = score;
        this.evaluationDate = LocalDate.now(); // LocalDate.now()
    }
    public void addFeedback(String feedback) {
        this.feedback = feedback;
    }
    public String getEvaluationId()      {
        return evaluationId; }
    public LocalDate getEvaluationDate() {
        return evaluationDate; }
    public double getScore()             {
        return score; }
    public String getFeedback()          {
        return feedback; }
}
