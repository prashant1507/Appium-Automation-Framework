package org.automation.listeners;

import org.automation.constants.GlobalVars;
import org.automation.enums.ConfigMap;
import org.automation.utils.PropertyUtils;
import org.testng.IRetryAnalyzer;
import org.testng.ITestResult;

/**
 * Listener class to re-run failed test case
 * 
 * <br>
 * Class is final to avoid extend.
 * <br><br>
 * Apr 8, 2021
 * @author User1
 * @version 1.0
 *
 */
public class RetryFailedTests implements IRetryAnalyzer {

	private int count = 0;
	private final int retries = GlobalVars.getMaxRetryCounter();

	@Override
	public boolean retry(ITestResult result) {
		boolean value = false;
		if (PropertyUtils.get(ConfigMap.RETRYFAILEDTESTCASES).equalsIgnoreCase(GlobalVars.getYes())) {
			value = count < retries;
			count++;
		}
		return value;
	}
}
