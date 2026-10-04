package pk.edu.fast.cms.models;

import pk.edu.fast.cms.users.Student;

public class Attendance {
    private Student student;
    private Section section;
    private AttendanceStatus status;
    public Attendance(Student student, Section section, AttendanceStatus status) {
        this.student = student; this.section = section; this.status = status;
    }
    public Student getStudent() { return student; }
    public Section getSection() { return section; }
    public AttendanceStatus getStatus() { return status; }
    public void setStatus(AttendanceStatus status) { this.status = status; }
    @Override public String toString() { return section + ": " + status; }
}
]