package org.automation.utils;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Properties;
import org.automation.constants.GlobalVars;
import org.automation.enums.ConfigMap;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

/**
 * Reading property from properties file.
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
public final class PropertyUtils {

	private static Properties property = new Properties();
	private static final Map<String, String> CONFIGMAP = new HashMap<String, String>();

	static {
		try {
			FileInputStream fis = new FileInputStream(GlobalVars.getConfigProp());
			property.load(fis);
			for (Map.Entry<Object, Object> entry : property.entrySet()) {
				CONFIGMAP.put(String.valueOf(entry.getKey()), String.valueOf(entry.getValue()).trim());
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}
	
	public static String get(ConfigMap key) {
		if (Objects.isNull(key) || Objects.isNull(CONFIGMAP.get(key.name().toLowerCase()))) {
			try {
				throw new Exception("Property name '" + key.toString().toLowerCase() + "' not found. Please check FrameworkConfig.properties");
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return CONFIGMAP.get(key.name().trim().toLowerCase());
	}
}
