package pl.edu.agh.kis.soa.model;

import javax.persistence.*;
import java.util.Arrays;
import java.util.List;

@Entity(name = "Student")
@Table(name = "student")
public class Student {

	@Id
	@GeneratedValue
	private Integer studentId;
	private String firstName;
	private String lastName;
	@ElementCollection(fetch = FetchType.EAGER)
	@CollectionTable(name = "subject", joinColumns = @JoinColumn(name = "studentId"))
	@Column(name = "subjectA")
	private List<String> subjects;
	@Lob
	private byte[] avatar;

	public Student(){}

	public Student(Integer studentId){
		this.studentId=studentId;
	}

	public Integer getStudentId() {
		return studentId;
	}

//	public void setStudentId(String studentId) {
//		this.studentId = studentId;
//	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public List<String> getSubjects() {
		return subjects;
	}

	public void setSubjects(List<String> subjects) {
		this.subjects = subjects;
	}

	public byte[] getAvatar() {
		return avatar;
	}

	public void setAvatar(byte[] avatar) {
		this.avatar = avatar;
	}

	@Override
	public String toString() {
		return "Student{" +
				"firstName='" + firstName + '\'' +
				", lastName='" + lastName + '\'' +
				", subjects=" + subjects +
				", avatar=" + Arrays.toString(avatar) +
				'}';
	}

}
