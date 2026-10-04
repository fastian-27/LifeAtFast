package pk.edu.fast.cms.models;

import java.util.HashSet;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * Represents an academic course offered by the university.
 */
public class Course {
    private String courseCode;
    private String title;
    private int creditHours;
    private Set<Course> prerequisites; // Set prevents duplicate prerequisites
    private List<Section> sections;

    public Course(String courseCode, String title, int creditHours) {
        this.courseCode = courseCode;
        this.title = title;
        this.creditHours = creditHours;
        this.prerequisites = new HashSet<>();
        this.sections = new ArrayList<>();
    }

    /**
     * Adds a prerequisite course.
     * @param course The course required before taking this one.
     */
    public void addPrerequisite(Course course) {
        if (course != null && !this.equals(course)) {
            this.prerequisites.add(course);
        }
    }

    /**
     * Links a section to this course.
     */
    public void addSection(Section section) {
        if (section != null && !this.sections.contains(section)) {
            this.sections.add(section);
        }
    }

    // --- Getters & Setters ---
    public String getCourseCode() { return courseCode; }
    public String getTitle() { return title; }
    public int getCreditHours() { return creditHours; }
    public Set<Course> getPrerequisites() { return prerequisites; }
    public List<Section> getSections() { return sections; }
}