package ukol.coreIT.importData.processingClasses;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class ProcessFiles {
    private static final Logger log = LoggerFactory.getLogger(ProcessFiles.class);
    private static final String  splitCsvBy = ",";

    // Static method processing all csv files of employees in spec directory
    public static void processCSVFiles(String importFilePath, String exportFilePath){
        try {
            Collection<Path> paths =  ProcessFiles.findCSVFiles(importFilePath);
            assert paths != null;
            for (Path path: paths) {
                String destPath = exportFilePath + System.getProperty("file.separator") + path.getFileName();
                processFileEmployees(path.toString(),destPath);
            }
        } catch (Exception e){
            log.error("In processCSVFiles() Exception: " + e.getMessage());
            e.printStackTrace();
        }
    }

    // Static method processing one csv file of employees and moving it after processing
    private static void processFileEmployees(String importPathFile, String destPath) {
        Path result = null;
        try {
            List<String[]> listOfData = readCSV(importPathFile);
            ProcessDataFromFile.processData(listOfData, importPathFile);
            result = Files.move(Paths.get(importPathFile), Paths.get(destPath));
        } catch (IOException e) {
            log.error("In processFileEmployees() IOException while moving file: " + e.getMessage());
            e.printStackTrace();
        } catch (Exception e) {
            log.error("In processFileEmployees() Exception while moving file: " + e.getMessage());
            e.printStackTrace();
        }
        if (result != null) {
            log.debug("In processFileEmployees() File moved successfully.");
        } else {
            log.debug("In processFileEmployees() File movement failed.");
        }
    }

    // Static method for reading CSV
    private static List<String[]> readCSV(String path){
        log.debug("In readCSV()");
        String line = "";
        List<String[]>  dataFromFile = new LinkedList<>();

        try (BufferedReader br = new BufferedReader(new FileReader(path))) {
            while ((line = br.readLine()) != null) {
                String[] oneLine;
                oneLine = line.split(splitCsvBy);
                dataFromFile.add(oneLine);
            }
        } catch (IOException ie) {
            log.error("In readCSV() IOException: " + ie.getMessage());
            ie.printStackTrace();
        } catch (Exception e) {
            log.error("In readCSV() Exception: " + e.getMessage());
            e.printStackTrace();
        }
        log.debug("Out readCSV() dataFromFile isEmpty: " + dataFromFile.isEmpty());
        return dataFromFile;
    }

    // Static method looking through dir fir .csv files
    // returning Collection of paths
    private static Collection<Path> findCSVFiles(String searchDirectory) {
        try (Stream<Path> files = Files.walk(Paths.get(searchDirectory))) {
            return files
                    .filter(f -> f.getFileName().toString().matches("\\w+\\.csv"))
                    .collect(Collectors.toList());
        } catch (Exception e){
            log.error("In findCSVFiles() Exception: " + e.getMessage());
            return null;
        }
    }
}
