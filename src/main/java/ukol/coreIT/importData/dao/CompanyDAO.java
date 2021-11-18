package ukol.coreIT.importData.dao;

import org.hibernate.Session;
import ukol.coreIT.importData.entity.Company;

import java.util.List;

public interface CompanyDAO {

    List<Company> findAll();

    Company findById(int theId);

    Company findByICO(int ico);

    void save(Company theCompany);

    boolean saveInSer(Company company, Session currentSession);

    void update(Company company);
}
