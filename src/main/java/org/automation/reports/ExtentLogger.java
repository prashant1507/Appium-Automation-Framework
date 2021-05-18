package org.automation.reports;

import org.automation.constants.GlobalVars;
import org.automation.enums.ConfigMap;
import org.automation.utils.PropertyUtils;
import org.automation.utils.ScreenshotUtils;
import com.aventstack.extentreports.MediaEntityBuilder;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

/**
 * This class adds the status of test step. <br>
 * Creating screenshot and attaching to report in Base64Encoding.
 *
 * <br>
 * Class is final to avoid extend. <br>
 * <br>
 * Apr 8, 2021
 * 
 * @author User1
 * @version 1.0
 *
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class ExtentLogger {

	public static void pass(String msg) {
		if (PropertyUtils.get(ConfigMap.PASSEDSCREENSHOT).equalsIgnoreCase(GlobalVars.getYes()))
			ExtentManager.getExtentTest().pass(msg,
					MediaEntityBuilder.createScreenCaptureFromBase64String(ScreenshotUtils.getBase64Image()).build());
		else
			ExtentManager.getExtentTest().pass(msg);
	}

	public static void fail(String msg) {
		ExtentManager.getExtentTest().fail(msg, MediaEntityBuilder.createScreenCaptureFromBase64String(ScreenshotUtils.getBase64Image()).build());
	}

	public static void skip(String msg) {
		ExtentManager.getExtentTest().skip(msg, MediaEntityBuilder.createScreenCaptureFromBase64String(ScreenshotUtils.getBase64Image()).build());
	}
	
	public static void info(String msg) {
		ExtentManager.getExtentTest().info(msg);
	}
}
