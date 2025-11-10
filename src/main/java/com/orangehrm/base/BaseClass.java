package com.orangehrm.base;

import java.io.FileInputStream;
import java.io.IOException;
import java.time.Duration;
import java.util.Properties;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.LockSupport;

import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;

import com.orangehrm.actiondriver.ActionDriver;
import com.orangehrm.utilities.LoggerManager;

public class BaseClass {

	protected static Properties prop;
	protected static WebDriver driver;
	private static ActionDriver actionDriver;
	public static final Logger logger = LoggerManager.getLogger(BaseClass.class);
	
	// This method can be used to load configuration if needed separately
	
	@BeforeSuite
	public void loadConfig() throws IOException {
		prop = new Properties();
		FileInputStream fis = new FileInputStream("src/main/resources/config.properties");
		prop.load(fis);
		logger.info("Configuration properties loaded successfully.");
	}
	
	@BeforeMethod
	public void setup() throws IOException {
		launchBrowser();
		configureBrowser();
		staticWait(2); // Static wait to allow the browser to stabilize
		 logger.info("Browser launched and configured successfully.");
		
	//Initialize ActionDriver instance only once
		if(actionDriver == null) {
			actionDriver = new ActionDriver(driver);
			logger.info("ActionDriver instance created in BaseClass");
		}
	}
	
	// Initialize the webDrive based on the browser defined in properties file
	private void launchBrowser() {
		// This method can be used to launch browser if needed separately
		String browser = prop.getProperty("browser");

		if (browser.equalsIgnoreCase("chrome")) {
			driver = new ChromeDriver();
			logger.info("Chrome browser launched.");
		} else if (browser.equalsIgnoreCase("firefox")) {
			driver = new FirefoxDriver();
			logger.info("Firefox browser launched.");
		} else if (browser.equalsIgnoreCase("edge")) {
			driver = new EdgeDriver();
			logger.info("Edge browser launched.");
		} else {
			throw new IllegalArgumentException("Browser not supported: " + browser);
		}
	}
	
	// Configure the browser settings
	private void configureBrowser() {
		// Implicit wait
		int implicitWait = Integer.parseInt(prop.getProperty("implicitWait"));
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(implicitWait));

		// Launch the URL
		try {
			driver.get(prop.getProperty("url"));
		} catch (Exception e) {
			System.out.println("Unable to launch the URL: " + e.getMessage());
		}
		

		// maximize the browser window
		driver.manage().window().maximize();
	}
	
	// Quit will close all the browser windows opened by WebDriver,
	// Close will close only the current window
	
	@AfterMethod
	public void tearDown() {
		if (driver != null) {
			try {
				driver.quit();
			} catch (Exception e) {
				System.out.println("Error while quitting the browser: " + e.getMessage()); 
			} 
							
		}
		logger.info("Webdriver instance is closed.");
		driver=null;
		actionDriver=null;
	}
	
	//Getter method for Properties
	public static Properties getProp() {
		return prop;
	}
	
	// Setter method for Properties
	public void setProp(Properties prop) {
		this.prop = prop;
	}
	
	//Getter method for WebDriver
	public static WebDriver getDriver() {
	      if (driver == null) {
	    	  System.out.println("WebDriver is not initialized");
	          throw new IllegalStateException("WebDriver instance is not initialized");
	      }
		return driver;
	}
	
	//Getter method for ActionDriver
	public static ActionDriver getActionDriver() {
	      if (actionDriver == null) {
	    	  System.out.println("ActionDriver is not initialized");
	          throw new IllegalStateException("ActionDriver instance is not initialized");
	      }
		return actionDriver;
	}
	
	/*// Driver get method
	public static WebDriver getDriver() {
		return driver;
	}
	
	//Driver set method
	public void setDriver(WebDriver driver) {
		this.driver = driver;
	} **/
	
	//Static wait for pause execution for a specified time
	public void staticWait(int seconds) {
		LockSupport.parkNanos(TimeUnit.SECONDS.toNanos(seconds));
	}

}
