//package org.example.models;
package pk.edu.fast.cms.users;
import pk.edu.fast.cms.users.Instructor;
public class VisitingInstructor extends Instructor {
    public VisitingInstructor(String id, String name, String email,
                              String phoneNumber, String teacherId) {
        super(id, name, email, phoneNumber, teacherId);
    }
    @Override
    public String getRole() { return "VisitingInstructor"; }

    @Override
    public String getDetails() {
        return ""; // get the relevant details....
    }
}
