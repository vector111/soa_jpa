package pl.edu.agh.kis.soa.dao;

import pl.edu.agh.kis.soa.model.Student;

import javax.ejb.Stateless;
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


}
