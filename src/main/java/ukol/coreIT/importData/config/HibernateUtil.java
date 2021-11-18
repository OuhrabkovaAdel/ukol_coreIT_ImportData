package ukol.coreIT.importData.config;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ukol.coreIT.importData.entity.Address;
import ukol.coreIT.importData.entity.Company;
import ukol.coreIT.importData.entity.Person;

public class HibernateUtil {
    private static final Logger log = LoggerFactory.getLogger(HibernateUtil.class);
    private static SessionFactory sessionFactory = null;

    static {
        try {
            loadSessionFactory();
        } catch (Exception e) {
            log.error("Exception while initializing hibernate util.. ");
            e.printStackTrace();
        }
    }

    private static void loadSessionFactory() {
        log.debug("In: loadSessionFactory()");
        sessionFactory = new Configuration()
                .configure("hibernate.cfg.xml")
                .addAnnotatedClass(Address.class)
                .addAnnotatedClass(Company.class)
                .addAnnotatedClass(Person.class)
                .buildSessionFactory();
        log.debug("Out: loadSessionFactory() Session Factory created: " + sessionFactory);
    }

    public static Session getSession() throws HibernateException {
        log.debug("In: getSession()");
        Session currentSession = null;
        try {
            currentSession = sessionFactory.openSession();
        } catch (Throwable t) {
            log.error("In: getSession(): Exception while getting session");
            t.printStackTrace();
        }
        if (currentSession == null) {
            log.error("In: getSession(): session is discovered null");
        }
        log.debug("Out: getSession() Session: " + currentSession);
        return currentSession;
    }

    public static void shutdown() {
        log.debug("In: shutdown(): Closing session factory");
        sessionFactory.close();
        log.debug("Out: shutdown() Session factory closed");
    }
}

