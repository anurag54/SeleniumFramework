package com.orangehrm.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import com.orangehrm.actiondriver.ActionDriver;

public class LoginPage {
	
	private ActionDriver actionDriver;
	
	//Define locators using By class
	
	private By usernameField = By.name("username");
	private By passwordField = By.cssSelector("input[type='password']");
	private By loginButton = By.xpath("//button[@class='oxd-button oxd-button--medium oxd-button--main orangehrm-login-button']");
	private By errorMessage = By.xpath("//p[text()='Invalid credentials']");
	
	//Initialize ActionDriver object by passing WebDriver instance
	public LoginPage(WebDriver driver) {
		this.actionDriver = new ActionDriver(driver);
	}
	
	// Method to perform login action
	public void login(String username, String password) {
		actionDriver.enterText(usernameField, username);
		actionDriver.enterText(passwordField, password);
		actionDriver.click(loginButton);
	}
	
	// Method to check if error message is displayed
	public boolean isErrorMethodDisplayed() {
		return actionDriver.isDisplayed(errorMessage);
	}
    
	// Method to get text from error message 
	public String getErrorMessageText() {
		return actionDriver.getText(errorMessage);
	}
	
	// Verify if error message is correct or not
	public boolean isErrorMessageCorrect(String expectedMessage) {
		return actionDriver.compareText(errorMessage, expectedMessage);
	}
}
