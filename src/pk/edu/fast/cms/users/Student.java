package pk.edu.fast.cms.users;

import pk.edu.fast.cms.models.*;

import java.util.ArrayList;
import java.util.List;
public abstract class Student extends Person {
    public static final int MAX_CREDIT_HOURS = 18;
    private String studentId;
    private int totalCreditHours;
    private List<Enrollment> enrollments = new ArrayList<>();
    private List<CourseClashRequest> requests = new ArrayList<>();
    public Student(String name, String email, String phone, String studentId) {
        super(name, email, phone);
        this.studentId = studentId;
    }
    public String getStudentId() { return studentId; }
    public boolean isRegistered(Section section) {
        for (Enrollment e : enrollments) if (e.getSection() == section) return true;
        return false;
    }
   // private Section registeredSectionOf(Course course) {
     //   for (Enrollment e : enrollments)
       //     if (e.getSection().getCourseCode.equals(course.getCourseCode())) return e.getSection();
       // return null;
   // }
    private boolean hasApprovedClashRequest(Section section) {
        for (CourseClashRequest r : requests)
            if (r.getSection() == section && r.isApproved()) return true;
        return false;
    }
    /** True if the section's timing overlaps with any section the student is already registered in. */
    public boolean checkSectionClash(Section section) {
        if (section.getSchedule() == null) return false;
        for (Enrollment e : enrollments) {
            if (e.getSection() == section) continue;
            if (section.getSchedule().clashesWith(e.getSection().getSchedule())) return true;
        }
        return false;
    }
    public void register(Section section) {
        if (isRegistered(section)) {
            System.out.println("Already registered in " + section); return;
        }
        // to be seen. showing some prob
       // Section sameCourse = registeredSectionOf(section.getCourseCode());
       // if (sameCourse != null) {
         //   System.out.println("Already registered in " + section.getCourse() + " (section " + sameCourse.getName() + ")"); return;
        //}
        if (section.isFull()) {
            System.out.println(section + " is full."); return;
        }
        if (checkSectionClash(section) && !hasApprovedClashRequest(section)) {
            System.out.println("Cannot register " + section + ": it clashes with another section. Submit a course clash request."); return;
        }
        // getCreditHours
        int newTotal = calculateTotalCreditHours() + section.getCourse().getCreditHours();
        if (newTotal > MAX_CREDIT_HOURS) {
            System.out.println("Cannot register " + section + ": credit hours would be " + newTotal + " (max " + MAX_CREDIT_HOURS + ")."); return;
        }
        enrollments.add(new Enrollment(this, section));
        section.addStudent(this);
        calculateTotalCreditHours();
        System.out.println(getName() + " registered in " + section);
    }
    public void drop(Section section) {
        if (!isRegistered(section)) {
            System.out.println("Not registered in " + section); return;
        }
        enrollments.removeIf(e -> e.getSection() == section);
        section.removeStudent(this);
        calculateTotalCreditHours();
        System.out.println(getName() + " dropped " + section);
    }
    public int calculateTotalCreditHours() {
        totalCreditHours = 0;
        for (Enrollment e : enrollments) totalCreditHours += e.getSection().getCourse().getCreditHours();
        return totalCreditHours;
    }
    public int viewCourseCreditHours(Course course) { return course.getCreditHours(); }
    public List<Section> viewAvailableCourses() {
        List<Section> available = new ArrayList<>();
        for (Section s : Section.getCatalog())
            if (!s.isFull() && registeredSectionOf(s.getCourse()) == null) available.add(s);
        return available;
    }
    public List<Course> viewCourses() {
        List<Course> courses = new ArrayList<>();
        for (Enrollment e : enrollments) courses.add(e.getSection().getCourse());
        return courses;
    }
    public List<Section> viewRegisteredCourses() {
        List<Section> sections = new ArrayList<>();
        for (Enrollment e : enrollments) sections.add(e.getSection());
        return sections;
    }
    public List<Schedule> viewTimetable() {
        List<Schedule> timetable = new ArrayList<>();
        for (Enrollment e : enrollments)
            if (e.getSection().getSchedule() != null) timetable.add(e.getSection().getSchedule());
        return timetable;
    }
    public List<Assignment> viewAssignments() {
        List<Assignment> all = new ArrayList<>();
        for (Enrollment e : enrollments) all.addAll(e.getSection().getAssignments());
        return all;
    }
    public List<Assignment> viewAssignments(Section section) {
        if (!isRegistered(section)) {
            System.out.println("Not registered in " + section);
            return new ArrayList<>();
        }
        return section.getAssignments();
    }
    public Submission submitAssignment(Assignment assignment, String content) {
        Section section = assignment.getSection();
        if (section == null || !isRegistered(section)) {
            System.out.println("You are not enrolled in this assignment's section."); return null;
        }
        if (content == null || content.isBlank()) {
            System.out.println("Submission cannot be empty."); return null;
        }
        Submission existing = assignment.getSubmissionBy(this);
        if (existing != null) {
            System.out.println("You have already submitted " + assignment.getTitle()); return existing;
        }
        Submission s = new Submission(this, assignment, content);
        assignment.addSubmission(s);
        System.out.println(getName() + " submitted " + assignment.getTitle() + (s.isLate() ? " (LATE)" : ""));
        return s;
    }
    public List<Attendance> viewAttendance(Section section) {
        if (!isRegistered(section)) {
            System.out.println("Not registered in " + section);
            return new ArrayList<>();
        }
        return section.getAttendanceFor(this);
    }
    public List<Attendance> viewAttendance() {
        List<Attendance> all = new ArrayList<>();
        for (Enrollment e : enrollments) all.addAll(e.getSection().getAttendanceFor(this));
        return all;
    }
    public double viewAttendancePercentage(Section section) {
        if (!isRegistered(section)) {
            System.out.println("Not registered in " + section); return 0;
        }
        return section.getAttendancePercentage(this);
    }
    public void submitCourseClashRequest(CourseClashRequest request) {
        Section section = request.getSection();
        if (request.getStudent() != this) {
            System.out.println("This request belongs to another student."); return;
        }
        if (isRegistered(section)) {
            System.out.println("Already registered in " + section); return;
        }
        if (!checkSectionClash(section)) {
            System.out.println("No clash for " + section + ", so no request is needed. Register directly."); return;
        }
        for (CourseClashRequest r : requests) {
            if (r.getSection() == section && !r.getStatus().equals("REJECTED")) {
                System.out.println("A request for " + section + " already exists (" + r.getStatus() + ")."); return;
            }
        }
        Request.submit(request);
        requests.add(request);
        System.out.println(getName() + " submitted: " + request);
    }

    public void submitCourseClashRequest(Section section, String reason) {
        submitCourseClashRequest(new CourseClashRequest(this, section, reason));
    }

    /** View Requests (this student's clash requests and their status). */
    public List<CourseClashRequest> viewRequests() { return requests; }

    @Override public String toString() { return getName() + " [" + studentId + "]"; }
}