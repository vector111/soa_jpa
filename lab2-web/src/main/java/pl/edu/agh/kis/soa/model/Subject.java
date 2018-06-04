package pl.edu.agh.kis.soa.model;

import org.codehaus.jackson.annotate.JsonIgnore;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import javax.persistence.*;
import java.util.List;

@Entity(name = "Subject")
@Table(name = "subject")
public class Subject {

    @Id
    @Column(name = "subject_id")
    @GeneratedValue
    Integer subjectId;
    String subjectName;
    @ManyToMany(mappedBy = "subjects")
    @LazyCollection(LazyCollectionOption.FALSE)
    @JsonIgnore
    private List<Student> students;

    public Subject(Integer id, String subjectName){
        this.subjectId = id;
        this.subjectName = subjectName;
    }

    public Subject() {
    }

    public Integer getSubjectId() {
        return subjectId;
    }

    public String getSubjectName() {
        return subjectName;
    }

    public void setSubjectName(String subjectName) {
        this.subjectName = subjectName;
    }

    public List<Student> getStudents() {
        return students;
    }

    public void setStudents(List<Student> students) {
        this.students = students;
    }
}
