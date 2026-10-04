package pk.edu.fast.cms;

import pk.edu.fast.cms.users.AcademicOfficeAdmin;
import pk.edu.fast.cms.models.Course;
import pk.edu.fast.cms.models.Section;
import pk.edu.fast.cms.models.Schedule;
import pk.edu.fast.cms.utils.Day;
import pk.edu.fast.cms.data.CourseRepository;

import java.time.LocalTime;
import java.util.List;

/**
 * The main entry point for the Campus Management System.
 * Currently testing the Core Domain and Academic Office Admin module.
 */
public class Main {
    public static void main(String[] args) {
        System.out.println("Starting FAST Campus Management System - Admin Module Test...\n");

        // 1. Initialize the Admin (Using 'Fateh' from the assignment scenario)
        AcademicOfficeAdmin admin = new AcademicOfficeAdmin("A-001", "Fateh", "fateh@nu.edu.pk", "0300-1234567");
        System.out.println("System Logged in as: " + admin.getDetails());
        System.out.println("--------------------------------------------------");

        // 2. Admin creates a Course (Using your actual course details!)
        System.out.println("Action: Admin is creating a new course...");
        Course scdCourse = admin.createCourse("SE3005", "Software Construction and Development", 3);
        System.out.println("Success: Created course " + scdCourse.getTitle());

        // 3. Admin creates a Schedule and a Section
        System.out.println("\nAction: Admin is creating a schedule and section...");
        Schedule sectionSchedule = new Schedule(Day.MONDAY, LocalTime.of(8, 30), LocalTime.of(11, 20), "Room-101");
        Section bcsSection = admin.createSection("SE3005-A", 50, scdCourse, sectionSchedule);
        System.out.println("Success: Created section " + bcsSection.getSectionId() +
                " with capacity " + bcsSection.getCapacity());

        // 4. Test File Persistence (Reading back what was just saved to the hard drive)
        System.out.println("\n--------------------------------------------------");
        System.out.println("Testing Data Persistence (File I/O)...");
        CourseRepository repo = new CourseRepository();
        List<Course> savedCourses = repo.loadAllCourses();

        System.out.println("Courses currently loaded from data/courses.txt:");
        for (Course c : savedCourses) {
            System.out.println(" -> " + c.getCourseCode() + " | " + c.getTitle() + " (" + c.getCreditHours() + " Cr)");
        }

        System.out.println("\nTest Complete!");
    }
}