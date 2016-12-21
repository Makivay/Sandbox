package ru.Makivay.sandbox.xlsxReport.cellsFiller;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;

class Extractor {

    static Sheet extract(final Workbook workbook, final String sheetName){
        final Sheet sheet = workbook.getSheet(sheetName);
        return sheet != null ? sheet : workbook.createSheet(sheetName);
    }

    static Row extract(final Sheet sheet, final int rowNum){
        final Row row = sheet.getRow(rowNum);
        return row != null ? row : sheet.createRow(rowNum);
    }

    static Cell extract(final Row row, final int cellNum){
        final Cell cell = row.getCell(cellNum);
        return cell != null ? cell : row.createCell(cellNum);
    }
}
