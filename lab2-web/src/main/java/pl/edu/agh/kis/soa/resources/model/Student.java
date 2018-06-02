package pl.edu.agh.kis.soa.resources.model;

import javax.persistence.*;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import java.util.Arrays;
import java.util.List;

@Entity(name = "StudentWEB")
@Table(name = "student_web")
public class Student {

	@Id
	@GeneratedValue
	private String studentId;
	private String firstName;
	private String lastName;
	private String albumNo;
	@Transient
	private List<String> subjects;
	@Transient
	private byte[] avatar;

	public Student(){}

	public String getStudentId() {
		return studentId;
	}

	public void setStudentId(String studentId) {
		this.studentId = studentId;
	}

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
