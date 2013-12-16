package selenium;
import java.io.IOException;
import java.util.Iterator;
import java.util.Set;

import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.Select;

import tool_selector.ExcelLibrary;

public class TestExecutor 
{
	public void executeTest(String TCS) throws IOException 
	{
		//Work with FF
		WebElement element=null;
		//create object of ExcelLibrary
		ExcelLibrary lib = new ExcelLibrary();
		//reptHand = new ReportHandler(scenarioName);
	    // ReadWER readwer = new ReadWER();
		String testCasesSheetName="TCS";
		int numOfTestSteps = lib.getRowCount(testCasesSheetName);
		for(int testStep=1;testStep<=numOfTestSteps;testStep++) 
		{
			String xpath = lib.getExcelData(testCasesSheetName, testStep, 2);
			String id=lib.getExcelData(testCasesSheetName, testStep, 3);
			String name=lib.getExcelData(testCasesSheetName, testStep, 1);
			String action = lib.getExcelData(testCasesSheetName, testStep, 7);
			String data = lib.getExcelData(testCasesSheetName, testStep, 8);	 
			try 
			{
				element = Selenium.driver.findElement(By.xpath(xpath));
			} 
			catch (Exception e) 
			{
			lib.writeToExcel(testCasesSheetName, testStep, 9, "FAIL");	
			}				
			try 
			{					
				element = Selenium.driver.findElement(By.name(name));
			}
			catch (Exception e) 
			{
				lib.writeToExcel(testCasesSheetName, testStep,9, "FAIL");	
			}				
			try 
			{
				element = Selenium.driver.findElement(By.id(id));
			} 
			catch (Exception e) 
			{
					lib.writeToExcel(testCasesSheetName, testStep, 9, "FAIL");	
			}	
			//actions
			if(action.equals("click"))
			{
				if(element!=null)
				{
					try
					{
						element.click();
						lib.writeToExcel(testCasesSheetName, testStep,9, "PASS");
						//	reptHand.pass("Clicked on " +logicalName);
					}
					catch(Exception e)
					{
						lib.writeToExcel(testCasesSheetName, testStep,9, "FAIL");	
					}
				}
			}
			else if(action.equals("sendKeys"))
			{
				if(element!=null)
				{
					try
					{
						element.sendKeys(data);
						lib.writeToExcel(testCasesSheetName, testStep, 9, "PASS");
						//reptHand.pass("Typed "+data+" into " +logicalName+ " textbox/textarea");
					}
					catch(Exception e)
					{
						lib.writeToExcel(testCasesSheetName, testStep,9, "FAIL");	
					//	reptHand.fail("Data did not enter " +logicalName+"textbox/textarea");
					}
				}
			}  else if(action.equals("select")){
				if(element!=null){
					try{
					Select dd = new Select(element);
					dd.selectByVisibleText(data);
					lib.writeToExcel(testCasesSheetName, testStep, 9, "PASS");
				//	reptHand.pass("Selected " +data+ " from " +logicalName);
					}
					catch(Exception e)
					{
						//reptHand.fail("Did Not Select " +data+ " from " +logicalName);
					}
				}
			}  else if(action.equals("switchWindow")){
				Set<String> allWindowHandles = Selenium.driver.getWindowHandles();
				Iterator<String> it = allWindowHandles.iterator();
				//loop through all the elements in the set till there
				//exists a next element
				while(it.hasNext()){
					//switch to the window
					Selenium.driver.switchTo().window(it.next());
					//capture the title of the window
					String windowTitle = Selenium.driver.getTitle();
					//compare the title with what user has passed.
					//If they match, no need to continue loop. break
					if(windowTitle.equals(data)){
						break;
					}
				}
			}else if(action.equals("close")){
				try{
				Selenium.driver.close();
				lib.writeToExcel(testCasesSheetName, testStep, 9, "PASS");
				//reptHand.pass("Close the Window ");
				}
				catch(Exception e)
				{
					//reptHand.fail("Window Did Not Close ");
				}
			}
			
			//switch to frame
			else if(action.equals("switchToFrame")){
				Selenium.driver.switchTo().frame(element);
			}
			//switch back from frame
			else if(action.equals("switchBackFromFrame")){
				Selenium.driver.switchTo().defaultContent();
			}
			//handling java script alert/confirmation
			else if(action.equals("alert")){
				Alert alrt = Selenium.driver.switchTo().alert();
				if(data.equalsIgnoreCase("ok")){
					try{
					alrt.accept();
					lib.writeToExcel(testCasesSheetName, testStep, 9, "PASS");
				//	reptHand.pass("Clicked on OK BUTTON " +logicalName);
					}
					catch(Exception e)
					{
						//reptHand.fail("Did Not Click on OK BUTTON " +logicalName);
					}
				}
				else if(data.equalsIgnoreCase("cancel")){
					try{
					alrt.dismiss();
					lib.writeToExcel(testCasesSheetName, testStep, 9, "PASS");
					//reptHand.pass("Clicked on CANCEL BUTTON " +logicalName);
					}
					catch(Exception e)
					{
						//reptHand.fail("Did Not click on CANCEL BUTTON " +logicalName);
					}
				}
			}
			else if(action.equals("mouse_over")){
				try{
				Actions a1 = new Actions(Selenium.driver);
				a1.moveToElement(element).perform();
				lib.writeToExcel(testCasesSheetName, testStep, 9, "PASS");
				//reptHand.pass("Moved on that Element "+logicalName);
				}
				catch(Exception e)
				{
					//reptHand.fail("Did Not Moved on that Element "+logicalName);
				}
			}
			else if(action.equals("verifyTitle")){
				String actual = Selenium.driver.getTitle();
				if(actual.equals(data)){
					try{
					lib.writeToExcel(testCasesSheetName, testStep, 9, "PASS");
					//reptHand.pass("Title matches " +logicalName);
					}
					catch(Exception e)
					{
						//reptHand.fail("Title did not match " +logicalName);
					}
				}
			}
			else if(action.equals("verifyText")){
				String actual = element.getText();
				if(actual.equals(data)){
					try{
					lib.writeToExcel(testCasesSheetName, testStep, 9, "PASS");
					//reptHand.pass("Text matches " +logicalName);
					}
					catch(Exception e)
					{
						//reptHand.fail("Text did not match " +logicalName);
					}
				}
			}
			else if(action.equals("verifyElement")){
				if(element!=null){
					try{
					lib.writeToExcel(testCasesSheetName, testStep, 9, "PASS");
					//reptHand.pass("Element found " +logicalName);
					}
					catch(Exception e)
					{
						//reptHand.fail("Element not found " +logicalName);
					}
				}
			}
		}
	}
}


