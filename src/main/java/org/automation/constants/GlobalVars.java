package org.automation.constants;

import lombok.Getter;
import lombok.experimental.UtilityClass;

import java.util.HashMap;

/**
 * FrameworkConstants stores all the framework related constants.
 * <br>
 * Class is to avoid extend.
 * <br><br>
 * Apr 8, 2021
 * @author User1
 * @version 1.0
 *
 */

@UtilityClass
public class GlobalVars {

	@Getter final String userDir = System.getProperty("user.dir");
	@Getter final String osPlatform = System.getProperty("os.name");

	@Getter final String resPath = userDir.concat("/src/test/resources/");
	@Getter final String reportDir = userDir.concat("/test-reports");
	@Getter final String videoDir = userDir.concat("/test-report-videos");
	@Getter final String screenshotDir = userDir.concat("/test-screenshots/");

	@Getter final String configProp = resPath.concat("config/Config.properties");
	@Getter final String configJson = resPath.concat("config/FrameworkConfig.json");
	@Getter final String dataFile = resPath.concat("data/Data.xlsx");
	@Getter final String capabilityDir = resPath.concat("capabilities/");
	@Getter final String logsDir = resPath.concat("logs/");
	@Getter final String capabilityTemplate = resPath.concat("capabilityTemplate.json");

	@Getter final String dateTimeFormat1 = "dd_MM_yyyy_hh_mm_ss_SSS";
	@Getter final String dateTimeFormat2 = "dd/MM/yyyy HH:mm:ss";
	@Getter final String reportName = "QA Test Report";
	@Getter final String reportTitle = "Test Report";
	@Getter final int explicitWait = 10;
	@Getter final int maxRetryCounter = 2;
	@Getter final String yes = "yes";
	@Getter final String no = "no";
	@Getter final String executePermission = "chmod u+x ";

	@Getter final String grid = "grid";
	@Getter final String local = "local";
	@Getter final String selenoid = "selenoid";
	@Getter final String zalenium = "zalenium";
	@Getter HashMap<String, Boolean> connectedDevices = new HashMap<>();
}
