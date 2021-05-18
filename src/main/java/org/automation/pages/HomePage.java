package org.automation.pages;

import org.automation.factories.BrowserOperationFactory;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public final class HomePage extends BasePage {
	
	@FindBy(xpath = "//div[@id='branding']/a[@id='welcome']")
	private By welcomeUserText;
	
	public WebElement getWelcomeUser() {
		return getWebElement(welcomeUserText);
	}
	
	public boolean getWelcomeText(String value) {
		return isStringContains(welcomeUserText, value);
	}
	
	public String getPageTitle() {
		return BrowserOperationFactory.getTitle();
	}
}

