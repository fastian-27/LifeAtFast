package pk.edu.fast.cms.models;

import pk.edu.fast.cms.users.Instructor;
import pk.edu.fast.cms.users.Student;
import pk.edu.fast.cms.users.TeachingAssistant;
import pk.edu.fast.cms.exceptions.CourseFullException;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents a specific offering of a Course, tied to a schedule and instructor.
 */
public class Section {
    private String sectionId;
    private int capacity;
    private Course course;
    private Instructor instructor;
    private TeachingAssistant teachingAssistant;
    private Schedule schedule;
    private List<Enrollment> enrollments;

    public Section(String sectionId, int capacity, Course course, Schedule schedule) {
        this.sectionId = sectionId;
        this.capacity = capacity;
        this.course = course;
        this.schedule = schedule;
        this.enrollments = new ArrayList<>();
    }

    /**
     * Checks if the section has reached its maximum capacity.
     */
    public boolean isFull() {
        return enrollments.size() >= capacity;
    }

    /**
     * Returns the number of remaining seats.
     */
    public int getAvailableSeats() {
        return capacity - enrollments.size();
    }

    /**
     * Detects if this section's schedule overlaps with another section's schedule.
     */
    public boolean hasClash(Section otherSection) {
        if (otherSection == null || otherSection.getSchedule() == null || this.schedule == null) {
            return false;
        }
        return this.schedule.hasClash(otherSection.getSchedule());
    }

    public void assignInstructor(Instructor instructor) {
        this.instructor = instructor;
    }

    public void assignTA(TeachingAssistant ta) {
        this.teachingAssistant = ta;
    }

    // --- INTEGRATION STUBS FOR PERSON C ---

    public void enroll(Student student) {
        if (isFull()) {
            throw new CourseFullException("Section " + sectionId + " is full. Cannot enroll student.");
        }
        // TODO (Person C): Implement Enrollment object creation and addition to enrollments list
    }

    public void drop(Student student) {
        // TODO (Person C): Implement logic to remove student from enrollments list
    }

    public List<Student> getEnrolledStudents() {
        // TODO (Person C): Iterate through enrollments list and extract Student objects
        return new ArrayList<>();
    }

    // --- Getters & Setters ---
    public String getSectionId() { return sectionId; }
    public int getCapacity() { return capacity; }
    public void setCapacity(int capacity) { this.capacity = capacity; }
    public Course getCourse() { return course; }
    public Instructor getInstructor() { return instructor; }
    public TeachingAssistant getTeachingAssistant() { return teachingAssistant; }
    public Schedule getSchedule() { return schedule; }
    public List<Enrollment> getEnrollments() { return enrollments; }
}