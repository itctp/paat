package tool_selector;

import java.io.IOException;

import org.junit.Test;

import selenium.Selenium;


public class Main_class 
{
	public static String masterSheetName = "Master";
	public static ExcelLibrary lib = new ExcelLibrary();
	
	public static String execStatus;
	public static String browser;
	public static String url;
	public static String TestTool;
	
	@Test
	public void test() throws IOException, Throwable
	{
		//Logger.getRootLogger().setLevel(Level.OFF);
		int numOfScripts = lib.getRowCount(masterSheetName);
		for (int row = 1; row <= numOfScripts; row++) 
		{
			execStatus = lib.getExcelData(masterSheetName, row, 0);
			browser=lib.getExcelData(masterSheetName, row, 1);
		  url=lib.getExcelData(masterSheetName, row, 2);
		  
		 TestTool = lib.getExcelData(masterSheetName, row, 3);
		 
			if (execStatus.equalsIgnoreCase("yes"))
			{
				if (TestTool.equalsIgnoreCase("Selenium")) 
				{
					try
					{
						Selenium sele=new Selenium();
						sele.test(url,browser);
						lib.writeToExcel(masterSheetName, row, 4, "FAIL");
						
					}
					catch(Exception e)
					{
					
					lib.writeToExcel(masterSheetName, row, 4, "PASS");
					}
				}
			
//				else if (TestTool.equalsIgnoreCase("jwebunit"))
//				{
//					try 
//					{  
//						Jwebunitstart jws=new Jwebunitstart();
//						jws.test(url);
//						lib.writeToExcel(masterSheetName, row, 4, "FAIL");
//						
//					}
//					catch(Exception e)
//					{  
//					
//						
//					 lib.writeToExcel(masterSheetName, row, 4, "PASS");
//						
//					}
//				}
			}
		}
	}
}



