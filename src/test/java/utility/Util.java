package utility;


import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Path;

import java.util.List;
import java.util.Properties;

import org.apache.commons.io.FilenameUtils;

import com.opencsv.CSVReader;
import com.opencsv.CSVReaderBuilder;
import com.opencsv.exceptions.CsvException;

public class Util {
	
	public static String cwd = Path.of("").toAbsolutePath().toString();
	public static Object[][]csvData=null;
	
	
	
		 		
	/**
	 * This method will get data from a property file
	 * @param filePath
	 * @param localizeKey
	 * @return
	 * @throws IOException
	 */
	
	public static String getDataFromPropertyFile(String filePath,String localizeKey) throws IOException {
		
		FileReader readerProp=new FileReader(cwd+FilenameUtils.separatorsToSystem(filePath));  
	    Properties objProp=new Properties();  
	    objProp.load(readerProp);  
	    return objProp.getProperty(localizeKey);
		
	}
	/**
	 * This method will read a CSV File and return List<String[]>
	 * @param filePath
	 * @return
	 * @throws IOException
	 * @throws CsvException
	 */
	
	public static Object[][]  readCSVFile(String filePath) throws IOException, CsvException{
		
		FileReader filereader = new FileReader(cwd+FilenameUtils.separatorsToSystem(filePath));
		CSVReader csvReader = new CSVReaderBuilder(filereader).withSkipLines(1).build();
		List<String[]> csvFullFileData = csvReader.readAll();
		int rowsCSV = csvFullFileData.size();
		int colCSV = csvFullFileData.get(0).length;
		csvData = new Object[rowsCSV][colCSV];
		 for(int i=0;i<rowsCSV;i++) {
			 for(int j=0;j<colCSV;j++) {
				 csvData[i][j]= csvFullFileData.get(i)[j];
			 }
		 }
		
		return csvData;
		
	}
	
	

}
