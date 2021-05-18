package org.automation.testrecorder;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
//import java.util.Arrays;
//import java.util.Comparator;
import javax.imageio.ImageIO;
import org.apache.commons.io.FileUtils;
import org.automation.setpath.ScreenshotPath;
import org.automation.setpath.VideoPath;
import org.jcodec.api.awt.AWTSequenceEncoder;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

/**
 * This class will create video using images taken during the test
 * execution.<br>
 * Class is final to avoid extend. <br>
 * <br>
 * Apr 26, 2021
 * 
 * @author User1
 * @version 1.0
 * 
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class TestRecording {

	/**
	 * This method creates a video from collection of images <br>
	 * Apr 26, 2021
	 * @return videoPath path of the video
	 */
	public static String getRecording() {
		String videoPath = VideoPath.getVideoPath();
		String screenshotsDir = ScreenshotPath.getCurrentTestExecutionScreenshotsDir();
		try {
			AWTSequenceEncoder awtEncoder = AWTSequenceEncoder.createSequenceEncoder(new File(videoPath), 1);
			File[] screenshots = new File(screenshotsDir).listFiles();
			// Arrays.sort(screenshots, Comparator.comparingLong(File::lastModified));
			for (File screenshot : screenshots) {
				BufferedImage image = ImageIO.read(screenshot.getAbsoluteFile());
				awtEncoder.encodeImage(image);
			}
			awtEncoder.finish();
			FileUtils.deleteDirectory(new File(screenshotsDir));
		} catch (IOException e) {
			e.printStackTrace();
		}
		return videoPath;
	}
}
