package ukol.coreIT.importData.dao;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ukol.coreIT.importData.config.HibernateUtil;
import ukol.coreIT.importData.entity.Company;

import java.util.List;

public class CompanyDAOImpl implements CompanyDAO {
        private static final Logger log = LoggerFactory.getLogger(PersonDAOImpl.class);

        @Override
        public List<Company> findAll() {
            log.debug("In findAll()");
            Session currentSession = null;
            List<Company> companies = null;
            try {
                currentSession = HibernateUtil.getSession();

                companies = currentSession.createQuery("from Company", Company.class)
                        .getResultList();

                for (Company company : companies) {
                    log.debug("In findAll() Getting from db: " + company.toString());
                }
            } catch (Exception ex) {
                ex.printStackTrace();
            } finally {
                if(currentSession != null){
                    log.debug("In findAll() closing currentSession: " +  currentSession);
                    currentSession.close();
                    log.debug("In findAll() closed currentSession: " +  currentSession);
                }
            }
            return companies;
        }



        @Override
        public Company findById(int theId) {
            log.debug("In findById()");
            Session currentSession = null;
            Company company = null;

            try {
                currentSession = HibernateUtil.getSession();
                company = currentSession.get(Company.class, theId);

                log.debug("Object findById: " + company.toString());
            } catch (Exception ex) {
                ex.printStackTrace();
            } finally {
                if(currentSession != null){
                    log.debug("In findById() closing currentSession: " +  currentSession);
                    currentSession.close();
                    log.debug("In findById() closed currentSession: " +  currentSession);
                }
            }
            return company;
        }


        @Override
        public Company findByICO(int ico) {
            log.debug("In findByICO()");
            Session currentSession = null;
            Company company = null;

            try {
                currentSession = HibernateUtil.getSession();

                List<Company> list =
                        currentSession.createQuery("from Company comp where comp.ico='" + ico + "'", Company.class)
                                .getResultList();
                if (list.size() >= 1) {
                    company = list.get(0);
                    log.debug("Object findByICO id:" + company.getId());
                }
            } catch (NullPointerException npe) {
                log.debug("In findByICO() not found");
            } catch (Exception ex) {
                ex.printStackTrace();
            } finally {
                if(currentSession != null){
                    log.debug("In findByICO() closing currentSession: " +  currentSession);
                    currentSession.close();
                    log.debug("In findByICO() closed currentSession: " +  currentSession);
                }
            }
            return company;
        }

        @Override
        public void save(Company company) {
            log.debug("In save()");
            Session currentSession = null;
            Transaction transaction = null;

            try {
                currentSession = HibernateUtil.getSession();
                transaction = currentSession.beginTransaction();
                log.debug("In save() creating transaction: " +  transaction);

                currentSession.saveOrUpdate(company);
                log.debug("In save() object saved : "  + company.toString());

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
    public boolean saveInSer(Company company, Session currentSession) {
        log.debug("In saveInSer()");

        try {
            currentSession.saveOrUpdate(company);
            log.debug("In saveInSer() object updated : "  + company.toString());

        } catch (Exception ex) {
            log.debug("In saveInSer() Exception: " +  ex.getMessage());
            ex.printStackTrace();
            return false;
        }
        return true;
    }

    @Override
    public void update(Company company) {
        log.debug("In update()");
        Session currentSession = null;
        Transaction transaction = null;

        try {
            currentSession = HibernateUtil.getSession();
            transaction = currentSession.beginTransaction();
            log.debug("In update() creating transaction: " + transaction);

            currentSession.update(company);
            log.debug("In update() object updated : " + company.toString());

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
