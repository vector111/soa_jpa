package pl.edu.agh.kis.soa.dao;

import pl.edu.agh.kis.soa.model.Something;
import pl.edu.agh.kis.soa.model.Subject;

import javax.ejb.Stateless;
import java.util.logging.Logger;

@Stateless
public class SubjectDao extends AbstractDao {

    private static final Logger logger = Logger.getLogger("AbstractDao");

    @Override
    protected Logger getLogger() {
        return logger;
    }

    @SuppressWarnings("unchecked")
    @Override
    protected Class<Subject> getType() {
        return Subject.class;
    }


}
