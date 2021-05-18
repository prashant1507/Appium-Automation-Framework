package org.automation.setpath;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.automation.constants.GlobalVars;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

/**
 * 
 * <br>
 * Class is final to avoid extend.
 * <br><br>
 * Apr 27, 2021
 * @author User1
 * @version 1.0
 * 
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class VideoPath {
	
	private static ThreadLocal<String> videoFullPath = new ThreadLocal<String>();

	
	public static void setVideoPath() {
		videoFullPath.set(GlobalVars.getVideoDir() + "/" + "ExecutionVideo_" + new SimpleDateFormat(GlobalVars.getDateTimeFormat1()).format(new Date()) + ".mp4");
	}
	
	public static String getVideoPath() {
		return videoFullPath.get();
	}
	
	public static void flushVideoPath() {
		videoFullPath.remove();
	}
}
