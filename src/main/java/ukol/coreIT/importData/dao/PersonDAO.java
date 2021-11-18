package ukol.coreIT.importData.dao;
import org.hibernate.Session;
import ukol.coreIT.importData.entity.Person;

import java.util.List;

public interface PersonDAO {

    List<Person> findAll();

    Person findById(int theId);

    Person findByEmail(String email);

    void save(Person theEmployee);

    boolean saveInSer(Person person, Session currentSession);

    void update(Person person);

}
