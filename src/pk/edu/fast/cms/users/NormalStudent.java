package pk.edu.fast.cms.users;

public class NormalStudent extends Student {
    public NormalStudent(String name, String email, String phone, String studentId) {
        super(name, email, phone, studentId);
    }

    @Override public String getRole() { return "Normal Student"; }

    @Override
    public String getDetails() {
        return "";
    }
}
