package pl.edu.agh.kis.soa.dao;

import pl.edu.agh.kis.soa.model.Student;
import pl.edu.agh.kis.soa.model.Subject;

import javax.ejb.Stateless;
import java.util.List;
import java.util.logging.Logger;

@Stateless
public class StudentDao extends AbstractDao {

    private static final Logger logger = Logger.getLogger("AbstractDao");

    @Override
    protected Logger getLogger() {
        return logger;
    }

    @SuppressWarnings("unchecked")
    @Override
    protected Class<Student> getType() {
        return Student.class;
    }

    public List<Student> getStudentsByName(String studentName){
        return entityManager
                .createQuery("SELECT a FROM Student a WHERE a.firstName LIKE :studentName", Student.class)
                .setParameter("studentName", studentName)
                .getResultList();
    }

    public List<Student> getSubjectByName(String subjectName){
        return entityManager
                .createQuery("SELECT a FROM Student a JOIN a.subjects s WHERE s.subjectName like :subjectName ", Student.class)
                .setParameter("subjectName", subjectName)
                .getResultList();
    }

    public List<Student> getStudentsByFilter(String studentName, String subjectName){
        return entityManager
                .createQuery("SELECT a FROM Student a JOIN a.subjects s WHERE s.subjectName like :subjectName " +
                        "and a.firstName like :studentName", Student.class)
                .setParameter("subjectName", subjectName)
                .setParameter("studentName", studentName)
                .getResultList();
    }


}
