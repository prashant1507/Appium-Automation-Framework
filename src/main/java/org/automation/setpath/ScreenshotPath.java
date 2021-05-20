package org.automation.setpath;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.automation.appium.AppiumSetup;
import org.automation.appium.CapabilityManager;
import org.automation.constants.GlobalVars;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

/**
 * This class is to set and get screenshot folder path <br>
 * Class is final to avoid extend. <br>
 * <br>
 * Apr 26, 2021
 * 
 * @author User1
 * @version 1.0
 * 
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class ScreenshotPath {

	private static ThreadLocal<String> dir = new ThreadLocal<String>();

	/**
	 * This function clears dir variable for thread safety <br>
	 * Apr 26, 2021
	 *
	 */
	public static void unloadDir() {
		dir.remove();
	}

	/**
	 * This method return current test execution screenshot folder <br>
	 * Apr 26, 2021
	 * @return return directory for current test's screenshot folder
	 */
	public static String getCurrentTestExecutionScreenshotsDir() {
		return dir.get();
	}

	/**
	 * This method sets current test execution screenshot folder <br>
	 * Apr 26, 2021
	 *
	 */
	public static void setCurrentTestExecutionScreenshotsDir() {
		dir.set(GlobalVars.getScreenshotDir() + AppiumSetup.getCapability("name", CapabilityManager.getDeviceUDID()) + "_"
				+ new SimpleDateFormat(GlobalVars.getDateTimeFormat1()).format(new Date()));
	}
}
