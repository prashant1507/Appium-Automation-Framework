package org.automation.tests;

import org.automation.appium.AppiumServerSetup;
import org.automation.appium.CapabilityManager;
import org.automation.appium.GenerateCapabilityFile;
import org.automation.appium.ServiceManager;
import org.automation.driver.Driver;
import org.automation.setpath.ScreenshotPath;
import org.automation.setpath.VideoPath;
import org.testng.ITestContext;
import org.testng.annotations.*;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

/*
 * Before and After methods
 */
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class BaseSetup {

	@BeforeMethod
	protected void setUp(ITestContext context) {
		try {
			Driver.initDriver();
			ScreenshotPath.setCurrentTestExecutionScreenshotsDir();
			VideoPath.setVideoPath();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@AfterMethod
	protected void tearDown() {
		Driver.quiteDriver();
		ServiceManager.getService().stop();
		ServiceManager.unloadService();
		CapabilityManager.unloadDeviceUDID();
		ScreenshotPath.unloadDir();
		VideoPath.flushVideoPath();
	}
}