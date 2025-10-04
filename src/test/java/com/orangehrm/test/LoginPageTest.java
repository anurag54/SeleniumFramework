package com.orangehrm.test;

import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.orangehrm.base.BaseClass;
import com.orangehrm.pages.HomePage;
import com.orangehrm.pages.LoginPage;

@Test
public class LoginPageTest extends BaseClass {
	
	private LoginPage loginPage;
	private HomePage homePage;
	
	@BeforeMethod
	public void setupPages() {
		loginPage = new LoginPage(getDriver());
		homePage = new HomePage(getDriver());
	}
	
	@Test(priority = 1)
	public void verifyValidLogin() {
		loginPage.login("admin", "admin123");
		Assert.assertTrue(homePage.isAdminTabVisible(), "Admin tab is visible after successful login.");
		Assert.assertTrue(homePage.isOrangeHRMLogoVisible(), "OrangeHRM logo is visible after successful login.");
		homePage.logout();
		staticWait(2); // Static wait to allow logout to complete
	}
	
	@Test(priority = 2)
	public void verifyInvalidLogin() {
		loginPage.login("admin", "admin");
		String expectedErrorMessage = "Invalid credentials";
		Assert.assertTrue(loginPage.isErrorMessageCorrect(expectedErrorMessage), "Error message matches expected text.");
	}

}
