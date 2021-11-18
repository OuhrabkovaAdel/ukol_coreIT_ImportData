package ukol.coreIT.importData.processingClasses;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ukol.coreIT.importData.entity.Address;
import ukol.coreIT.importData.entity.Company;
import ukol.coreIT.importData.entity.Person;

import java.util.List;
enum  DBProcessingResult {
    DUPLICATE_RECORD, NEW_RECORD,  UPDATE_RECORD
}


class ProcessDataFromFile {
    private static final Logger log = LoggerFactory.getLogger(ProcessDataFromFile.class);

    // Static method processing raw employees data
    static void processData(List<String[]> listOfData, String fileName){
        DBProcessing dbProcessing = new DBProcessing();
        long duplicateRecord = 0L;
        long updateRecord = 0L;
        long newRecord = 0L;

        for (String[] lineOfData: listOfData) {
            try {
                Address address = new Address(lineOfData[1], lineOfData[2], lineOfData[3], lineOfData[4], lineOfData[5], lineOfData[6]);
                Company company = new Company(lineOfData[0], lineOfData[1]);
                Person person = new Person(lineOfData[7], lineOfData[8], lineOfData[9]);

                DBProcessingResult result = dbProcessing.dataProcessing(company,person,address);

                switch (result){
                    case NEW_RECORD:
                        newRecord++;
                        break;
                    case UPDATE_RECORD:
                        updateRecord++;
                        break;
                    case DUPLICATE_RECORD:
                        duplicateRecord++;
                        break;
                }
            } catch (Exception e){
                log.error("In processData() processing record: " + e.getMessage());
            }
        }
        statistic(duplicateRecord, updateRecord, newRecord, fileName);
    }

    // Static method printing statistic after processing
    private static void statistic(long duplicateRecord, long updateRecord, long newRecord, String fileName) {
        log.debug("In statistic()");
        System.out.println("**************************** FINAL STATISTIC ****************************");
        System.out.println("From file: " + fileName);
        System.out.println("\t -> Added new record for employee: " + newRecord);
        System.out.println("\t -> Updated record for employee: " + updateRecord);
        System.out.println("\t -> Duplicate record: " + duplicateRecord);
        System.out.println("*************************************************************************");
    }
}