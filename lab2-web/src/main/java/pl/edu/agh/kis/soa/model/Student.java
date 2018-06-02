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

	@OneToMany(mappedBy="student")
	private List<Subject> subjects;
	@Transient
	private byte[] avatar;

	public Student(){}

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

	public List<Subject> getSubjects() {
		return subjects;
	}

	public void setSubjects(List<Subject> subjects) {
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
