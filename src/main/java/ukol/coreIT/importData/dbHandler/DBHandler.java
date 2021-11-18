package ukol.coreIT.importData.dbHandler;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ukol.coreIT.importData.config.HibernateUtil;
import ukol.coreIT.importData.dao.*;
import ukol.coreIT.importData.entity.Address;
import ukol.coreIT.importData.entity.Company;
import ukol.coreIT.importData.entity.Person;

public class DBHandler {
    private static final Logger log = LoggerFactory.getLogger(DBHandler.class);
    private AddressDAO addressDAO = new AddressDAOImpl();
    private CompanyDAO companyDAO = new CompanyDAOImpl();
    private PersonDAO personDAO = new PersonDAOImpl();

    public Person personExist(Person person){
        return personDAO.findByEmail(person.getEmail()) ;
    }

    public Company companyExist(Company company){
        return companyDAO.findByICO(company.getIco());
    }

    public boolean save(Company company, Address address, Person person){
        Session currentSession = null;
        Transaction transaction = null;
        try {
            currentSession = HibernateUtil.getSession();
            transaction = currentSession.beginTransaction();
            log.debug("In save() creating transaction: " +  transaction);

            company.setAddress(address);
            person.setCompany(company);
            if (addressDAO.saveInSer(address,currentSession) &&
                   companyDAO.saveInSer(company,currentSession) &&
                    personDAO.saveInSer(person,currentSession)) {
                log.debug("In save() committing: " + transaction);
                transaction.commit();
                log.debug("In save() committed: " + transaction);
            } else {
                transaction.rollback();
                log.debug("In save() Transaction rollback");
            }
        } catch (Exception ex) {
            log.debug("In save() Exception: " +  ex.getMessage());
            ex.printStackTrace();
            if (transaction != null) {
                transaction.rollback();
                log.debug("In save() Transaction rollback");
            }
            log.debug("In save() Transaction rollback");
            return false;
        } finally {
            if(currentSession != null){
                log.debug("In save() closing currentSession: " +  currentSession);
                currentSession.close();
                log.debug("In save() closed currentSession: " +  currentSession);
            }
        }
    return true;
    }

    public boolean save(Company company, Address address){
        Session currentSession = null;
        Transaction transaction = null;
        try {
            currentSession = HibernateUtil.getSession();
            transaction = currentSession.beginTransaction();
            log.debug("In save() creating transaction: " +  transaction);

            company.setAddress(address);

            if (addressDAO.saveInSer(address,currentSession) &&
                    companyDAO.saveInSer(company,currentSession)) {
                log.debug("In save() committing: " + transaction);
                transaction.commit();
                log.debug("In save() committed: " + transaction);
            } else {
                transaction.rollback();
                log.debug("In save() Transaction rollback");
            }
        } catch (Exception ex) {
            log.debug("In save() Exception: " +  ex.getMessage());
            ex.printStackTrace();
            if (transaction != null) {
                transaction.rollback();
                log.debug("In save() Transaction rollback");
            }
            return false;
        } finally {
            if(currentSession != null){
                log.debug("In save() closing currentSession: " +  currentSession);
                currentSession.close();
                log.debug("In save() closed currentSession: " +  currentSession);
            }
        }
        return true;
    }

    public boolean savePerson(Person person){
        try {
            personDAO.save(person);
        } catch (Exception ex) {
            log.debug("In savePerson() Exception: " +  ex.getMessage());
            ex.printStackTrace();
            return false;
        }
        return true;
    }


    public boolean updateAddress(Address oldAddress, Address newAddress){
        try {
            newAddress.setId(oldAddress.getId());
            addressDAO.update(newAddress);
        } catch (Exception ex) {
            log.debug("In updateAddress() Exception: " +  ex.getMessage());
            ex.printStackTrace();
            return false;
        }
        return true;
    }

    public boolean updatePerson(Person oldPerson, Person newPerson){
        try {
            newPerson.setId(oldPerson.getId());
            personDAO.update(newPerson);
        } catch (Exception ex) {
            log.debug("In updateAddress() Exception: " +  ex.getMessage());
            ex.printStackTrace();
            return false;
        }
        return true;
    }

    public boolean updateCompanyAndAddress(Company oldCompany, Company newCompany, Address address){
        try {
            address.setId(oldCompany.getAddress().getId());
            newCompany.setId(oldCompany.getId());
            newCompany.setAddress(address);

            addressDAO.update(address);
            companyDAO.update(newCompany);

        } catch (Exception ex) {
            log.debug("In updateCompany() Exception");
            ex.printStackTrace();
            return false;
        }
        return true;
    }
}
