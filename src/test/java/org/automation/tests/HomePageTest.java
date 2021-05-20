package org.automation.tests;

import org.automation.listeners.RetryFailedTests;
import org.automation.pages.LoginPage;
import org.testng.Assert;
import org.testng.SkipException;
import org.testng.annotations.Test;

public final class HomePageTest extends BaseSetup {

    @Test(testName = "Login page verification", description = "Verify if login page is displayed - HomePageTest", retryAnalyzer = RetryFailedTests.class)
    public void verifyLoginPage() {
        LoginPage loginPage = new LoginPage();
        Assert.assertTrue(loginPage.isPageLogoImgDisplayed(), "Icon not displayed.");
        Assert.assertTrue(loginPage.isNotificationBellDisplayed(), "Notification Bell not displayed.");
        Assert.assertTrue(loginPage.isNotificationIconDisplayed(), "Notification Icon not displayed.");
    }

    @Test(testName = "Skip test name", description = "Skip test case - HomePageTest")
    public void skip() {
        throw new SkipException("hi, I am skipped");
    }

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
