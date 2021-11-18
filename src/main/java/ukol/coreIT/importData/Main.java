package ukol.coreIT.importData;
import ukol.coreIT.importData.config.HibernateUtil;
import ukol.coreIT.importData.processingClasses.ProcessFiles;

public class Main {

	public static void main(String[] args) {
		ProcessFiles.processCSVFiles("Import", "Finished");
		HibernateUtil.shutdown();
	}
}
