package com.orangehrm.test;

import org.testng.Assert;
import org.testng.annotations.Test;

import com.orangehrm.base.BaseClass;

public class DummyTest2 extends BaseClass {

	@Test
	public void dummyTest2() {
		// Navigate to the OrangeHRM application URL before checking the title
		driver.get("https://opensource-demo.orangehrmlive.com/web/index.php/auth/login"); // TODO: Replace with actual OrangeHRM URL

		// Wait for the title to be 'OrangeHRM' (up to 10 seconds)
		new org.openqa.selenium.support.ui.WebDriverWait(driver, java.time.Duration.ofSeconds(10))
			.until(d -> d.getTitle().equals("OrangeHRM"));

		String title = driver.getTitle();
		Assert.assertEquals(title, "OrangeHRM");
		System.out.println("Title verified: " + title);
	}
}