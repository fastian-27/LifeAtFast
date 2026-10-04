package pk.edu.fast.cms.users;

import pk.edu.fast.cms.models.Course;
import pk.edu.fast.cms.models.Request;
import pk.edu.fast.cms.models.Section;
import pk.edu.fast.cms.models.Schedule;
import pk.edu.fast.cms.utils.RequestStatus;
import pk.edu.fast.cms.utils.SystemLogger;
import pk.edu.fast.cms.exceptions.InvalidRequestException;
import pk.edu.fast.cms.data.CourseRepository;

import java.util.List;

/**
 * The administrative controller responsible for university infrastructure and request handling.
 */
public class AcademicOfficeAdmin extends Person {

    public AcademicOfficeAdmin(String id, String name, String email, String phone) {
        super(id, name, email, phone);
    }

    @Override
    public String getRole() {
        return "Academic Office Admin";
    }

    @Override
    public String getDetails() {
        return "Admin ID: " + id + " | Name: " + name;
    }

    // --- Course Lifecycle Management ---

    public Course createCourse(String code, String title, int creditHours) {
        Course newCourse = new Course(code, title, creditHours);

        // 1. Save to the text file using the repository
        CourseRepository repository = new CourseRepository();
        repository.saveCourse(newCourse);

        // 2. Log the action
        SystemLogger.getInstance().log(SystemLogger.Level.INFO, "AcademicOfficeAdmin", this.id,
                "Created course: " + code);
        return newCourse;
    }

    public void updateCourse(Course course, String newTitle, int newCreditHours) {
        if (course != null) {
            // Note: Since we didn't add setters for title/credits in Course to maintain immutability,
            // in a full system you would add them, or replace the course object in the repository.
            SystemLogger.getInstance().log(SystemLogger.Level.INFO, "AcademicOfficeAdmin", this.id,
                    "Updated course: " + course.getCourseCode());
        }
    }

    // --- Section Lifecycle & Assignments ---

    public Section createSection(String sectionId, int capacity, Course course, Schedule schedule) {
        Section newSection = new Section(sectionId, capacity, course, schedule);
        course.addSection(newSection);
        SystemLogger.getInstance().log(SystemLogger.Level.INFO, "AcademicOfficeAdmin", this.id,
                "Created section: " + sectionId + " for course: " + course.getCourseCode());
        return newSection;
    }

    public void setSectionCapacity(Section section, int newCapacity) {
        if (section != null) {
            section.setCapacity(newCapacity);
            SystemLogger.getInstance().log(SystemLogger.Level.INFO, "AcademicOfficeAdmin", this.id,
                    "Changed capacity of " + section.getSectionId() + " to " + newCapacity);
        }
    }

    public void assignRoom(Section section, String room) {
        if (section != null && section.getSchedule() != null) {
            section.getSchedule().setRoom(room);
            SystemLogger.getInstance().log(SystemLogger.Level.INFO, "AcademicOfficeAdmin", this.id,
                    "Assigned room " + room + " to section " + section.getSectionId());
        }
    }

    public void assignInstructor(Section section, Instructor instructor) {
        if (section != null && instructor != null) {
            section.assignInstructor(instructor);
            SystemLogger.getInstance().log(SystemLogger.Level.INFO, "AcademicOfficeAdmin", this.id,
                    "Assigned instructor " + instructor.getId() + " to section " + section.getSectionId());
        }
    }

    // --- Request Processing Pipeline ---

    public void viewRequests(List<Request> systemRequests) {
        // Sorts using the comparator we built
        systemRequests.sort(new pk.edu.fast.cms.utils.RequestPriorityComparator());
        System.out.println("--- Pending Requests (Sorted by Priority) ---");
        for (Request req : systemRequests) {
            if (req.getStatus() == RequestStatus.PENDING) {
                System.out.println(req.getDetails());
            }
        }
    }

    public void approveRequest(Request request) {
        if (request != null && request.getStatus() == RequestStatus.PENDING) {
            request.setStatus(RequestStatus.APPROVED);
            SystemLogger.getInstance().log(SystemLogger.Level.INFO, "AcademicOfficeAdmin", this.id,
                    "Approved request: " + request.getRequestId());
        } else {
            throw new InvalidRequestException("Request is either null or already processed.");
        }
    }

    public void rejectRequest(Request request) {
        if (request != null && request.getStatus() == RequestStatus.PENDING) {
            request.setStatus(RequestStatus.REJECTED);
            SystemLogger.getInstance().log(SystemLogger.Level.INFO, "AcademicOfficeAdmin", this.id,
                    "Rejected request: " + request.getRequestId());
        } else {
            throw new InvalidRequestException("Request is either null or already processed.");
        }
    }
}