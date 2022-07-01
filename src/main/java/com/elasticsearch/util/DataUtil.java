package com.elasticsearch.util;

import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.elasticsearch.model.Product;

public class DataUtil {

	public static List<Product> getExcelData() throws Exception {
		List<Product> productList = new ArrayList();
		String fileLocation = "C:\\Springwork\\Products.xlsx";
		FileInputStream file = new FileInputStream(new File(fileLocation));
		Workbook workbook = new XSSFWorkbook(file);
		Sheet sheet = workbook.getSheetAt(0);

		Map<Integer, List<String>> data = new HashMap<>();
		int i = 0;
		for (Row row : sheet) {
			data.put(i, new ArrayList<String>());//
			Product item = new Product();
			Cell c=row.getCell(0);
			CellType t=row.getCell(0).getCellType();
			item.setId(getCellValue(row.getCell(0)));
			item.setName(getCellValue(row.getCell(1)));
			item.setCount(new Double(getCellValue(row.getCell(2))));
			

			productList.add(item);
			i++;
		}
		return productList;
	}

	public static String getCellValue(Cell c) {
		String cellValue = "";
		if (c.getCellType() == CellType.STRING) {
			cellValue = c.getStringCellValue();
		} else if (c.getCellType() == CellType.NUMERIC) {
			cellValue = new Double(c.getNumericCellValue()).toString();
		}
		return cellValue;
	}

}
