package org.automation.listeners;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import org.testng.IAnnotationTransformer;
import org.testng.annotations.ITestAnnotation;

/**
 * 
 * Listener for  annotations to remove all the annotation mentioned for @Test
 * <br>Remember: If all @Test has same annotation then its very helpful
 * <br>
 * Class is final to avoid extend.
 * <br><br>
 * Apr 8, 2021
 * @author User1
 * @version 1.0
 *
 */

public class AnnotationTransformer implements IAnnotationTransformer {

	@Override
	public void transform(ITestAnnotation annotation, Class testClass, Constructor testConstructor, Method testMethod) {
		// @Test(testName = "Login page verification", dataProvider =
		// "InvalidLoginCreds", dataProviderClass = DataProviders.class, description =
		// "Verify if login page is displayed", retryAnalyzer = RetryFailedTests.class)
		annotation.setDataProvider("InvalidLoginCreds");
		annotation.setTestName("Login page verification");
		annotation.setDescription("Verify if login page is displayed");
		// annotation.setDataProviderClass(DataProviders.class);
		annotation.setRetryAnalyzer(RetryFailedTests.class);
	}

}
