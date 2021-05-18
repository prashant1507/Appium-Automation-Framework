package org.automation.utils;

import java.io.File;
import java.io.IOException;

import org.apache.commons.io.FileUtils;
import org.automation.constants.GlobalVars;
import org.automation.enums.ConfigMap;
import org.automation.setpath.ScreenshotPath;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

/**
 * 
 * RemoveOldReports removes all the reports older than X days. User can set the
 * decision to delete the files in properties file. <br>
 * Class is final to avoid extend. <br>
 * <br>
 * Apr 7, 2021
 * 
 * @author User1
 * @version 1.0
 * 
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class FileSystemHandler {

	/**
	 * This method deletes all the X days older reports and videos. <br>
	 * Apr 7, 2021
	 *
	 */
	public static void deleteOldReports() {
		File targetDirForReport = new File(GlobalVars.getReportDir());
		File targetDirForVideos = new File(GlobalVars.getVideoDir());
		if (PropertyUtils.get(ConfigMap.DELETEOLDREPORTS).equals(GlobalVars.getYes())) {
			if (targetDirForReport.exists()) {
				deleteOldFiles(targetDirForReport.listFiles());
			}
			if (targetDirForVideos.exists()) {
				deleteOldFiles(targetDirForVideos.listFiles());
			}
		}
	}
	
	/**
	 * This method deletes all the X days older reports and videos.<br>
	 * Apr 22, 2021
	 * @param filesToDelete Path for files to be deleted
	 *
	 */
	private static void deleteOldFiles(File[] filesToDelete) {
		int daysBack = Integer.parseInt(PropertyUtils.get(ConfigMap.NUMBEROFDAYS));
		long purgeTime = System.currentTimeMillis() - ((long) daysBack * 24 * 60 * 60 * 1000);
		for (File report : filesToDelete) {
			if (report.lastModified() < purgeTime) {
				report.delete();
			}
		}
	}
	
	/**
	 * This function will clean old data from screenshots folder
	 * <br>
	 * Apr 26, 2021
	 * @param path Directory path to be deleted
	 */
	public static void deleteDir(String path) {
		try {
			if (new File(path).exists())
				FileUtils.deleteDirectory(new File(path));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static void createDir(String path) {
		try {
			if (!new File(path).exists())
				FileUtils.forceMkdir(new File(path));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static void copyFile(String from, String to) {
		try {
			if (new File(from).exists())
				FileUtils.copyFile(new File(from), new File(to));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * This method will create the directories required by framework <br>
	 * Apr 26, 2021
	 *
	 */
	public static void createRequiredDirs() {
		String[] dirsToCreate = { GlobalVars.getVideoDir(), ScreenshotPath.getCurrentTestExecutionScreenshotsDir()};
		for (int i = 0; i < dirsToCreate.length; i++) {
			File dir = new File(dirsToCreate[i]);
			if (!dir.exists()) {
				dir.mkdirs();
			}
		}
	}
}