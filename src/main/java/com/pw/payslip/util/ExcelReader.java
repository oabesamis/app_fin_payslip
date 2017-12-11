package com.pw.payslip.util;

import java.io.File;
import java.io.FileInputStream;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class ExcelReader {

	public static HashMap loadExcelLines(File fileName){
		
		LinkedHashMap<Integer, HashMap<String, String>> hashMap = new LinkedHashMap<Integer, HashMap<String, String>>();
		
		FileInputStream fis = null;
		
		try{
			fis = new FileInputStream(fileName);
			//Sheet level
			
			XSSFWorkbook workbook = new XSSFWorkbook(fis);
			
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
			    		 rowMap.put(columnNames.getCell(counter).getStringCellValue(), cell.getStringCellValue());
			    		 counter++;
			    	 }	    	
			    	
			    	hashMap.put(row.getRowNum() + 1, rowMap);	//+1 because first entry starts at row 2 in an excel file.
			    }
				
			}
			
			
		}catch (Exception ex){
			System.out.println(ex);
		}
		
		return hashMap;
		
		
	}
	
	
	public static void main(String[] args){
		
		File file = new File("C:\\Users\\Olie.Abesamis\\Documents\\JavaApps\\testdata.xlsx");
		ExcelReader.loadExcelLines(file);
		
	}
	
	
	
}
