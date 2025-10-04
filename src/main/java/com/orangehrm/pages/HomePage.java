package com.orangehrm.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import com.orangehrm.actiondriver.ActionDriver;

public class HomePage {

	ActionDriver actionDriver;

	// Define locators using By class
	private By adminTab = By.xpath("//span[text()='Admin']");
	private By userIDButton = By.className("oxd-userdropdown-name");
	private By logoutButton = By.xpath("//a[text()='Logout']");
	private By orangeHRMLogo = By.xpath("//div[@class='oxd-brand-banner']//img");

	// Initialize ActionDriver object by passing WebDriver instance
	public HomePage(WebDriver driver) {
		this.actionDriver = new ActionDriver(driver);
	}

	// Method to verify if Admin tab is displayed
	public boolean isAdminTabVisible() {
		return actionDriver.isDisplayed(adminTab);
	}

	// Method to verify if OrangeHRM logo is displayed
	public boolean isOrangeHRMLogoVisible() {
		return actionDriver.isDisplayed(orangeHRMLogo);
	}

	// Method to perform logout action
	public void logout() {
		actionDriver.click(userIDButton);
		actionDriver.click(logoutButton);
	}

}
