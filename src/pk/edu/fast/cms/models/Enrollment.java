package pk.edu.fast.cms.models;

import pk.edu.fast.cms.users.Student;

public class Enrollment {
        private Student student;
        private Section section;
        public Enrollment(Student student, Section section) { this.student = student; this.section = section; }
        public Student getStudent() { return student; }
        public Section getSection() { return section; }
    }
