package pl.edu.agh.kis.soa.model;

import javax.persistence.*;

@Entity
@Table
public class Subject {

    @Id
    @GeneratedValue
    Integer subjectId;
    String subjectName;
    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="studentId")
    Student student;

    public String getSubjectName() {
        return subjectName;
    }

    public void setSubjectName(String subjectName) {
        this.subjectName = subjectName;
    }
}
