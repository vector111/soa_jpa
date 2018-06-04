package pl.edu.agh.kis.soa.model;

import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import javax.persistence.*;
import java.util.Arrays;
import java.util.List;
import java.util.Set;

@Entity(name = "Student")
@Table(name = "student")
public class Student {

	@Id
	@Column(name = "student_id")
	@GeneratedValue
	private Integer studentId;
	@Column(name = "first_name", nullable = false)
	private String firstName;
	@Column(name = "last_name", nullable = false)
	private String lastName;
	@ElementCollection(fetch = FetchType.EAGER)
	@CollectionTable(name = "subject", joinColumns = @JoinColumn(name = "student_id"))
	@Column(name = "subject_name")
	private List<String> subjects;
	@Lob
	@Column(name = "avatar")
	private byte[] avatar;
	@OneToMany(cascade = CascadeType.ALL)
	@LazyCollection(LazyCollectionOption.FALSE)
	private Set<Something> somethings;


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

	public Set<Something> getSomethings() {
		return somethings;
	}

	public void setSomethings(Set<Something> somethings) {
		this.somethings = somethings;
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
