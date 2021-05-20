package org.automation.pages;

import org.openqa.selenium.By;

public final class LoginPage extends BasePage {

	private final By pageLogoImg = By.id("logo_icon");
	private final By notificationBell = By.id("in_app_notification_bell");
	private final By cartIcon = By.id("cart_bg_icon");

	public boolean isPageLogoImgDisplayed() {
		return isDisplayed(pageLogoImg);
	}

	public boolean isNotificationBellDisplayed() {
		return isDisplayed(notificationBell);
	}

	public boolean isNotificationIconDisplayed() {
		return isDisplayed(cartIcon);
	}
}
