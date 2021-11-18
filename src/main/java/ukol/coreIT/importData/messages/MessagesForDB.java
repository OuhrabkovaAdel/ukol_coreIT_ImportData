package ukol.coreIT.importData.messages;

import ukol.coreIT.importData.entity.Address;
import ukol.coreIT.importData.entity.Company;
import ukol.coreIT.importData.entity.Person;

public class MessagesForDB {

    public static void printMessagePersonAdded(Person person){
        System.out.println("Person added: " +
                "\n\t Name: " + person.getFirstName() +
                "\n\t Surname: " + person.getLastName() +
                "\n\t Company: " + person.getCompany().getCompanyName());
    }

    public static void printMessageCompanyAdded(Company company){
        System.out.println("Company added: " +
                "\n\t Company: " + company.getCompanyName() +
                "\n\t Ico: " + company.getIco());
    }

    public static void printMessagePersonUpdated(Person person){
        System.out.println("Person updated: " +
                "\n\t Name: " + person.getFirstName() +
                "\n\t Surname: " + person.getLastName() +
                "\n\t Company: " + person.getCompany().getCompanyName());
    }

    public static void printMessageCompanyUpdated(Company company){
        System.out.println("Company updated: " +
                "\n\t Company: " + company.getCompanyName() +
                "\n\t Ico: " + company.getIco());
    }

    public static void printMessageAddressUpdated(Address address){
        System.out.println("Address updated for company: " +
                "\n\t Company: " + address.getAddressee());
    }
}
