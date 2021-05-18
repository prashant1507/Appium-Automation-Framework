package org.automation.dataproviders;

import org.testng.annotations.DataProvider;

/*
 * Using DataProvider. Usage Example is in LoginPageTests.class -> verifyInvalidLogin
 */
public final class DataProviders {

	@DataProvider(name = "InvalidLoginCreds", parallel = true)
	public Object[][] getInvalidCreds() {
		return new Object[][] { { "user1", "user1" }, { "user2", "user2" }, { "user3", "user3" } };
	}

	@DataProvider(name = "ValidLoginCreds", parallel = true)
	public String[][] getValidCreds() {
		return new String[][] { { "Admin", "admin123" } };
	}
}
