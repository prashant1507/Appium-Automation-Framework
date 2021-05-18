package org.automation.setpath;

import java.text.SimpleDateFormat;
import java.util.Date;
import org.automation.constants.GlobalVars;
import org.automation.enums.ConfigMap;
import org.automation.utils.PropertyUtils;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class ReportPath {

	private static String reportFilePath = "";

	public static String getReportPath() {
		if (reportFilePath.isEmpty()) {
			reportFilePath = setReportPath();
		}
		return reportFilePath;
	}

	private static String setReportPath() {
		if (PropertyUtils.get(ConfigMap.OVERRIDEREPORTS).equalsIgnoreCase(GlobalVars.getNo())) {
			return GlobalVars.getReportDir() + "/ExecutionReport_"
					+ new SimpleDateFormat(GlobalVars.getDateTimeFormat1()).format(new Date()) + ".html";
		} else {
			return GlobalVars.getReportDir().concat("/index.html");
		}
	}
}