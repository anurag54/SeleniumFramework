package com.orangehrm.actiondriver;

import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class ActionDriver {

	private WebDriver driver;
	private WebDriverWait wait;

	public ActionDriver(WebDriver driver) {
		this.driver = driver;
		this.wait = new WebDriverWait(driver, Duration.ofSeconds(30));
	}

	// Method to click on an element
	public void click(By by, String value) {
		try {
			waitForElementToBeClickable(by);
			driver.findElement(by).click();
		} catch (Exception e) {
			System.out.println("Unable to click on element: " + e.getMessage());
		}
	}

	// Method to type text into an input field
	public void enterText(By by, String value) {
		try {
			waitForElementToBeVisible(by);
			driver.findElement(by).clear();
			driver.findElement(by).sendKeys(value);
		} catch (Exception e) {
			System.out.println("Unable to enter the value: " + e.getMessage());
		}
	}

	// Method to get text from an element
	public String getText(By by) {
		try {
			waitForElementToBeVisible(by);
			return driver.findElement(by).getText();
		} catch (Exception e) {
			System.out.println("Unable to get text from element: " + e.getMessage());
			return "";
		}
	}

	// Method to compare Two text
	public void compareText(By by, String expectedText) {
		waitForElementToBeVisible(by);
		try {
			String actualText = driver.findElement(by).getText();
			if (expectedText.equals(actualText)) {
				System.out.println("Text are matching: " + actualText + " and " + expectedText);
			} else {
				System.out.println("Text are not matching: " + actualText + " and " + expectedText);
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			System.out.println("Unable to get text from element: " + e.getMessage());
		}
	}
	
	//Method to check if element is displayed
	public boolean isDisplayed(By by) {
		try {
			waitForElementToBeVisible(by);
			boolean isDisplayed = driver.findElement(by).isDisplayed();
			if(isDisplayed) {
				System.out.println("Element is displayed");
				return isDisplayed;
				} else {
					return isDisplayed;
				}
		} catch (Exception e) {
			System.out.println("Element not displayed: " + e.getMessage());
			return false;
		}
	}
	
	// Wait for page load 
	public void waitForPageLoad(int timeOutInSeconds) {
		try {
			wait.withTimeout(Duration.ofSeconds(timeOutInSeconds)).until(
				webDriver -> ((JavascriptExecutor) webDriver).executeScript("return document.readyState").equals("complete"));
			System.out.println("Page loaded successfully");
		} catch (Exception e) {
			System.out.println("Page did not load within " + timeOutInSeconds + " seconds: " + e.getMessage());
		}
	}

	// Scroll to element
	public void scrollToElement(By by) {
		try {
			waitForElementToBeVisible(by);
			JavascriptExecutor js = (JavascriptExecutor) driver;
			js.executeScript("arguments[0].scrollIntoView(true);", driver.findElement(by));
		} catch (Exception e) {
			System.out.println("Unable to scroll to element: " + e.getMessage());
		}
	}
	
	// Wait for element to be clickable
	private void waitForElementToBeClickable(By by) {
		try {
			wait.until(ExpectedConditions.elementToBeClickable(by));
		} catch (Exception e) {
			System.out.println("Element not clickable: " + e.getMessage());
		}
	}

	// Wait for element to be visible
	private void waitForElementToBeVisible(By by) {
		try {
			wait.until(ExpectedConditions.visibilityOfElementLocated(by));
		} catch (Exception e) {
			System.out.println("Element not visible: " + e.getMessage());
		}
	}

}
