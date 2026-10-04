package pk.edu.fast.cms.users;
import pk.edu.fast.cms.models.Attendance;
import pk.edu.fast.cms.models.AttendanceStatus;
import pk.edu.fast.cms.models.Course;
import pk.edu.fast.cms.models.Section;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors; // java.util.stream.Collectors;
public abstract class Instructor extends Person {
    protected String teacherId;
    protected List<Section> assignedSections;
    public Instructor(String id, String name, String email,
                      String phoneNumber, String teacherId) {
        super(id, name, email);
        this.teacherId = teacherId;
        this.assignedSections = new ArrayList<>();
    }
    public List<Course> viewCourses() {
        return assignedSections.stream() //.stream()
                .map(Section::getCourse) // .map(Section::getCourse);
                .distinct() // distinct courses...
                .collect(Collectors.toList()); //.collect(Collectors.toList());
    }
    public List<Section> viewSections() {
        return assignedSections;
    }
    public List<Student> viewEnrolledStudents(Section section) {
        if (section == null) {
            return new ArrayList<>();
        }
        return section.getEnrolledStudents();
    }
    public void markAttendance(Attendance attendance, AttendanceStatus status) {
    }
    public void updateAttendance(Attendance attendance, AttendanceStatus status) {
    }
    public double calculateAttendancePercentage(Student student, Section section) {
        return 0.0;
    }
    public String getTeacherId() {
        return teacherId;
    }
    public List<Section> getAssignedSections() {
        return assignedSections;
    }
    public void addSection(Section section) {
        if (section != null && !assignedSections.contains(section)) {
            assignedSections.add(section);
        }
    }
}
