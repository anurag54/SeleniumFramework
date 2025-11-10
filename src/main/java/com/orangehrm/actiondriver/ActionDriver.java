package com.orangehrm.actiondriver;

import java.time.Duration;

import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.orangehrm.base.BaseClass;

public class ActionDriver {

	private WebDriver driver;
	private WebDriverWait wait;
	public static final Logger logger = BaseClass.logger;

	public ActionDriver(WebDriver driver) {
		this.driver = driver;
		int explicitWait = Integer.parseInt(BaseClass.getProp().getProperty("explicitWait"));
		this.wait = new WebDriverWait(driver, Duration.ofSeconds(explicitWait));
		logger.info("Webdriver instance is created in ActionDriver");
	}

	// Method to click on an element
	public void click(By by) {
		try {
			waitForElementToBeClickable(by);
			driver.findElement(by).click();
			logger.info("Clicked on element");
		} catch (Exception e) {
			logger.error("Unable to click on element: " + e.getMessage());
		}
	}

	// Method to type text into an input field
	public void enterText(By by, String value) {
		try {
			waitForElementToBeVisible(by);
			//driver.findElement(by).clear();
			//driver.findElement(by).sendKeys(value);
			WebElement element = driver.findElement(by);
			element.clear();
			element.sendKeys(value);
			logger.info("Entered text: " + value);
		} catch (Exception e) {
			logger.error("Unable to enter the value: " + e.getMessage());
		}
	}

	// Method to get text from an element
	public String getText(By by) {
		try {
			waitForElementToBeVisible(by);
			return driver.findElement(by).getText();
		} catch (Exception e) {
			logger.error("Unable to get text from element: " + e.getMessage());
			return "";
		}
	}

	// Method to compare Two text
	public boolean compareText(By by, String expectedText) {
		waitForElementToBeVisible(by);
		try {
			String actualText = driver.findElement(by).getText();
			if (expectedText.equals(actualText)) {
				logger.info("Text are matching: " + actualText + " and " + expectedText);
				return true;
			} else {
				logger.error("Text are not matching: " + actualText + " and " + expectedText);
				return false;
			}
		} catch (Exception e) {
			logger.error("Unable to get text from element: " + e.getMessage());
		}
		return false;
	}
	
	//Method to check if element is displayed
	public boolean isDisplayed(By by) {
		try {
			waitForElementToBeVisible(by);
			return driver.findElement(by).isDisplayed();
		} catch (Exception e) {
			logger.error("Element not displayed: " + e.getMessage());
			return false;
		}
	}
	
	// Wait for page load 
	public void waitForPageLoad(int timeOutInSeconds) {
		try {
			wait.withTimeout(Duration.ofSeconds(timeOutInSeconds)).until(
				webDriver -> ((JavascriptExecutor) webDriver).executeScript("return document.readyState").equals("complete"));
			logger.info("Page loaded successfully");
		} catch (Exception e) {
			logger.error("Page did not load within " + timeOutInSeconds + " seconds: " + e.getMessage());
		}
	}

	// Scroll to element
	public void scrollToElement(By by) {
		try {
			waitForElementToBeVisible(by);
			JavascriptExecutor js = (JavascriptExecutor) driver;
			js.executeScript("arguments[0].scrollIntoView(true);", driver.findElement(by));
		} catch (Exception e) {
			logger.error("Unable to scroll to element: " + e.getMessage());
		}
	}
	
	// Wait for element to be clickable
	private void waitForElementToBeClickable(By by) {
		try {
			wait.until(ExpectedConditions.elementToBeClickable(by));
		} catch (Exception e) {
			logger.error("Element not clickable: " + e.getMessage());
		}
	}

	// Wait for element to be visible
	private void waitForElementToBeVisible(By by) {
		try {
			wait.until(ExpectedConditions.visibilityOfElementLocated(by));
		} catch (Exception e) {
			logger.error("Element not visible: " + e.getMessage());
		}
	}

}