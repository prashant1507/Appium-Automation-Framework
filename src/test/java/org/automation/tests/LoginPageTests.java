package org.automation.tests;

import io.appium.java_client.MobileElement;
import org.automation.dataproviders.DataProviders;
import org.automation.driver.DriverManager;
import org.automation.listeners.RetryFailedTests;
import org.automation.pages.LoginPage;
import org.automation.pages.HomePage;
import org.openqa.selenium.By;
import org.testng.Assert;
import org.testng.SkipException;
import org.testng.annotations.Test;

public final class LoginPageTests extends BaseSetup {

	@Test(testName = "Login page verification", description = "Verify if login page is displayed", retryAnalyzer = RetryFailedTests.class)
	public void verifyLoginPage() {
		MobileElement btn_seven = DriverManager.getDriver().findElement(By.id("com.oneplus.calculator:id/digit_7"));
		MobileElement btn_three = DriverManager.getDriver().findElement(By.id("com.oneplus.calculator:id/digit_3"));
		MobileElement btn_plus = DriverManager.getDriver().findElement(By.id("com.oneplus.calculator:id/op_add"));
		MobileElement btn_equal = DriverManager.getDriver().findElement(By.id("com.oneplus.calculator:id/eq"));
		MobileElement result = DriverManager.getDriver().findElement(By.id("com.oneplus.calculator:id/result"));
		btn_seven.click();
		btn_plus.click();
		btn_three.click();
		btn_equal.click();
		String result_cal = result.getText();
		System.out.println("Result is: "+result_cal);
		System.out.println("Text: "+btn_seven.getText());
		System.out.println("Text: "+btn_three.getText());
	}
	
//	@Test(testName = "Skip test name", description = "Skip test case")
//	public void skip() {
//		LoginPage loginPage = new LoginPage();
//		loginPage.enterUsername("Admin567");
//		loginPage.enterPassword("admin123");
//		// Using explicit wait in clickLoginBtn method
//		loginPage.clickLoginBtn();
//		throw new SkipException("hi, I am skipped");
//	}
//
//	@Test(testName = "Valid login details duplicate", description = "duplicate")
//	public void verifyLogin1() {
//		LoginPage loginPage = new LoginPage();
//		HomePage homePage = new HomePage();
//		loginPage.enterUsername("Admin77777777");
//		loginPage.enterPassword("admin123");
//		// Using explicit wait in clickLoginBtn method
//		loginPage.clickLoginBtn();
//		Assert.assertFalse(homePage.getWelcomeText("Welcome"), "Failed to login.");
//	}
//
//	@Test(testName = "Valid login details", dataProvider = "ValidLoginCreds", dataProviderClass = DataProviders.class, description = "Verify if user is able to login using valid credentials")
//	public void verifyLogin(String username, String password) {
//		LoginPage loginPage = new LoginPage();
//		HomePage homePage = new HomePage();
//		loginPage.enterUsername(username);
//		loginPage.enterPassword(password);
//		// Using explicit wait in clickLoginBtn method
//		loginPage.clickLoginBtn();
//		Assert.assertFalse(homePage.getWelcomeText("Welcome"), "Failed to login.");
//	}
//
//	@Test(testName = "Verify invalid login details", dataProvider = "InvalidLoginCreds", dataProviderClass = DataProviders.class, description = "Verify if user is not able to login using invalid credentials")
//	public void verifyInvalidLogin(String username, String password) throws InterruptedException {
//		LoginPage loginPage = new LoginPage();
//		loginPage.enterUsername(username);
//		loginPage.enterPassword(password);
//		// Using explicit wait in clickLoginBtn method
//		loginPage.clickLoginBtn();
//		Assert.assertEquals(loginPage.getInvalidCredentialsErrorMsg(), "Invalid credentialsss", "Failed to login.");
//	}
}
