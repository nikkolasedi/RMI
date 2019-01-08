package com.jcg.csv2excel;

import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.Serializable;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

import org.apache.commons.lang.math.NumberUtils;
import org.apache.log4j.Logger;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.streaming.SXSSFSheet;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;

import com.opencsv.CSVReader;

public class EPowModImplementation extends UnicastRemoteObject implements EPowModInterface, Serializable {

	

	protected EPowModImplementation(String s) throws RemoteException {
		File storageDir = new File (s);
		storageDir.mkdir();
	}

	private static final long serialVersionUID = 1L;
	public static final char FILE_DELIMITER = ',';
	public static final String FILE_EXTN = ".xlsx";
	public static final String FILE_NAME = "EXCEL_DATA";

	private static Logger logger = Logger.getLogger(EPowModImplementation.class);

	public String convertCsvToXls(String xlsFileLocation, String csvFilePath, String csvFilePath1, String sheetName, String sheetName1) throws RemoteException {
		SXSSFSheet sheet = null;
		CSVReader reader = null;
		Workbook workBook = null;
		String generatedXlsFilePath = "";
		FileOutputStream fileOutputStream = null;

		try {

			/**** Get the CSVReader Instance & Specify The Delimiter To Be Used ****/
			/**read the first worksheet**/
			String[] nextLine;
			reader = new CSVReader(new FileReader(csvFilePath), FILE_DELIMITER);

			workBook = new SXSSFWorkbook();
			sheet = (SXSSFSheet) workBook.createSheet(sheetName);
			
			
			int rowNum = 0;
			while((nextLine = reader.readNext()) != null) {
				Row currentRow = sheet.createRow(rowNum++);
				for(int i=0; i < nextLine.length; i++) {
					if(NumberUtils.isDigits(nextLine[i])) {
						currentRow.createCell(i).setCellValue(Integer.parseInt(nextLine[i]));
					} else if (NumberUtils.isNumber(nextLine[i])) {
						currentRow.createCell(i).setCellValue(Double.parseDouble(nextLine[i]));
					} else {
						currentRow.createCell(i).setCellValue(nextLine[i]);
					}
				}
			}
			
			/**reading the second worksheet**/
			sheet = (SXSSFSheet) workBook.createSheet(sheetName1);
			reader = new CSVReader(new FileReader(csvFilePath1), FILE_DELIMITER);
			rowNum = 0;
			logger.info("Creating New .Xls File From The Already Generated .Csv File");
			while((nextLine = reader.readNext()) != null) {
				Row currentRow = sheet.createRow(rowNum++);
				for(int i=0; i < nextLine.length; i++) {
					if(NumberUtils.isDigits(nextLine[i])) {
						currentRow.createCell(i).setCellValue(Integer.parseInt(nextLine[i]));
					} else if (NumberUtils.isNumber(nextLine[i])) {
						currentRow.createCell(i).setCellValue(Double.parseDouble(nextLine[i]));
					} else {
						currentRow.createCell(i).setCellValue(nextLine[i]);
					}
				}
			}

			generatedXlsFilePath = xlsFileLocation + FILE_NAME + FILE_EXTN;
			logger.info("The File Is Generated At The Following Location?= " + generatedXlsFilePath);

			fileOutputStream = new FileOutputStream(generatedXlsFilePath.trim());
			workBook.write(fileOutputStream);
		} catch(Exception exObj) {
			logger.error("Exception In convertCsvToXls() Method?=  " + exObj);
		} finally {			
			try {

				/**** Closing The Excel Workbook Object ****/
				workBook.close();

				/**** Closing The File-Writer Object ****/
				fileOutputStream.close();

				/**** Closing The CSV File-ReaderObject ****/
				reader.close();
			} catch (IOException ioExObj) {
				logger.error("Exception While Closing I/O Objects In convertCsvToXls() Method?=  " + ioExObj);			
			}
		}

		return generatedXlsFilePath;
	}

	public void uploadFileToServer(byte[] mydata, String serverpath, int length) throws RemoteException {
		try {
    		File serverpathfile = new File(serverpath);
    		FileOutputStream out=new FileOutputStream(serverpathfile);
    		byte [] data=mydata;
			
    		out.write(data);
			out.flush();
	    	out.close();
	 
		} catch (IOException e) {
			
			e.printStackTrace();
		}
    	
    	System.out.println("Done writing data...");
		
		
	}	
}