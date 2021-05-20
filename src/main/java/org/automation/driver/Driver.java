package org.automation.driver;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.automation.appium.AppiumSetup;
import org.automation.appium.ServiceManager;
import org.openqa.selenium.remote.DesiredCapabilities;

/**
 * 
 * Setup the web driver and removes it at the end of execution. <br>
 * Class is final to avoid extend. <br>
 * <br>
 * Apr 8, 2021
 * 
 * @author User1
 * @version 1.0
 *
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class Driver {
	
	/**
	 * Setup the web driver for browser <br>
	 * Apr 8, 2021
	 *
	 */
	public static void initDriver() {

		if (Objects.isNull(DriverManager.getDriver())) {
			try {
				DesiredCapabilities cap = new DesiredCapabilities(AppiumSetup.startAppiumServer());
				URL url = new URL(ServiceManager.getService().getUrl().toString());
				AppiumDriver<MobileElement> driver = new AppiumDriver<>(url, cap);
				DriverManager.setDriver(driver);
			} catch (MalformedURLException e) {
				e.printStackTrace();
			}
			DriverManager.getDriver().manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
		}
	}

	/**
	 * Removes the web driver at the end of execution <br>
	 * Apr 8, 2021
	 *
	 */
	public static void quiteDriver() {
		if (Objects.nonNull(DriverManager.getDriver())) {
			DriverManager.getDriver().quit();
			DriverManager.unload();
		}
	}
}
