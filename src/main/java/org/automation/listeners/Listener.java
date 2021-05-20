package org.automation.listeners;

import java.io.File;
import java.util.Arrays;
import java.util.Base64;

import org.automation.appium.AppiumSetup;
import org.automation.appium.CapabilityManager;
import org.automation.appium.CapabilityFileGeneration;
import org.automation.constants.GlobalVars;
import org.automation.elk.ELKUtils;
import org.automation.enums.ConfigMap;
import org.automation.reports.ExtentLogger;
import org.automation.reports.ExtentManager;
import org.automation.reports.ExtentReport;
import org.automation.testrecorder.TestRecording;
import org.automation.utils.PropertyUtils;
import org.automation.utils.FileSystemHandler;
import org.automation.utils.UserInputCheck;
import org.testng.ISuite;
import org.testng.ISuiteListener;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;
import org.testng.annotations.Test;
import org.testng.xml.XmlSuite;

/**
 * To set various methods for handling test cases
 * 
 * <br>
 * Class is final to avoid extend. <br>
 * <br>
 * Apr 8, 2021
 * 
 * @author User1
 * @version 1.0
 *
 */
public class Listener implements ITestListener, ISuiteListener {

	/**
	 * 
	 * Checks if the password for email is in Base64encoded <br>
	 * Apr 8, 2021
	 */
	public void onStart(ISuite suite) {
		// Before suite
		XmlSuite a = suite.getXmlSuite();
		int z = a.getThreadCount();

		try {
			if (PropertyUtils.get(ConfigMap.SENDMAILAFTEREXECUTION)
					.equalsIgnoreCase(GlobalVars.getYes())) {
				Base64.getDecoder().decode(PropertyUtils.get(ConfigMap.EMAILPASSWORD));
			}
		} catch (Exception e) {
			UserInputCheck.designerOutputForPasswordError();
			System.exit(1);
		}
		FileSystemHandler.deleteOldReports();
		FileSystemHandler.deleteDir(GlobalVars.getScreenshotDir());
		ExtentReport.initReports();
		CapabilityFileGeneration.generateCapabilityFile();
	}

	public void onFinish(ISuite suite) {
		ExtentReport.flushReport();
		UserInputCheck.sendTestReportOnEmail();
		CapabilityManager.unloadCapabilityFile();
	}

	public void onTestStart(ITestResult result) {
		ExtentReport.createTests(result.getMethod().getDescription());
		FileSystemHandler.createRequiredDirs();
		ExtentLogger.info(PropertyUtils.get(ConfigMap.URLFORENV));
		ExtentManager.getExtentTest().assignDevice((String) AppiumSetup.getCapability("name", CapabilityManager.getDeviceUDID()));
	}

	public void onTestSuccess(ITestResult result) {
		ExtentLogger.pass("Testcase: "
				+ (result.getMethod().getConstructorOrMethod().getMethod().getAnnotation(Test.class).testName())
				+ "<br/>" + (result.getClass()).toString().replace("class", "Class: ") + "<br/>" + "Method: "
				+ result.getMethod().getMethodName() + "<br/> Status: Pass");
		ELKUtils.sendDetailsToELK(
				result.getMethod().getConstructorOrMethod().getMethod().getAnnotation(Test.class).testName(), "Pass");
	}

	/** 
	 * Record video in case of test fail. <br>
	 * Apr 8, 2021
	 */
	public void onTestFailure(ITestResult result) {
		ExtentLogger.fail(result.getThrowable().toString() + "<br/><br/>"
				+ Arrays.toString(result.getThrowable().getStackTrace()));
		ExtentLogger.fail("Testcase: "
				+ (result.getMethod().getConstructorOrMethod().getMethod().getAnnotation(Test.class).testName())
				+ "<br/>" + (result.getClass()).toString().replace("class", "Class: ") + "<br/>" + "Method: "
				+ result.getMethod().getMethodName() + "<br/> Status: Fail<br/>");

		ExtentLogger.info("<video width='620' height='340' controls> <source src='" + TestRecording.getRecording()
				+ "' type='video/mp4'> <videos>");
		ELKUtils.sendDetailsToELK(
				result.getMethod().getConstructorOrMethod().getMethod().getAnnotation(Test.class).testName(), "Fail");
	}

	public void onTestSkipped(ITestResult result) {
		ExtentLogger.skip("Testcase: "
				+ (result.getMethod().getConstructorOrMethod().getMethod().getAnnotation(Test.class).testName())
				+ "<br/>" + (result.getClass()).toString().replace("class", "Class: ") + "<br/>" + "Method: "
				+ result.getMethod().getMethodName() + "<br/> Status: Skip");
		ExtentLogger.info("<video width='620' height='340' controls> <source src='" + TestRecording.getRecording()
				+ "' type='video/mp4'> <videos>");
		ELKUtils.sendDetailsToELK(
				result.getMethod().getConstructorOrMethod().getMethod().getAnnotation(Test.class).testName(), "Skip");
	}

	public void onTestFailedButWithinSuccessPercentage(ITestResult result) {
		/*
		 * For future implementation
		 */
	}

	public void onStart(ITestContext context) {
		// Will run before <test> in testng.xml
	}

	public void onFinish(ITestContext context) {
		/*
		 * Invoked after all the tests have run and all their Configuration methods have
		 * been called. Currently, there is only one test
		 */
	}

}