package ukol.coreIT.importData.processingClasses;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ukol.coreIT.importData.dbHandler.DBHandler;
import ukol.coreIT.importData.entity.Address;
import ukol.coreIT.importData.entity.Company;
import ukol.coreIT.importData.entity.Person;
import ukol.coreIT.importData.messages.MessagesForDB;

class DBProcessing {
    private static final Logger log = LoggerFactory.getLogger(DBProcessing.class);
    private DBHandler dbHandler = new DBHandler();

     DBProcessingResult dataProcessing(Company company, Person person, Address address){

        // Looking if company EXIST in DB -> RETURNING Address else NULL
        Company foundCompany = dbHandler.companyExist(company);

        // Looking if person EXIST in DB -> RETURNING Address else NULL
        Person foundPerson = dbHandler.personExist(person);

        if (foundPerson != null && foundCompany != null) {
            // Getting address for found company
            Address foundAddress = foundCompany.getAddress();
            if (foundPerson.personEquals(person) && foundCompany.companyEquals(foundCompany) && foundAddress.addressEquals(address)) {
                log.debug("In processData() duplicate record");
                return DBProcessingResult.DUPLICATE_RECORD;
            }
        }


        // IF Person NOT exist BY email
        // ELSE Person exist BY email
        if (foundPerson == null) {
            // IF Person NOT exist BY email AND Company NOT exist by ICO
            // ELSE Person NOT exist BY email AND Company exist by ICO
            if (foundCompany == null) {
                log.debug("In processData() Person NOT exist by Email, Company NOT exist by ICO in DB -> INSERT person, address, company");

                // SETTING
                company.setAddress(address);
                person.setCompany(company);

                // INSERT new person, new company, new address
                addingNew(company, address, person);
                return DBProcessingResult.NEW_RECORD;
            } else {
                log.debug("In processData() Person NOT exist by Email, Company exist by ICO in DB: " + foundCompany.getIco() );

                // Person NOT exist by Email, Company exist by ICO in DB
                // IF Companies are EQUAL looking for EQUALITY of addresses
                // ELSE Companies are NOT EQUAL -> INSERT person UPDATING COMPANY and ADDRESS for fount company
                if(company.companyEquals(foundCompany)){
                    // Getting address for found company
                    Address foundAddress = foundCompany.getAddress();
                    log.debug("In processData() Companies are EQUAL");

                    // if Address are EQUAL -> INSERT person
                    // else Address are NUT EQUAL -> INSERT person UPDATE address for found company
                    if(address.addressEquals(foundAddress)){
                        log.debug("In processData() Addresses are EQUAL");

                        // SETTING
                        person.setCompany(foundCompany);
                    } else {
                        log.debug("In processData() Addresses are NOT EQUAL -> updating Address");

                        // UPDATE new address for found company
                        upDate(foundAddress,address);

                        // SETTING
                        person.setCompany(foundCompany);
                    }
                } else {
                    log.debug("In processData() Companies are NOT EQUAL -> updating Address and Company");

                    //  UPDATING COMPANY and ADDRESS for found company
                    upDate(foundCompany, company, address);

                    // SETTING
                    person.setCompany(dbHandler.companyExist(company));
                }
                // INSERT new person
                addingNew(person);
                return DBProcessingResult.NEW_RECORD;
            }
        }  else {
            // Person exist by Email
            // IF Company NOT exist
            // ELSE Company EXIST
            if (foundCompany == null) {
                log.debug("In processData() Person exist by Email: " + foundPerson.getEmail()+ ", Company NOT found by ICO");
                // SETTING
                company.setAddress(address);

                // INSERT new company, new address
                addingNew(company, address);

                //SETTING
                person.setCompany(dbHandler.companyExist(company));

            } else {
                log.debug("In processData() Person exist by Email: " + foundPerson.getEmail()+ ", Company found by ICO:" + foundCompany.getIco());
                // IF Companies are EQUAL looking for EQUALITY of addresses
                // ELSE Companies are NOT EQUAL -> INSERT person UPDATING COMPANY and ADDRESS for fount company
                if(company.companyEquals(foundCompany)){
                    // Getting address for found company
                    Address foundAddress = foundCompany.getAddress();
                        log.debug("In processData() Companies are EQUAL");

                        // if Address are EQUAL -> INSERT person
                        // else Address are NUT EQUAL -> INSERT person UPDATE address for found company
                        if(address.addressEquals(foundAddress)){
                            log.debug("In processData() Addresses are EQUAL");
                        } else {
                            log.debug("In processData() Addresses are NOT EQUAL -> updating Address");

                            // UPDATE new address for found company
                            upDate(foundAddress,address);
                        }
                    } else {
                        log.debug("In processData() Companies are NOT EQUAL -> updating Address and Company");

                        //  UPDATING COMPANY and ADDRESS for found company
                        upDate(foundCompany, company, address);

                        // SETTING
                        person.setCompany(dbHandler.companyExist(company));
                        // UPDATE PERSON
                        upDate(foundPerson,person);
                    }
                }
            }
        return DBProcessingResult.UPDATE_RECORD;
        }


    // UPDATE address AND company
    private void upDate(Company foundCompany, Company company, Address address) {
        log.debug("In upDate UPDATE address AND company");
        if (dbHandler.updateCompanyAndAddress(foundCompany, company, address)) {
            MessagesForDB.printMessageCompanyUpdated(company);
        }
    }

    // UPDATE address
    private void upDate(Address foundAddress, Address address) {
        log.debug("In upDate UPDATE address");
        if(dbHandler.updateAddress(foundAddress,address)){
            MessagesForDB.printMessageAddressUpdated(address);
        }
    }

    // UPDATE person
    private void upDate(Person foundPerson, Person person) {
        log.debug("In upDate UPDATE person");
        if(dbHandler.updatePerson(foundPerson,person)){
            MessagesForDB.printMessagePersonUpdated(person);
        }
    }

    // INSERT new person, new company, new address
    private void addingNew(Company company, Address address, Person person) {
        log.debug("In addingNew() INSERT new person, new company, new address");
        if (dbHandler.save(company, address, person)) {
            MessagesForDB.printMessagePersonAdded(person);
            MessagesForDB.printMessageCompanyAdded(company);
        }
    }

    // INSERT new company, new address
    private void addingNew(Company company, Address address) {
        log.debug("In addingNew() INSERT new company, new address");
        if (dbHandler.save(company, address)) {
            MessagesForDB.printMessageCompanyAdded(company);
        }
    }

    // INSERT new person
    private void addingNew(Person person) {
        log.debug("In addingNew() INSERT new person");
        if(dbHandler.savePerson(person)){
            MessagesForDB.printMessagePersonAdded(person);
        }
    }

}
