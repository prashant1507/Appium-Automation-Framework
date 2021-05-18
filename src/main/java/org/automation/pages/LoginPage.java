package org.automation.pages;

import org.openqa.selenium.By;

public final class LoginPage extends BasePage {

	private final By pageLogoImg = By.xpath("//div[@id='divLogo']/img");
	private final By usernameTextbox = By.id("txtUsername");
	private final By passwordTextbox = By.id("txtPassword");
	private final By loginBtn = By.xpath("//input[@id='btnLogin']");
	private final By forgotPasswordLink = By.xpath("//div[@id='forgotPasswordLink']/a");
	private final By invalidCredentialsErrorMsg = By.xpath("//span[@id='spanMessage']");

	public boolean isPageLogoImgDisplayed() {
		return isDisplayed(pageLogoImg);
	}
	
	public boolean isForgotPasswordLinkDisplayed() {
		return isDisplayed(forgotPasswordLink);
	}

	public void enterUsername(String username) {
		sendKeys(usernameTextbox, username, "Username");
	}

	public void enterPassword(String password) {
		sendKeys(passwordTextbox, password, "Password");
	}

	public void clickLoginBtn() {
		click(loginBtn, "Login button");
	}
	
	public String getInvalidCredentialsErrorMsg() {
		return getText(invalidCredentialsErrorMsg, "Invalid credential error message");
	}
}
