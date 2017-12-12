package com.pw.payslip.util;

import java.io.File;
import java.io.FileInputStream;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.pw.payslip.exception.PaySlipException;

public class ExcelReader {

	public static HashMap loadExcelLines(File fileName){
		
		LinkedHashMap<Integer, HashMap<String, String>> hashMap = new LinkedHashMap<Integer, HashMap<String, String>>();
		
		FileInputStream fis = null;
		
		try{
			fis = new FileInputStream(fileName);
			//Sheet level
			
			XSSFWorkbook workbook = new XSSFWorkbook(fis);
			DataFormatter fmt = new DataFormatter();
			//first sheet of workbook
			for(int i = 0; i <workbook.getNumberOfSheets(); i++){
				XSSFSheet sheet = workbook.getSheetAt(0);//TODO assumption is everything on first sheet
				
				java.util.Iterator<Row> rows = sheet.rowIterator();
				XSSFRow columnNames = sheet.getRow(0); //column names, first row
				
			    while (rows.hasNext()){
			    	XSSFRow row = (XSSFRow) rows.next();
			    	//skip first row
			    	if(row.getRowNum() == 0){
			    		continue; 
			    	}
			    	
			    	//process rows starting at row 2			    	
			    	LinkedHashMap<String, String> rowMap = new LinkedHashMap<String, String>();
			    			    	
			    	java.util.Iterator<Cell> cells = row.cellIterator();
			    	int counter = 0;
			    	 while (cells.hasNext()){
			    		 XSSFCell cell = (XSSFCell) cells.next();
			    		 if (cell.getCellType() == 0){//0 NUMERIC
			    			 rowMap.put(columnNames.getCell(counter).getStringCellValue(), fmt.formatCellValue(cell)+"");				 
			    		 }else{
			    			rowMap.put(columnNames.getCell(counter).getStringCellValue(), fmt.formatCellValue(cell));
			    		 }
			    		 counter++;
			    	 }	  	
			    	
			    	hashMap.put(row.getRowNum() + 1, rowMap);	//reverting + 1, row starts at row 2
			    }
				
			}
			
			
		}catch (Exception ex){
			System.out.println(ex);
		}
		
		return hashMap;
		
		
	}
	
	
	public static void main(String[] args){
		PropertiesUtil propIUtil = new PropertiesUtil();
		Properties prop = propIUtil.loadProperties();
		File file = new File("C:\\Users\\Olie.Abesamis\\Documents\\JavaApps\\testdata.xlsx");
		Map results = ExcelReader.loadExcelLines(file);
		
		 System.out.println(results.size());
		//looping on the list start with 1 since it is the first row with data
		for (int i = 2; i<= results.size(); i++){
			Map cells = (LinkedHashMap<String, String>) results.get(i);
			//TODO
			//loop to generate pdf
			//send email
			PdfCreator creator = new PdfCreator();
			creator.createPdf(null, cells);
			String title = (String) prop.get(PayConstants.COMPANY_NAME_FIELD);
	        System.out.println(title);
			System.out.println(i + " " + cells.get(prop.get(PayConstants.PERIOD_FIELD)) + " " + cells.get(prop.get(PayConstants.EMPLOYEE_ID_FIELD)));			
		}
		
	}
	
	
	
}
