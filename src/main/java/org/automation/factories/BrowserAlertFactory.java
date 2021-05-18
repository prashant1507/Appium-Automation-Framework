package org.automation.factories;

import org.automation.driver.DriverManager;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

/**
 * 
 * This class manages all the browser related alerts.
 * <br>
 * Class is final to avoid extend.
 * <br><br>
 * Apr 8, 2021
 * @author User1
 * @version 1.0
 *
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class BrowserAlertFactory {

	public static void dismissAert() {
		DriverManager.getDriver().switchTo().alert().dismiss();
	}

	public static void acceptAert() {
		DriverManager.getDriver().switchTo().alert().accept();
	}

	public static void sendValueToAlert(String value) {
		DriverManager.getDriver().switchTo().alert().sendKeys(value);
	}
	
	public static String getAertText() {
		return DriverManager.getDriver().switchTo().alert().getText();
	}
}
