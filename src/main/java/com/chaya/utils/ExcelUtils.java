package com.chaya.utils;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileInputStream;

public class ExcelUtils {

    public static Object[][] getData(String filePath, String sheetName) {
        try (FileInputStream fis = new FileInputStream(filePath);
             Workbook workbook = new XSSFWorkbook(fis)) {

            Sheet sheet = workbook.getSheet(sheetName);
            if (sheet == null) {     // clear message instead of NullPointerException
                throw new RuntimeException("Sheet '" + sheetName + "' not found in " + filePath);
            }
            int rows = sheet.getLastRowNum();              // data rows (header is row 0)
            int cols = sheet.getRow(0).getLastCellNum();   // number of columns
            Object[][] data = new Object[rows][cols];
            DataFormatter formatter = new DataFormatter();

            for (int i = 1; i <= rows; i++) {
                Row row = sheet.getRow(i);
                for (int j = 0; j < cols; j++) {
                    Cell cell = row.getCell(j, Row.MissingCellPolicy.CREATE_NULL_AS_BLANK);
                    data[i - 1][j] = formatter.formatCellValue(cell);
                }
            }
            return data;
        } catch (Exception e) {
            throw new RuntimeException("Could not read Excel: " + filePath, e);
        }
    }
}