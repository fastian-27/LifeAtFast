package pk.edu.fast.cms.users;

/**
 * Abstract base class representing any human actor in the system.
 * INTEGRATION: Teammates will extend this class for Student and Instructor models.
 */
public abstract class Person {

    // Protected so subclasses (Student, Instructor, Admin) can access them directly
    protected String id; // Required for system identification and file I/O tracking
    protected String name;
    protected String email;
    protected String phone;

    public Person(String id, String name, String email, String phone) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.phone = phone;
    }

    /**
     * Abstract method to be implemented by subclasses.
     * @return The specific role of the user (e.g., "Normal Student", "Academic Office Admin").
     */
    public abstract String getRole();

    /**
     * Abstract method to get a formatted string of user details.
     * @return Formatted details.
     */
    public abstract String getDetails();

    // --- Getters and Setters ---
    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getPhone() { return phone; }
    public void setPhone(String phone) { this.phone = phone; }
}