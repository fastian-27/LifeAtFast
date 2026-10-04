package pk.edu.fast.cms.models;

import pk.edu.fast.cms.users.Instructor;
import pk.edu.fast.cms.users.Student;
import pk.edu.fast.cms.users.TeachingAssistant;

import java.util.ArrayList;
import java.util.List;

public class Section {
    // Sections published by the admin; students browse this to find available courses
    private static List<Section> catalog = new ArrayList<>();

    private String name;
    private Course course;
    private int capacity;
    private Instructor instructor;
    private Schedule schedule;
    private String room;
    private List<Student> students = new ArrayList<>();
    private List<Attendance> attendanceRecords = new ArrayList<>();
    private List<Assignment> assignments = new ArrayList<>();
    private List<TeachingAssistant> teachingAssistants = new ArrayList<>();

    public Section(String name, Course course, int capacity) {
        this.name = name; this.course = course; this.capacity = capacity;
    }
    public static List<Section> getCatalog() { return catalog; }
    public static void addToCatalog(Section s) { if (!catalog.contains(s)) catalog.add(s); }
    public static void removeFromCatalog(Section s) { if (catalog.contains(s)) catalog.remove(s); }
    // add ya remmove krny sy pehly existence check to remove redundancy and duplication in first case and NOTFOUNDException
    // in the other.
    public Course getCourse() {return course;}
    public String getName() { return name; }
    public String getCourseCode() { return course.getCourseCode(); }
    public int getCapacity() { return capacity; }
    public void setCapacity(int capacity) { this.capacity = capacity; }
    public Instructor getInstructor() { return instructor; }
    public void setInstructor(Instructor instructor) { this.instructor = instructor; }
    public Schedule getSchedule() { return schedule; }
    public void setSchedule(Schedule schedule) { this.schedule = schedule; }
    public String getRoom() { return room; }
    public void setRoom(String room) { this.room = room; }
    public boolean isFull() { return students.size() >= capacity; }
    public int getAvailableSeats() { return capacity - students.size(); }
    public List<Student> getStudents() { return students; }
    public void addStudent(Student s) { students.add(s); }
    public void removeStudent(Student s) { students.remove(s); }
    public List<Attendance> getAttendanceRecords() { return attendanceRecords; }
    public void addAttendance(Attendance a) { if (!attendanceRecords.contains(a)) attendanceRecords.add(a); }
    public List<Attendance> getAttendanceFor(Student student) {
        List<Attendance> result = new ArrayList<>();
        for (Attendance a : attendanceRecords) if (a.getStudent() == student) result.add(a);
        return result;
    }
    public double getAttendancePercentage(Student student) {
        List<Attendance> records = getAttendanceFor(student);
        if (records.isEmpty()) return 0;
        int present = 0;
        for (Attendance a : records)
            // Present aur Late ki tafreek nahi hai...
            if (a.getStatus() == AttendanceStatus.PRESENT || a.getStatus() == AttendanceStatus.LATE) present++;
        return (present * 100.0) / records.size();
    }
    public List<Assignment> getAssignments() { return assignments; }
    public void addAssignment(Assignment a) { a.setSection(this); assignments.add(a); }
    public List<TeachingAssistant> getTeachingAssistants() { return teachingAssistants; }
    public void addTeachingAssistant(TeachingAssistant ta) { teachingAssistants.add(ta); }
    public String getDetails() {
        return "Section " + this + " | " + course.getTitle() + " (" + course.getCreditHours() + " CH)\n"
                + "Instructor: " + (instructor == null ? "Not assigned" : instructor.getName()) + "\n"
                + "Schedule: " + (schedule == null ? "TBA" : schedule) + "\n"
                + "Room: " + (room == null ? "TBA" : room) + "\n"
                + "Enrolled: " + students.size() + "/" + capacity;
    }
    @Override public String toString() { return course.getCourseCode() + "-" + name; }
}
