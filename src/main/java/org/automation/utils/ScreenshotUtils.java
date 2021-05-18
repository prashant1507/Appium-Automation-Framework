package org.automation.utils;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.imageio.ImageIO;
import org.automation.constants.GlobalVars;
import org.automation.driver.DriverManager;
import org.automation.setpath.ScreenshotPath;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriverException;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

/**
 * This class is to take screenshot for each test steps
 * <br>
 * Class is final to avoid extend.
 * <br><br>
 * Apr 26, 2021
 * @author User1
 * @version 1.0
 *
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class ScreenshotUtils {
	
	/**
	 * This function will return image as Base64 and will save image as png for creating video.
	 * <br>
	 * Apr 26, 2021
	 * @return Screenshot in Base64
	 */
	public static String getBase64Image() {
		getImageAsFile(ScreenshotPath.getCurrentTestExecutionScreenshotsDir());
		return ((TakesScreenshot) DriverManager.getDriver()).getScreenshotAs(OutputType.BASE64);
	}
	
	/**
	 * This function will save screenshot as png format
	 * <br>
	 * Apr 26, 2021
	 * @param dir folder where screenshots for current test are stored
	 *
	 */
	static void getImageAsFile(String dir) {
		String screenShotName = dir + "/screenshot_" + new SimpleDateFormat(GlobalVars.getDateTimeFormat1()).format(new Date()) + ".png";
		File ts = ((TakesScreenshot) DriverManager.getDriver()).getScreenshotAs(OutputType.FILE);
		try {
			// Setting image dimension as an even value 888 x 1920 (w x h)
			BufferedImage inputImage = ImageIO.read(ts.getAbsoluteFile());
			BufferedImage outputImage = new BufferedImage(1920, 888, inputImage.getType());
			Graphics2D g2d = outputImage.createGraphics();
		    g2d.drawImage(inputImage, 0, 0, 1920, 888, null);
		    g2d.dispose();
		    ImageIO.write(outputImage, "png", new File(screenShotName));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
