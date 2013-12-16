package tool_selector;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;

public class ExcelLibrary {
	
	String excelSheet = "Scenarios.xls";
	
	public String getExcelData(String sheetName, int row, int cell){
		String retVal = null;
		try {
			FileInputStream fis = new FileInputStream(excelSheet);
			Workbook wb = WorkbookFactory.create(fis);
			Sheet s = wb.getSheet(sheetName);
			Row r = s.getRow(row);
			Cell c = r.getCell(cell);
			retVal=c.getStringCellValue();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (InvalidFormatException e) {
			e.printStackTrace();
		}
		return retVal;
	}

	public double getDATAValue(String sheetName, int row, int cell)
	{
		double retValue = 0;
		try {
			FileInputStream fis = new FileInputStream(excelSheet);
			Workbook wb = WorkbookFactory.create(fis);
			Sheet s = wb.getSheet(sheetName);
			Row r = s.getRow(row);
			Cell c = r.getCell(cell);
			retValue=c.getNumericCellValue();
			
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (InvalidFormatException e) {
			e.printStackTrace();
		}
		return retValue;
		
	}
	
	public int getRowCount(String sheetName){
		int rowCount=1;
		try {
			FileInputStream fis = new FileInputStream(excelSheet);
			Workbook wb = WorkbookFactory.create(fis);
			Sheet s = wb.getSheet(sheetName);
			rowCount = s.getLastRowNum();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (InvalidFormatException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return rowCount;
	}
	public void writeToExcel(String sheetName, int row, int cell, String data){
		try {
		FileInputStream fis = new FileInputStream(excelSheet);
		Workbook wb = WorkbookFactory.create(fis);
		Sheet s = wb.getSheet(sheetName);
		Row r = s.getRow(row);	
		Cell c = r.createCell(cell);
		
		CellStyle style = wb.createCellStyle();
		  c.setCellValue(data);
		  
		  if (data.equalsIgnoreCase("PASS"))
		  { 
			  style.setFillForegroundColor(IndexedColors.GREEN.getIndex());
				 style.setFillPattern(CellStyle.SOLID_FOREGROUND); 
				 //c. setCellValue("fail");
				  c. setCellStyle(style);
				  
		  }
		 else
		  {
			  style.setFillForegroundColor(IndexedColors.RED.getIndex());
				 style.setFillPattern(CellStyle.SOLID_FOREGROUND); 
				//  c. setCellValue("pass");
				  c. setCellStyle(style);
		  }
		
		  FileOutputStream fos = new FileOutputStream(excelSheet);
			wb.write(fos);
		
		
		fos.close();
		fis.close();
		
		
	} catch (FileNotFoundException e) {
		e.printStackTrace();
	} catch (InvalidFormatException e) {
		e.printStackTrace();
	} catch (IOException e) {
		e.printStackTrace();
	}
		
}

	

	
	
	
		}	
		
		
	
		


	
