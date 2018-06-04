import org.codehaus.jackson.map.ObjectMapper;
import org.jboss.resteasy.client.jaxrs.ResteasyClient;
import org.jboss.resteasy.client.jaxrs.ResteasyClientBuilder;
import org.jboss.resteasy.client.jaxrs.ResteasyWebTarget;
import org.jboss.resteasy.client.jaxrs.*;
import pl.edu.agh.kis.soa.model.Subject;


import javax.ws.rs.client.Entity;
import javax.ws.rs.client.Invocation;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


public class RestClient {

    public static void main(String args[]){

        System.out.println("get student");
        ResteasyClient client = new ResteasyClientBuilder().build();
        ResteasyWebTarget target = client.target("http://localhost:8080/lab2-web/rest/getStudent/2");
        Response response = target.register(new BasicAuthentication("user", "user")).request().get();
        String student1 = response.readEntity(String.class);
        response.close();
        System.out.println(student1+"\n");

        Subject subject = new Subject(1,"SOA");
        List<Subject> subjects = new ArrayList<>();
        subjects.add(subject);

        System.out.println("Adding subject");
        target = client.target("http://localhost:8080/lab2-web/rest/addSubject");
        response = target.register(new BasicAuthentication("user", "user")).request().put(Entity.entity("{\"subjectName\":\"SOA\"}", "application/json"));
        System.out.println(response.getStatus()+" "+response.getStatusInfo());
        response.close();



        Student student = StudentBuilder.aStudent()
                .withAvatar("C:\\Users\\Admin\\java_workspace\\soa\\abc\\avatar.png")
                .withFirstName("Baltazar")
                .withLastName("Gąbka")
                .withSubjects(subjects)
                .build();

        ObjectMapper mapper = new ObjectMapper();
        try {
            mapper.writeValue(new File("C:\\Users\\Admin\\java_workspace\\soa\\abc\\file.json"), student);
        } catch (IOException e) {
            e.printStackTrace();
        }


        String jsonInString = null;
        try {
            jsonInString = mapper.writeValueAsString(student);
        } catch (IOException e) {
            e.printStackTrace();
        }

        System.out.println("Adding student");
        target = client.target("http://localhost:8080/lab2-web/rest/addStudent");
        response = target.register(new BasicAuthentication("user", "user")).request().put(Entity.entity(jsonInString, "application/json"));
        System.out.println(response.getStatus()+" "+response.getStatusInfo());
        response.close();

        System.out.println("\nget student's avatar");
        target = client.target("http://localhost:8080/lab2-web/rest/getAvatar/2");
        response = target.register(new BasicAuthentication("user", "user")).request().get();
        //byte[] avatar = response.readEntity(byte[].class);
        String avatar2 = response.readEntity(String.class);
        response.close();
        //System.out.println(avatar+"\n");
        System.out.println(avatar2+"\n");


        System.out.println("\nget students");
        target = client.target("http://localhost:8080/lab2-web/rest/getStudents");
        response = target.register(new BasicAuthentication("user", "user")).request().get();
        String students = response.readEntity(String.class);
        response.close();
        System.out.println(students);


        System.out.println("\nstudent removed");
        target = client.target("http://localhost:8080/lab2-web/rest/deleteStudent/2");
        response = target.register(new BasicAuthentication("user", "user")).request().delete();
        response.close();

        System.out.println("\nget students");
        target = client.target("http://localhost:8080/lab2-web/rest/getStudents");
        response = target.register(new BasicAuthentication("user", "user")).request().get();
        students = response.readEntity(String.class);
        response.close();
        System.out.println(students);

    /*    System.out.println("\nStudent 2 updated");
        target = client.target("http://localhost:8080/lab2-web/rest/updateStudent/2");
        response = target.register(new BasicAuthentication("user", "user")).request().put(Entity.entity(jsonInString, "application/json"));
        System.out.println(response.getStatus()+" "+response.getStatusInfo());
        response.close();
*/
        /*target = client.target("http://localhost:8080/lab2-web/rest/getStudent/2");
        response = target.register(new BasicAuthentication("user", "user")).request().get();
        student1 = response.readEntity(String.class);
        response.close();
        System.out.println(student1+"\n");*/

    }

}
