package org.automation.elk;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import org.automation.constants.GlobalVars;
import org.automation.enums.ConfigMap;
import org.automation.utils.PropertyUtils;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import static io.restassured.RestAssured.*;

/**
 * This class is to display data in real time dashboard
 * <br>
 * Class is final to avoid extend.
 * <br><br>
 * Apr 9, 2021
 * @author User1
 * @version 1.0
 * 
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class ELKUtils {
	
	/**
	 * This method sends details of test execution to ELK
	 * <br>
	 * Apr 9, 2021
	 * @param testName name of the test case
	 * @param testStatus test case status
	 *
	 */
	public static void sendDetailsToELK(String testName, String testStatus) {
		if (PropertyUtils.get(ConfigMap.USEELK).equalsIgnoreCase(GlobalVars.getYes())) {
			Map<String, String> map = new HashMap<String, String>();
			map.put("TestName", testName);
			map.put("Status", testStatus);
			map.put("ExecutionTime", LocalDateTime.now().toString());
			
			given().header("Content-Type", "application/json")
					.when()
					.body(map)
					.post(PropertyUtils.get(ConfigMap.ELKSUITEURL));
			
		}
	}
}
