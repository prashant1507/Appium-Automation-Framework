package org.automation.factories;

import org.automation.constants.GlobalVars;
import org.automation.driver.DriverManager;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

/**
 * Managing different explicit wait and returning webElement.
 * 
 * <br>
 * Class is final to avoid extend.
 * <br><br>
 * Apr 8, 2021
 * @author User1
 * @version 1.0
 *
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class ExplicitWaitFactory {

	/**
	 * Explicit wait for element to be clickable.
	 * <br>
	 * Apr 8, 2021
	 * @param by to locate web elements
	 * @return WebElement
	 *
	 */
	public static WebElement explicitWaitForElementToBeClickable(By by) {
		return new WebDriverWait(DriverManager.getDriver(), GlobalVars.getExplicitWait())
				.until(ExpectedConditions.elementToBeClickable(by));
	}

	/**
	 * Explicit wait for element to be present.
	 * <br>
	 * Apr 8, 2021
	 * @param by to locate web elements
	 * @return WebElement
	 *
	 */
	public static WebElement explicitWaitForElementToBePresent(By by) {
		return new WebDriverWait(DriverManager.getDriver(), GlobalVars.getExplicitWait())
				.until(ExpectedConditions.presenceOfElementLocated(by));
	}
}