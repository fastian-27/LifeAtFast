package pk.edu.fast.cms;
// TESTING EVERYTHING.
import pk.edu.fast.cms.models.*;
import pk.edu.fast.cms.users.AcademicOfficeAdmin;
import pk.edu.fast.cms.users.NormalStudent;
import pk.edu.fast.cms.users.PermanentInstructor;
import pk.edu.fast.cms.users.TeachingAssistant;
import pk.edu.fast.cms.utils.Day;
import pk.edu.fast.cms.data.CourseRepository;

import java.time.LocalTime;
import java.util.List;

import java.time.LocalDate;
import java.time.LocalTime;
public class Main {
    public static void main(String[] args) {
        // ===== Admin / instructor setup =====
        AcademicOfficeAdmin admin = new AcademicOfficeAdmin("Ali", "ali@uni.edu", "0300", "A1");
        PermanentInstructor prof = new PermanentInstructor("Dr. Sara", "sara@uni.edu", "0301", "T1");

        Course oop = new Course("CS101", "OOP", 3);
        Course db  = new Course("CS102", "Databases", 3);
        Course dsa = new Course("CS103", "DSA", 3);
        Section oopA = new Section("A", oop, 30);
        Section dbA  = new Section("A", db, 30);
        Section dsaA = new Section("A", dsa, 30);

        for (Course c : new Course[]{oop, db, dsa}) admin.createCourse(c);
        for (Section s : new Section[]{oopA, dbA, dsaA}) {
            admin.createSection(s);
            admin.assignInstructor(s, prof);
        }
        // correct this bs
        admin.assignRoom(oopA, new Schedule("Mon", LocalTime.of(10, 0), LocalTime.of(11, 30)));
        admin.assignRoom(dbA,  new Schedule("Mon", LocalTime.of(11, 0), LocalTime.of(12, 30))); // clashes with oopA
        admin.assignRoom(dsaA, new Schedule("Tue", LocalTime.of(9, 0),  LocalTime.of(10, 30)));
        System.out.println("--- STUDENT ---");
        NormalStudent bilal  = new NormalStudent("Bilal",  "bilal@uni.edu",  "0302", "S1");
        NormalStudent ayesha = new NormalStudent("Ayesha", "ayesha@uni.edu", "0303", "S2");
        System.out.println("Available courses: " + bilal.viewAvailableCourses());
        bilal.register(oopA);
        bilal.register(dsaA);
        bilal.register(dbA);                                    // blocked: clash
        bilal.submitCourseClashRequest(dbA, "Needed to graduate on time");
        bilal.submitCourseClashRequest(dbA, "Please approve");  // blocked: duplicate
        for (Request r : admin.viewRequests()) admin.approveRequest(r);
        System.out.println("My requests: " + bilal.viewRequests());
        bilal.register(dbA);                                    // allowed now (approved request)

        System.out.println("Registered: " + bilal.viewRegisteredCourses());
        System.out.println("Total credit hours: " + bilal.calculateTotalCreditHours()
                + " | " + oop.getCourseCode() + " has " + bilal.viewCourseCreditHours(oop) + " CH");
        System.out.println("Timetable: " + bilal.viewTimetable());
        bilal.drop(dsaA);
        System.out.println("After drop: " + bilal.viewRegisteredCourses());

        ayesha.register(oopA);

        // Attendance (marked by instructor)
        prof.markAttendance(new Attendance(bilal, oopA, AttendanceStatus.PRESENT), AttendanceStatus.PRESENT);
        prof.markAttendance(new Attendance(bilal, oopA, AttendanceStatus.PRESENT), AttendanceStatus.PRESENT);
        prof.markAttendance(new Attendance(bilal, oopA, AttendanceStatus.ABSENT), AttendanceStatus.ABSENT);
        System.out.println("Attendance: " + bilal.viewAttendance(oopA));
        System.out.printf("Attendance %%: %.1f%n", bilal.viewAttendancePercentage(oopA));

        // ===== Teaching assistant use cases =====
        System.out.println("\n--- TEACHING ASSISTANT ---");
        prof.assignTA(ayesha, oopA);
        TeachingAssistant ta = oopA.getTeachingAssistants().get(0);

        System.out.println(ta.viewAssignedSection());
        System.out.println("Enrolled students: " + ta.viewEnrolledStudents());

        Assignment hw1 = ta.createAssignment("HW1", "Write a class", LocalDate.now().plusDays(7), 10);
        ta.createAssignment("Bad", "Past deadline", LocalDate.now().minusDays(1), 10); // rejected
        ta.setTotalMarks(hw1, 20);
        ta.setAssignmentDeadline(hw1, LocalDate.now().plusDays(5));

        System.out.println("Bilal sees: " + bilal.viewAssignments());
        Submission onTime = bilal.submitAssignment(hw1, "my solution");
        bilal.submitAssignment(hw1, "again");                       // duplicate blocked
        // simulate a submission made after the deadline
        Submission late = new Submission(ayesha, hw1, "late solution", LocalDate.now().plusDays(10));
        hw1.addSubmission(late);

        System.out.println("Submissions: " + ta.viewSubmissions(hw1));
        System.out.println("Late: " + ta.checkLateSubmissions(hw1));

        ta.evaluate();
        ta.assignMarks(late, 25);                                   // invalid: > total marks
        ta.evaluateSubmission(onTime, 18, "Good work");
        ta.evaluateSubmission(late, 12, "Submitted after the deadline");
        ta.evaluate();
        System.out.println("Evaluated: " + hw1.getSubmissions());
        System.out.println("Feedback for Bilal: " + onTime.getFeedback());
    }
}

/**
 * The main entry point for the Campus Management System.
 * Currently testing the Core Domain and Academic Office Admin module.
public class Main {
    public static void main(String[] args) {
        System.out.println("Starting FAST Campus Management System - Admin Module Test...\n");

        AcademicOfficeAdmin admin = new AcademicOfficeAdmin("A-001", "Fateh", "fateh@nu.edu.pk", "0300-1234567");
        System.out.println("System Logged in as: " + admin.getDetails());
        System.out.println("--------------------------------------------------");

        System.out.println("Action: Admin is creating a new course...");
        Course scdCourse = admin.createCourse("SE3005", "Software Construction and Development", 3);
        System.out.println("Success: Created course " + scdCourse.getTitle());

        System.out.println("\nAction: Admin is creating a schedule and section...");
        Schedule sectionSchedule = new Schedule(Day.MONDAY, LocalTime.of(8, 30), LocalTime.of(11, 20), "Room-101");
        Section bcsSection = admin.createSection("SE3005-A", 50, scdCourse, sectionSchedule);
        System.out.println("Success: Created section " + bcsSection.getSectionId() +
                " with capacity " + bcsSection.getCapacity());

        System.out.println("\n--------------------");
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