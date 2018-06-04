package pl.edu.agh.kis.soa;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;
import java.util.stream.Collectors;

import javax.annotation.security.RolesAllowed;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.Response;

import org.apache.commons.io.IOUtils;
import org.jboss.resteasy.plugins.providers.multipart.InputPart;
import org.jboss.resteasy.plugins.providers.multipart.MultipartFormDataInput;
import pl.edu.agh.kis.soa.dao.SomethingDao;
import pl.edu.agh.kis.soa.dao.StudentDao;
import pl.edu.agh.kis.soa.model.Something;
import pl.edu.agh.kis.soa.model.Student;
import pl.edu.agh.kis.soa.model.StudentBuilder;
import pl.edu.agh.kis.soa.model.Subject;

/**
 * Klasa wystawiająca interfejs REST.
 * @author teacher
 *
 */

@Path("rest")
@Stateless
public class StudentResource {

	@PersistenceContext(unitName = "NewPersistenceUnit")
	EntityManager em;

	@Inject
	StudentDao studentDao;

	@Inject
	SomethingDao somethingDao;

	private static final Logger logger = Logger.getLogger("StudentResource");
	private List<Student> students = new ArrayList<>();

	private final String UPLOADED_FILE_PATH = "C:\\Users\\Admin\\Documents\\Studia\\6 semestr\\soa\\[soa2018]Maciej Mika-RESTzaliczenie\\";

	public StudentResource(){}

	@RolesAllowed("other")
	@GET
	@Path("getStudent/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public Student getStudent(@PathParam("id") String studentId) {
		Student student = studentDao.get(Integer.valueOf(studentId));
		if(student == null)
			throw new WebApplicationException(Response.status(Response.Status.NOT_FOUND).entity("Student doesn't exsist!").build());
		return student;
	}

	@RolesAllowed("other")
	@GET
	@Path("getStudents")
	@Produces(MediaType.APPLICATION_JSON)
	public List<Student> getStudents() {
		List<Student> students = studentDao.list(0,100);
		if(students.isEmpty())
			throw new WebApplicationException(Response.status(Response.Status.NOT_FOUND).entity("Students not found!").build());
		else
			return students;
	}

	@RolesAllowed("other")
	@GET
	@Path("getAvatar/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public byte[] getAvatarById(@PathParam("id") String id) {
		Student student = studentDao.get(Integer.valueOf(id));
		if(student == null)
			throw new WebApplicationException(Response.status(Response.Status.NOT_FOUND).entity("Student not found!").build());
		if(student.getAvatar() == null)
			throw new WebApplicationException(Response.status(Response.Status.NOT_FOUND).entity("Avatar not found!").build());
		else
			return student.getAvatar();
	}

	@RolesAllowed("other")
	@PUT
	@Path("addStudent")
	@Consumes(MediaType.APPLICATION_JSON)
	public Response addStudent(Student student){
		if(!(student.getStudentId() == null)) throw new WebApplicationException(Response.status(Response.Status.BAD_REQUEST).entity("Student ID is created automatically, remove studentId from JSON!").build());
		if(student.getFirstName() == null || student.getLastName() == null) throw new WebApplicationException(Response.status(Response.Status.BAD_REQUEST).entity("Student must have first name and last name!").build());
		studentDao.create(student);
		return Response.status(Response.Status.CREATED).entity("Student added").build();
	}

	@RolesAllowed("other")
	@DELETE
	@Path("deleteStudent/{id}")
	public Response deleteStudent(@PathParam("id") String id){
		Student student = studentDao.get(Integer.valueOf(id));
		if(student == null)
			throw new WebApplicationException(Response.status(Response.Status.NOT_FOUND).entity("Student not found!").build());
		else
			studentDao.delete(Integer.valueOf(id));
		return Response.status(Response.Status.OK).entity("Student removed").build();
	}

	@RolesAllowed("other")
	@PUT
	@Path("updateStudent/{id}")
	@Consumes(MediaType.APPLICATION_JSON)
	public Response updateStudent(@PathParam("id") String id, Student student){
		if(studentDao.get(Integer.valueOf(id)) == null)return Response.status(Response.Status.NOT_FOUND).entity("Student doesn't exsist!").build();
		if(student.getFirstName() == null || student.getLastName() == null) throw new WebApplicationException(Response.status(Response.Status.BAD_REQUEST).entity("Student must have first name and last name!").build());
		studentDao.update(student);
		return Response.status(Response.Status.OK).entity("Student updated").build();
	}

	@RolesAllowed("other")
	@POST
	@Path("addAvatarForStudent/{id}")
	@Consumes(MediaType.MULTIPART_FORM_DATA)
	public Response uploadAvatar(@PathParam("id") String studentId, MultipartFormDataInput input){

		Student s = studentDao.get(Integer.valueOf(studentId));
		if(s == null)
			throw new WebApplicationException(Response.status(Response.Status.NOT_FOUND).entity("Student not found!").build());

		String fileName = "";

		Map<String, List<InputPart>> uploadForm = input.getFormDataMap();
		List<InputPart> inputParts = uploadForm.get("uploadedFile");

		for (InputPart inputPart : inputParts) {

			try {

				MultivaluedMap<String, String> header = inputPart.getHeaders();
				fileName = getFileName(header);

				//convert the uploaded file to inputstream
				InputStream inputStream = inputPart.getBody(InputStream.class,null);

				byte [] bytes = IOUtils.toByteArray(inputStream);

				Student student = studentDao.get(Integer.valueOf(studentId));
				student.setAvatar(bytes);
				studentDao.update(student);

				//constructs upload file path
				fileName = UPLOADED_FILE_PATH + fileName;

				writeFile(bytes,fileName);

				System.out.println("Done");

			} catch (IOException e) {
				e.printStackTrace();
			}

		}

		return Response.status(200)
				.entity("uploadFile is called, Uploaded file name : " + fileName).build();
	}

	private String getFileName(MultivaluedMap<String, String> header) {

		String[] contentDisposition = header.getFirst("Content-Disposition").split(";");

		for (String filename : contentDisposition) {
			if ((filename.trim().startsWith("filename"))) {

				String[] name = filename.split("=");

				String finalFileName = name[1].trim().replaceAll("\"", "");
				return finalFileName;
			}
		}
		return "unknown";
	}

	private void writeFile(byte[] content, String filename) throws IOException {

		File file = new File(filename);

		if (!file.exists()) {
			file.createNewFile();
		}

		FileOutputStream fop = new FileOutputStream(file);

		fop.write(content);
		fop.flush();
		fop.close();

	}


	/*
	SOMETHING____________________________________________
	 */

	@RolesAllowed("other")
	@PUT
	@Path("addSomething")
	@Consumes(MediaType.APPLICATION_JSON)
	public Response addSomething(Something something){
		if(!(something.getSthId() == null)) throw new WebApplicationException(Response.status(Response.Status.BAD_REQUEST).entity("STH ID is created automatically, remove studentId from JSON!").build());
		somethingDao.create(something);
		return Response.status(Response.Status.CREATED).entity("Something added").build();
	}

	/*
	SUBJECT____________________________________________
	 */

	@RolesAllowed("other")
	@PUT
	@Path("addSubject")
	@Consumes(MediaType.APPLICATION_JSON)
	public Response addSubject(Subject subject){
		if(!(subject.getSubjectId() == null)) throw new WebApplicationException(Response.status(Response.Status.BAD_REQUEST).entity("STH ID is created automatically, remove studentId from JSON!").build());
		somethingDao.create(subject);
		return Response.status(Response.Status.CREATED).entity("Subject added").build();
	}
}
