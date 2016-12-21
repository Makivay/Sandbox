package ru.Makivay.sandbox.xlsxReport;

import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.List;

public class XLSXReport {

    private final static String FIRST_SHEET_NAME = "Report Options";

    private final XSSFWorkbook workbook;
    private final ReportData reportData;
    private DateFormat dateFormat;

    public XLSXReport(final ReportData reportData) {
        this.reportData = reportData;
        this.workbook = new XSSFWorkbook();
        this.dateFormat = new SimpleDateFormat();
    }

    public XSSFWorkbook generate() {
        addFirstSheet();
        return workbook;
    }

    private void addFirstSheet() {
        final XSSFSheet sheet = workbook.createSheet(FIRST_SHEET_NAME);
        workbook.setSheetOrder(FIRST_SHEET_NAME, 0);
        int rowNumber = 0;
        createCells(sheet.createRow(rowNumber++), reportData.getCompanyName());
        createCells(sheet.createRow(rowNumber++), "As of", dateFormat.format(reportData.getDate()));
        createCells(sheet.createRow(rowNumber++), "Requested By", reportData.getRequestBy());
        createCells(sheet.createRow(rowNumber++), "For Client", reportData.getClientName());
        createCells(sheet.createRow(rowNumber++));

        final List<String> options = reportData.getOptions();
        createCells(sheet.createRow(rowNumber++), "Options", options.size() > 0 ? options.get(0) : "");
        if(options.size() > 1){
            for (int i = 1; i < options.size(); i++) {
                createCells(sheet.createRow(rowNumber++), "", options.get(i));
            }
        }
    }

    private XSSFRow createCells(final XSSFRow row, final String... o) {
        for (int cellNumber = 0; cellNumber < o.length; cellNumber++) {
            row.createCell(cellNumber).setCellValue(o[cellNumber]);
        }
        return row;
    }

    public DateFormat getDateFormat() {
        return dateFormat;
    }

    public void setDateFormat(DateFormat dateFormat) {
        this.dateFormat = dateFormat;
    }
}
