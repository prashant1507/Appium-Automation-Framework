package org.automation.utils;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import org.apache.commons.collections4.map.HashedMap;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

/**
 * 
 * Utility class to read from excel file. This class store a map of rows and
 * columns combination.<br>
 * For e.g.,<br>
 * - firstRow + secondRow<br>
 * - firstRow + thirdRow<br>
 * and so on. Considering firstRow as key and other rows as values <br>
 * Class is final to avoid extend. <br>
 * <br>
 * Apr 7, 2021
 * @author User1
 * @version 1.0
 *
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class ExcelUtils {

	/**
	 * To read data from excel file
	 * <br>
	 * Apr 7, 2021
	 * @param excelFilePath Path of the excel file.
	 * @param sheetName Sheet name from where data needs to be extracted.
	 * @return List
	 *
	 */
	public static List<Map<String, String>> getDataDeatils(String excelFilePath, String sheetName) {
		List<Map<String, String>> list = null;
		FileInputStream excelToRead = null;
		XSSFWorkbook workBook;
		try {
			excelToRead = new FileInputStream(excelFilePath);
			workBook = new XSSFWorkbook(excelToRead);
			XSSFSheet sheet = workBook.getSheetAt(0);
			Map<String, String> map = null;
			list = new ArrayList<Map<String, String>>();
			int lastRowNum = sheet.getLastRowNum();
			int lastColNum = sheet.getRow(0).getLastCellNum();
			for (int i = 1; i <= lastRowNum; i++) {
				map = new HashedMap<String, String>();
				for (int j = 0; j < lastColNum; j++) {
					String key = sheet.getRow(0).getCell(j).getStringCellValue();
					String value = sheet.getRow(i).getCell(j).getStringCellValue();
					map.put(key, value);
				}
				list.add(map);
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (Objects.nonNull(excelToRead))
				try {
					excelToRead.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
		}
		return list;
	}
}