package utils;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;

public class ExcelUtil {

	private static final String TestData = "./src/test/resources/testdata/OpenCartTestData.xlsx";
	private static Workbook book;
	private static Sheet sheet;

	
	public static Object[][] getTestData(String sheetName) {
		Object data[][] = null;
		try {
			FileInputStream ip = new FileInputStream(TestData);
			book = WorkbookFactory.create(ip);
			sheet = book.getSheet(sheetName);

			data = new Object[sheet.getLastRowNum()][sheet.getRow(0).getLastCellNum()];

			for (int i = 0; i < sheet.getLastRowNum(); i++) {
				for (int j = 0; j < sheet.getRow(0).getLastCellNum(); j++) {
					data[i][j] = sheet.getRow(i + 1).getCell(j).toString();
				}
			}

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (InvalidFormatException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		return data;

	}
	
	
/*
	public static Object[][] testData(String sheetName) {

		Object data[][] = null;
		DataFormatter formatter = new DataFormatter();
		try {
			FileInputStream fis = new FileInputStream(TestData);
			book = WorkbookFactory.create(fis);
			sheet = book.getSheet(sheetName);

			// [row]-- get the LastRow [column]-- get the first row and the last column

			data = new Object[sheet.getLastRowNum()][sheet.getRow(0).getLastCellNum()];

			for (int i = 0; i < sheet.getLastRowNum(); i++) {
				for (int j = 0; j < sheet.getRow(0).getLastCellNum(); j++) {
					if (sheet.getRow(i + 1) == null || sheet.getRow(i + 1).getCell(j) == null) {
						data[i][j] = "";
					} else {
						data[i][j] = formatter.formatCellValue(sheet.getRow(i + 1).getCell(j));
					}
//					data[i][j] = sheet.getRow(i + 1).getCell(j).toString();
				}
			}

			// ✅ Defensive check
			if (data.length == 0 || data[0].length == 0) {
				throw new RuntimeException("No data found in Excel file!");
			}

		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InvalidFormatException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return data;
	}
*/
}
