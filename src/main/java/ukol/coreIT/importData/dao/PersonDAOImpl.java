package ukol.coreIT.importData.dao;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ukol.coreIT.importData.config.HibernateUtil;
import ukol.coreIT.importData.entity.Person;

import java.util.List;


public class PersonDAOImpl implements PersonDAO {
    private static final Logger log = LoggerFactory.getLogger(PersonDAOImpl.class);

    @Override
    public List<Person> findAll() {
        log.debug("In findAll()");
        Session currentSession = null;
        List<Person> people = null;
        try {
            currentSession = HibernateUtil.getSession();

            people = currentSession.createQuery("from Person", Person.class)
                    .getResultList();

            for (Person person : people) {
                log.debug("Getting from db: " + person.toString());
            }
        } catch (NullPointerException npe) {
            log.info("In findAll() not found");
        } catch (Exception ex) {
            log.error("In findAll() Exception: " + ex.getMessage());
            ex.printStackTrace();
        } finally {
            if (currentSession != null) {
                log.debug("In findAll() closing currentSession: " + currentSession);
                currentSession.close();
                log.debug("In findAll() closed currentSession: " + currentSession);
            }
        }
        return people;
    }



    @Override
    public Person findById(int theId) {
        log.debug("In findById()");
        Session currentSession = null;
        Person person = null;

        try {
            currentSession = HibernateUtil.getSession();
            person = currentSession.get(Person.class, theId);

            log.debug("Object findById: " + person.toString());
        } catch (NullPointerException npe) {
            log.debug("In findById() Id not found");
        } catch (Exception ex) {
            log.error("In findById() Exception: " +  ex.getMessage());
            ex.printStackTrace();
        } finally {
            if(currentSession != null){
                log.debug("In findById() closing currentSession: " +  currentSession);
                currentSession.close();
                log.debug("In findById() closed currentSession: " +  currentSession);
            }
        }
        return person;
    }


    @Override
    public Person findByEmail(String email) {
        log.debug("In findByEmail()");
        Session currentSession = null;
        Person person = null;

        try {
            currentSession = HibernateUtil.getSession();

            List<Person> list =
                    currentSession.createQuery("from Person person where person.email='" + email + "'", Person.class)
                            .getResultList();
            if (list.size() >= 1) {
                person = list.get(0);
                log.debug("Object findByEmail id:" + person.getId());
            }
        } catch (NullPointerException npe) {
            log.debug("In findByEmail() not found");
        } catch (Exception ex) {
            log.error("In findByEmail() Exception: " +  ex.getMessage());
            ex.printStackTrace();
        } finally {
            if(currentSession != null){
                log.debug("In findByEmail() closing currentSession: " +  currentSession);
                currentSession.close();
                log.debug("In findByEmail() closed currentSession: " +  currentSession);
            }
        }
        return person;
    }

    @Override
    public void save(Person person) {
        log.debug("In save()");
         Session currentSession = null;
        Transaction transaction = null;

        try {
            currentSession = HibernateUtil.getSession();
            transaction = currentSession.beginTransaction();
            log.debug("In save() creating transaction: " +  transaction);

            currentSession.saveOrUpdate(person);
            log.debug("In save() object saved : "  + person.toString());

            log.debug("In save() committing: "  + transaction);
            transaction.commit();
            log.debug("In save() committed: "  + transaction);

        } catch (Exception ex) {
            log.debug("In save() Exception: " +  ex.getMessage());
            ex.printStackTrace();
            if(transaction != null) {
                transaction.rollback();
                log.debug("In save() Transaction rollback");
            }
        } finally {
            if(currentSession != null){
                log.debug("In save() closing currentSession: " +  currentSession);
                currentSession.close();
                log.debug("In save() closed currentSession: " +  currentSession);
            }
        }
    }

    @Override
    public boolean saveInSer(Person person, Session currentSession) {
        log.debug("In saveInSer()");

        try {
            currentSession.save(person);
            log.debug("In saveInSer() object saved : "  + person.toString());
        } catch (Exception ex) {
            log.debug("In saveInSer() Exception: " +  ex.getMessage());
            ex.printStackTrace();
            return false;
        }
        return true;
    }


    @Override
    public void update(Person person) {
        log.debug("In update()");
        Session currentSession = null;
        Transaction transaction = null;

        try {
            currentSession = HibernateUtil.getSession();
            transaction = currentSession.beginTransaction();
            log.debug("In update() creating transaction: " + transaction);

            currentSession.update(person);
            log.debug("In update() object updated : " + person.toString());

            log.debug("In update() committing: " + transaction);
            transaction.commit();
            log.debug("In update() committed: " + transaction);

        } catch (Exception ex) {
            log.debug("In update() Exception: " + ex.getMessage());
            ex.printStackTrace();
            if (transaction != null) {
                transaction.rollback();
                log.debug("In update() Transaction rollback");
            }
        } finally {
            if(currentSession != null){
                log.debug("In update() closing currentSession: " +  currentSession);
                currentSession.close();
                log.debug("In update() closed currentSession: " +  currentSession);
            }
        }
    }
}
