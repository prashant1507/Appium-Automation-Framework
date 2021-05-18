package org.automation.pages;

import java.util.List;
import org.automation.driver.DriverManager;
import org.automation.reports.ExtentLogger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

/**
 * This class has methods to do various operations with webelements.
 * 
 * <br>
 * Class is final to avoid extend.
 * <br><br>
 * Apr 8, 2021
 * @author User1
 * @version 1.0
 *
 */
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class BasePage {
	
	private Select getSelectWebElement(By by) {
		return new Select(DriverManager.getDriver().findElement(by));
	}
	
	protected void wait(By by, int milliSeconds) {
		try {
			DriverManager.getDriver().findElement(by).wait(milliSeconds);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	protected void click(By by, String elementNameForReport) {
		DriverManager.getDriver().findElement(by).click();
		ExtentLogger.pass(elementNameForReport + " - clicked Successfully.");
	}

	protected void clear(By by, String elementNameForReport) {
		DriverManager.getDriver().findElement(by).clear();
		ExtentLogger.pass(elementNameForReport + " - cleared Successfully.");
	}

	protected void sendKeys(By by, String value, String elementNameForReport) {
		DriverManager.getDriver().findElement(by).sendKeys(value);
		ExtentLogger.pass(elementNameForReport + " - '" + value + "' sent successfully.");
	}
	
	protected String getText(By by, String elementNameForReport) {
		String text = DriverManager.getDriver().findElement(by).getText();
		ExtentLogger.pass(elementNameForReport +" - '" + text + "' obtained successfully.");
		return text;
	}

	protected boolean isDisplayed(By by) {
		return DriverManager.getDriver().findElement(by).isDisplayed();
	}

	protected boolean isSelected(By by) {
		return DriverManager.getDriver().findElement(by).isSelected();
	}

	protected boolean isEnabled(By by) {
		return DriverManager.getDriver().findElement(by).isEnabled();
	}
	
	protected WebElement getWebElement(By by) {
		return DriverManager.getDriver().findElement(by);
	}

	protected boolean isAllElemetsEmpty(By by) {
		return DriverManager.getDriver().findElements(by).isEmpty();
	}

	protected int getAllElemetsEmpty(By by) {
		return DriverManager.getDriver().findElements(by).size();
	}
	
	protected boolean isStringContains(By by, String value) {
		return DriverManager.getDriver().findElement(by).getText().contains(value);
	}
	
	protected boolean isStringEquals(By by, String value) {
		return DriverManager.getDriver().findElement(by).getText().equals(value);
	}
	
	protected boolean isStringEqualsIgnoreCase(By by, String value) {
		return DriverManager.getDriver().findElement(by).getText().equalsIgnoreCase(value);
	}
	
	protected void getDropDownValueByVisibleText(By by, String value) {
		getSelectWebElement(by).selectByVisibleText(value);
	}
	
	protected void getDropDownValueByIndex(By by, int index) {
		getSelectWebElement(by).selectByIndex(index);
	}
	
	protected void getDropDownValueByValue(By by, String value) {
		getSelectWebElement(by).selectByValue(value);
	}
	
	protected List<WebElement> getDropDownOptions(By by) {
		return getSelectWebElement(by).getOptions();
	}
	
	protected List<WebElement> getDropDownAllSelectedOptions(By by) {
		return getSelectWebElement(by).getAllSelectedOptions();
	}
	
	protected WebElement getDropDownFirstSelection(By by) {
		return getSelectWebElement(by).getFirstSelectedOption();
	}
	
	protected void deselectDropDownValueByVisibleText(By by, String value) {
		getSelectWebElement(by).deselectByVisibleText(value);
	}
	
	protected void deselectDropDownValueByIndex(By by, int index) {
		getSelectWebElement(by).deselectByIndex(index);
	}
	
	protected void deselectDropDownValueByValue(By by, String value) {
		getSelectWebElement(by).deselectByValue(value);
	}
	
	protected void deselectAllDropDownValueByValue(By by) {
		getSelectWebElement(by).deselectAll();
	}
	
	protected boolean isMultipleSelectionSupported(By by) {
		return getSelectWebElement(by).isMultiple();
	}

}
