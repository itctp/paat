package selenium;

import java.awt.AWTException;
import java.io.IOException;
import java.net.URL;
import java.util.concurrent.TimeUnit;

import org.junit.Rule;
import org.junit.rules.TestName;
import org.openqa.selenium.Platform;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;

import com.saucelabs.common.SauceOnDemandAuthentication;
import com.saucelabs.common.SauceOnDemandSessionIdProvider;
import com.saucelabs.junit.SauceOnDemandTestWatcher;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.remote.SessionId;

public class Selenium extends TestExecutor implements SauceOnDemandSessionIdProvider {

	
    /**
     * Constructs a {@link SauceOnDemandAuthentication} instance using the supplied user name/access key.  To use the authentication
     * supplied by environment variables or from an external file, use the no-arg {@link SauceOnDemandAuthentication} constructor.
     */
    public SauceOnDemandAuthentication authentication = new SauceOnDemandAuthentication("manjunathrabagal", "3bb81394-310c-4545-8ca1-7e430d02e1f0");
    
    /**
     * JUnit Rule which will mark the Sauce Job as passed/failed when the test succeeds or fails.
     */
    public @Rule
    SauceOnDemandTestWatcher resultReportingTestWatcher = new SauceOnDemandTestWatcher(this, authentication);

    /**
     * JUnit Rule which will record the test name of the current test.  This is referenced when creating the {@link DesiredCapabilities},
     * so that the Sauce Job is created with the test name.
     */
    public @Rule TestName testName= new TestName();
	
public static WebDriver driver;


private String sessionId;


@Override
public String getSessionId() {
    return sessionId;
}

	public void test(String url, String browser) throws ClassNotFoundException,IOException, InterruptedException, AWTException {
		
		String driversFolder = "drivers\\";
		String chromeDriver = "chromedriver.exe";
		String ieDriver = "IEDriverServer.exe";
		String testCasesSheetName = "TCS";			
				if (browser.equalsIgnoreCase("Firefox")) {
					
				
					
					/**
					 * sauce code
					 */
					DesiredCapabilities capabilities = DesiredCapabilities.firefox();
			        capabilities.setCapability("version", "27");
			        capabilities.setCapability("platform", Platform.XP);
			        capabilities.setCapability("name",  testName.getMethodName());
			        this.driver = new RemoteWebDriver(
			                new URL("http://" + authentication.getUsername() + ":" + authentication.getAccessKey() + "@ondemand.saucelabs.com:80/wd/hub"),
			                capabilities);
			        this.sessionId = ((RemoteWebDriver)driver).getSessionId().toString();
			        driver.get(url);
					
					
				} else if (browser.equalsIgnoreCase("Chrome")) {
					
				System.setProperty("webdriver.chrome.driver", driversFolder + chromeDriver);
					driver = new ChromeDriver();
					driver.get(url);
					
					/**
					 * sauce snippet
					 */
					
					 DesiredCapabilities capabilities = DesiredCapabilities.chrome();
				        capabilities.setCapability("version", "17");
				        capabilities.setCapability("platform", Platform.XP);
				        capabilities.setCapability("name",  testName.getMethodName());
				        this.driver = new RemoteWebDriver(
				                new URL("http://" + authentication.getUsername() + ":" + authentication.getAccessKey() + "@ondemand.saucelabs.com:80/wd/hub"),
				                capabilities);
				        this.sessionId = ((RemoteWebDriver)driver).getSessionId().toString();
				        driver.get(url);
				
				} else if (browser.equalsIgnoreCase("ie")) {
					System.setProperty("webdriver.ie.driver", driversFolder	+ ieDriver);
					driver = new InternetExplorerDriver();
					driver.get(url);
					Thread.sleep(3000);
					
					/**
					 * sauce snippet
					 */
					
					DesiredCapabilities capabilities = DesiredCapabilities.internetExplorer();
			        capabilities.setCapability("version", "17");
			        capabilities.setCapability("platform", Platform.XP);
			        capabilities.setCapability("name",  testName.getMethodName());
			        this.driver = new RemoteWebDriver(
			                new URL("http://" + authentication.getUsername() + ":" + authentication.getAccessKey() + "@ondemand.saucelabs.com:80/wd/hub"),
			                capabilities);
			        this.sessionId = ((RemoteWebDriver)driver).getSessionId().toString();
			        driver.get(url);
					
				}

				try {
					driver.manage().window().maximize();
					driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
				}
				catch (Exception e) {
                           
				}
				
						TestExecutor executor = new TestExecutor();
						executor.executeTest(testCasesSheetName);

	}
}
