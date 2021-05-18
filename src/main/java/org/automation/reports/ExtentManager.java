package org.automation.reports;

import java.util.Objects;

import com.aventstack.extentreports.ExtentTest;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

/**
 * To set, get and remove ExtentTest and manage Thread safety.
 * 
 * <br>
 * Class is final to avoid extend.
 * <br><br>
 * Apr 8, 2021
 * @author User1
 * @version 1.0
 *
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class ExtentManager {

	private static ThreadLocal<ExtentTest> et = new ThreadLocal<ExtentTest>();

	/**
	 * Get ExtentTest instance.
	 * <br>
	 * Apr 8, 2021
	 * @return ExtentTest
	 *
	 */
	public static ExtentTest getExtentTest() {
		return et.get();
	}
	
	/**
	 * Set ExtentTest instance
	 * <br>
	 * Apr 8, 2021
	 * @param extentTestRef ExtentTest reference variable.
	 *
	 */
	static void setExtentTest(ExtentTest extentTestRef) {
		if (Objects.nonNull(extentTestRef))
			et.set(extentTestRef);
	}

	/**
	 * Removes the ExtentTest instance.
	 * <br>
	 * Apr 8, 2021
	 *
	 */
	static void unload() {
		et.remove();
	}
}
