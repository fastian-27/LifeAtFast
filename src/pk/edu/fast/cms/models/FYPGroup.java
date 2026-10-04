package pk.edu.fast.cms.models;

import pk.edu.fast.cms.users.PermanentInstructor;
import pk.edu.fast.cms.users.Student;

import java.util.ArrayList;
import java.util.List;

public class FYPGroup {

    private String groupId;
    private String title;
    private String description;
    private List<Student> members;
    private PermanentInstructor supervisor;
    private List<FYPMeeting> meetings;
    private List<FYPEvaluation> evaluations;
    public FYPGroup(String groupId, String title, String description) {
        this.groupId = groupId;
        this.title = title;
        this.description = description;
        this.members = new ArrayList<>();
        this.meetings = new ArrayList<>();
        this.evaluations = new ArrayList<>();
    }
    public void addMember(Student student) {
        members.add(student);
    }
    public void removeMember(Student student) {
        members.remove(student);
    }
    public List<Student> getMembers() { return members; }
    public void assignSupervisor(PermanentInstructor supervisor) {
        this.supervisor = supervisor;
    }
    public void addMeeting(FYPMeeting meeting) {
        meetings.add(meeting);
    }
    public void addEvaluation(FYPEvaluation evaluation) {
        evaluations.add(evaluation);
    }

    public String getDetails() {
        return "FYPGroup[" + groupId + " | " + title + " | members=" + members.size() + "]";
    }
    public String getGroupId()                    { return groupId; }
    public String getTitle()                      { return title; }
    public String getDescription()                { return description; }
    public PermanentInstructor getSupervisor()    { return supervisor; }
    public List<FYPMeeting> getMeetings()         { return meetings; }
    public List<FYPEvaluation> getEvaluations()   { return evaluations; }
}


/*
Previous work by Arham overpowered by Ahmad Shafee...
import java.util.ArrayList;
import java.util.List;

public class FYPGroup {
    private static List<FYPGroup> allGroups = new ArrayList<>();
    private String title;
    private List<Student> members = new ArrayList<>();
    private List<FYPMeeting> meetings = new ArrayList<>();
    private FYPEvaluation evaluation = new FYPEvaluation();

    public FYPGroup(String title) { this.title = title; allGroups.add(this); }
    public static List<FYPGroup> getAllGroups() { return allGroups; }
    public String getTitle() { return title; }
    public List<Student> getMembers() { return members; }
    public void addMember(Student s) { members.add(s); }
    public List<FYPMeeting> getMeetings() { return meetings; }
    public void addMeeting(FYPMeeting m) { meetings.add(m); }
    public FYPEvaluation getEvaluation() { return evaluation; }
 */