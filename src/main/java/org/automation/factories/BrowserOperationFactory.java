package org.automation.factories;

import java.util.Set;

import org.automation.driver.DriverManager;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

/**
 * 
 * Manages web page related operations.
 * <br>
 * Class is final to avoid extend.
 * <br><br>
 * Apr 8, 2021
 * @author User1
 * @version 1.0
 *
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class BrowserOperationFactory {

	public static String getTitle() {
		return DriverManager.getDriver().getTitle();
	}

	public static String getPageSource() {
		return DriverManager.getDriver().getPageSource();
	}

	public static String getCurrentUrl() {
		return DriverManager.getDriver().getCurrentUrl();
	}

	public static String getWindowHandle() {
		return DriverManager.getDriver().getWindowHandle();
	}
	
	public static void switchToWindow(String windowName) {
		DriverManager.getDriver().switchTo().window(windowName);
	}

	public static Set<String> getAllWindowHandle() {
		return DriverManager.getDriver().getWindowHandles();
	}
}
