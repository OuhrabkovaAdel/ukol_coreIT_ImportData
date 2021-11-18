package ukol.coreIT.importData.dao;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ukol.coreIT.importData.config.HibernateUtil;
import ukol.coreIT.importData.entity.Address;
import java.util.List;



public class AddressDAOImpl implements AddressDAO{
    private static final Logger log = LoggerFactory.getLogger(AddressDAOImpl.class);

    @Override
    public List<Address> findAll() {
        log.debug("In findAll()");
        Session currentSession = null;
        List<Address> addresses = null;
        try {
            currentSession = HibernateUtil.getSession();

        /*
            Query<Address> theQuery =
                    currentSession.createQuery("from Address", Address.class);
             addresses = theQuery.getResultList();
        */

            addresses = currentSession.createQuery("from Address", Address.class)
                                      .getResultList();

            for (Address address : addresses) {
                log.debug("In findAll(): Getting from db: " + address.toString());
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
        return addresses;
    }



    @Override
    public Address findById(int theId) {
        log.debug("In findById()");
        Session currentSession = null;
        Address address = null;

        try {
            currentSession = HibernateUtil.getSession();
            address = currentSession.get(Address.class, theId);

            log.debug("Object findById: " + address.toString());
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            if(currentSession != null){
                log.debug("In findById() closing currentSession: " +  currentSession);
                currentSession.close();
                log.debug("In findById() closed currentSession: " +  currentSession);
            }
        }
        return address;
    }


    @Override
    public Address findExisting(Address currentAddress) {
        log.debug("In findExisting()");
        Session currentSession = null;
        Address address = null;
        try {
            currentSession = HibernateUtil.getSession();

            List<Address> list =
                    currentSession.createQuery("from Address adr where adr.addressee='" + currentAddress.getAddressee()+"' and " +
                            "adr.street='" + currentAddress.getStreet()+"' and " +
                            "adr.buildingNumber='" + currentAddress.getBuildingNumber()+"' and " +
                            "adr.postalCode='" + currentAddress.getPostalCode()+"' and " +
                            "adr.locality='" + currentAddress.getLocality()+"'", Address.class)
                    .getResultList();

            if (list.size() >= 1){
                address = list.get(0);
                log.debug("Object findExisting:" + address.toString());
            }

        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            if(currentSession != null){
                log.debug("In findExisting() closing currentSession: " +  currentSession);
                currentSession.close();
                log.debug("In findExisting() closed currentSession: " +  currentSession);
            }
        }
        return address;
    }

    @Override
    public void save(Address address) {
        log.debug("In save()");
        Session currentSession = null;
        Transaction transaction = null;

        try {
            currentSession = HibernateUtil.getSession();
            transaction = currentSession.beginTransaction();
            log.debug("In save() creating transaction: " +  transaction);

            currentSession.save(address);
            log.debug("In save() object saved : "  + address.toString());

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
    public boolean saveInSer(Address address, Session currentSession) {
        log.debug("In saveInSer()");

        try {
            currentSession.saveOrUpdate(address);
            log.debug("In saveInSer() object saved : "  + address.toString());

        } catch (Exception ex) {
            log.debug("In saveInSer() Exception: " +  ex.getMessage());
            ex.printStackTrace();
            return false;
        }
        return true;
    }

    @Override
    public void update(Address address) {
        log.debug("In update()");
        Session currentSession = null;
        Transaction transaction = null;

        try {
            currentSession = HibernateUtil.getSession();
            transaction = currentSession.beginTransaction();
            log.debug("In update() creating transaction: " + transaction);

            currentSession.update(address);
            log.debug("In update() object updated : " + address.toString());

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
