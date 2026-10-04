package pk.edu.fast.cms.users;
// STUB FOR PERSON C
public class TeachingAssistant extends Student {
    public TeachingAssistant(String id, String name, String email, String phone) { super(id, name, email, phone); }
    @Override public String getRole() { return "Teaching Assistant"; }
    @Override public String getDetails() { return name; }
}