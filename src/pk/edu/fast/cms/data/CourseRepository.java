package pk.edu.fast.cms.data;

import pk.edu.fast.cms.models.Course;

import java.util.ArrayList;
import java.util.List;
 // Handles the serialization and deserialization of Course objects to/from text files.
 //INTEGRATION: Admin and Instructor modules will use this to fetch course data.
public class CourseRepository {
    private static final String FILE_PATH = "data/courses.txt";
    private static final String DELIMITER = "\\|"; // Regex used for splitting strings
     // Saves a new Course object to the text file.
    public void saveCourse(Course course) {
        // Format: courseCode|title|creditHours
        String record = String.format("%s|%s|%d",
                course.getCourseCode(),
                course.getTitle(),
                course.getCreditHours());
        FileHandler.appendLine(FILE_PATH, record);
    }
     // Loads all courses from the text file into memory.
     // @return A list of Course objects.
    public List<Course> loadAllCourses() {
        List<String> lines = FileHandler.readLines(FILE_PATH);
        List<Course> courses = new ArrayList<>();
        for (String line : lines) {
            String[] parts = line.split(DELIMITER);
            // split by delimiter nand in 3 parts. CREDIT hours is in string so Integer.parseInt(parts[2]) done...
            // Ensure the line has exactly the 3 data points we expect
            if (parts.length >= 3) { // check by the condition...
                String code = parts[0];
                String title = parts[1];
                int creditHours = Integer.parseInt(parts[2]);
                courses.add(new Course(code, title, creditHours));
            }
        }
        return courses;
    }
}