package org.automation.reports;

import java.awt.Desktop;
import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Objects;
import org.automation.constants.GlobalVars;
import org.automation.enums.ConfigMap;
import org.automation.setpath.ReportPath;
import org.automation.utils.PropertyUtils;

import com.aventstack.extentreports.AnalysisStrategy;
import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.aventstack.extentreports.reporter.JsonFormatter;
import com.aventstack.extentreports.reporter.configuration.Theme;
import com.aventstack.extentreports.reporter.configuration.ViewName;

/**
 * Preparing and setting up Extent report instances.
 * 
 * <br>
 * Class is final to avoid extend.
 * <br><br>
 * Apr 8, 2021
 * @author User1
 * @version 1.0
 *
 */
public final class ExtentReport {

	/**
	 * 
	 * Private constructor to avoid external instantiation
	 * <br>
	 * Apr 8, 2021
	 */
	private ExtentReport() {
	}

	private static ExtentReports extentReports;

	/**
	 * Setting up the test report information.
	 * <br>
	 * Apr 8, 2021
	 *
	 */
	public static void initReports() {
		if (Objects.isNull(extentReports)) {
			// Setting the view order for the tabs in report
			ExtentSparkReporter spark = new ExtentSparkReporter(ReportPath.getReportPath()).viewConfigurer()
					.viewOrder().as(new ViewName[] { ViewName.DASHBOARD, ViewName.TEST, ViewName.CATEGORY,
							ViewName.DEVICE, ViewName.AUTHOR })
					.apply();

			extentReports = new ExtentReports();
			spark.config().setTheme(Theme.DARK);
			spark.config().setDocumentTitle(GlobalVars.getReportTitle());
			spark.config().setReportName(GlobalVars.getReportName());
			spark.config().setEncoding(String.valueOf(StandardCharsets.UTF_8));
			spark.config().setTimeStampFormat(GlobalVars.getDateTimeFormat2());
			// spark.config().setOfflineMode(true);

			// Setting up information
			try {
				extentReports.setSystemInfo("Name", PropertyUtils.get(ConfigMap.TESTERNAME));
				extentReports.setSystemInfo("Environment", PropertyUtils.get(ConfigMap.ENVIRONMENT));
				extentReports.setSystemInfo("OS", "Android");
				extentReports.setSystemInfo("App", PropertyUtils.get(ConfigMap.APPNAME));
				extentReports.setSystemInfo("App Package", PropertyUtils.get(ConfigMap.APPPACKAGE));
				extentReports.setSystemInfo("App Activity", PropertyUtils.get(ConfigMap.APPACTIVITY));
				extentReports.setSystemInfo("URL", PropertyUtils.get(ConfigMap.URLFORENV));
				extentReports.setSystemInfo("Report Path", ReportPath.getReportPath());
				extentReports.setAnalysisStrategy(AnalysisStrategy.TEST);
				if (PropertyUtils.get(ConfigMap.OVERRIDEREPORTS).equalsIgnoreCase(GlobalVars.getYes())) {
					JsonFormatter json = new JsonFormatter("old-report-data.json");
					extentReports.createDomainFromJsonArchive("old-report-data.json");
					extentReports.attachReporter(json, spark);
				} else
					extentReports.attachReporter(spark);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * Removing the ExtentReport instance.
	 * <br>
	 * Apr 8, 2021
	 *
	 */
	public static void flushReport() {
		if (Objects.nonNull(extentReports)) {
			extentReports.flush();
			try {
				ExtentManager.unload();
				// To open the report automatically in default browser
				Desktop.getDesktop().browse(new File(ReportPath.getReportPath()).toURI());
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * Setting up the ExtentTest instance.
	 * <br>
	 * Apr 8, 2021
	 * @param testCaseName current test case name.
	 *
	 */
	public static void createTests(String testCaseName) {
		ExtentTest extentTest = null;
		try {
			extentTest = extentReports.createTest(testCaseName)
					.assignAuthor(PropertyUtils.get(ConfigMap.TESTERNAME))
					.assignCategory(PropertyUtils.get(ConfigMap.ENVIRONMENT));
		} catch (Exception e) {
			e.printStackTrace();
		}
		ExtentManager.setExtentTest(extentTest);
	}
}
