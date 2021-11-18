package ukol.coreIT.importData.dao;

import org.hibernate.Session;
import ukol.coreIT.importData.entity.Address;

import java.util.List;

public interface AddressDAO {

    List<Address> findAll();

    Address findById(int theId);

    Address findExisting(Address address);

    void save(Address theAddress);

    boolean saveInSer(Address address, Session currentSession);

    void update(Address address);
}
