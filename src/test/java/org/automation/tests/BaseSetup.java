package org.automation.tests;

import org.automation.appium.AppiumSetup;
import org.automation.appium.CapabilityManager;
import org.automation.appium.ServiceManager;
import org.automation.constants.GlobalVars;
import org.automation.driver.Driver;
import org.automation.enums.ConfigMap;
import org.automation.setpath.ScreenshotPath;
import org.automation.setpath.VideoPath;
import org.automation.utils.PropertyUtils;
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
			CapabilityManager.setDeviceUDID(AppiumSetup.getAvailableDeviceUDID());
			GlobalVars.getConnectedDevices().put(CapabilityManager.getDeviceUDID(), true);
			Driver.initDriver();
			ScreenshotPath.setCurrentTestExecutionScreenshotsDir();
			VideoPath.setVideoPath();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@AfterMethod
	protected void tearDown() {
		GlobalVars.getConnectedDevices().put(CapabilityManager.getDeviceUDID(), false);
		Driver.quiteDriver();
		if (PropertyUtils.get(ConfigMap.RUNMODE).equalsIgnoreCase(GlobalVars.getLocal())) {
			ServiceManager.getService().stop();
		}
		ServiceManager.unloadService();
		CapabilityManager.unloadDeviceUDID();
		ScreenshotPath.unloadDir();
		VideoPath.flushVideoPath();
	}
}