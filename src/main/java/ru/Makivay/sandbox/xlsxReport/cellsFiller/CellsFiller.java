package ru.Makivay.sandbox.xlsxReport.cellsFiller;

import com.google.common.collect.Table;
import org.apache.poi.ss.usermodel.*;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;

public class CellsFiller {
    private final Sheet sheet;
    private DateFormat dateFormat = new SimpleDateFormat();

    public CellsFiller(Sheet sheet) {
        this.sheet = sheet;
    }

    public CellsFiller(final Workbook workbook, final String sheetName) {
        this.sheet = Extractor.extract(workbook, sheetName);
    }

        public <V> Stylist fillTable(final int rowNumber, final int columnNumber, Collection<Collection<V>> table) {
            int startRowNumber = rowNumber;
            Row row;
            for (Collection<V> rowValues : table) {
                row = Extractor.extract(sheet, startRowNumber++);
                fillRow(row, columnNumber, rowValues);
            }
            return Stylists.create(this, sheet, rowNumber, columnNumber, table.size(), table.stream().mapToInt(Collection::size).max().orElse(0));
        }

        public <R, C, V> Stylist fillTable(final int rowNumber, final int columnNumber, Table<R, C, V> table) {
        int startRowNumber = rowNumber;
        Row row;
        for (R rowKey : table.rowKeySet()) {
            row = Extractor.extract(sheet, startRowNumber++);
            fillRow(row, columnNumber, table.row(rowKey).values());
        }
        return Stylists.create(this, sheet, rowNumber, columnNumber, table.rowKeySet().size(), table.columnKeySet().size());
    }

    public <V> Stylist fillRow(final int rowNumber, Collection<V> values) throws RuntimeException {
        final Row row = Extractor.extract(sheet, rowNumber);
        return fillRow(row, 0, values);
    }

    public <V> Stylist fillRow(final int rowNumber, final int columnNumber, Collection<V> values) throws RuntimeException {
        final Row row = Extractor.extract(sheet, rowNumber);
        return fillRow(row, columnNumber, values);
    }

    public <V> Stylist fillRow(final Row row, final int columnNumber, Collection<V> values) throws RuntimeException {
        int position = columnNumber;
        for (Object value : values) {
            fillCell(Extractor.extract(row, position++), value);
        }
        return Stylists.create(this, row, columnNumber, values.size());
    }

    public <V> Stylist fillCell(final int rowNumber, final int columnNumber, final V value) throws RuntimeException {
        final Row row = Extractor.extract(sheet, rowNumber);
        final Cell cell = Extractor.extract(row, columnNumber);
        return fillCell(cell, value);
    }

    public <V> Stylist fillCell(final Cell cell, V value) throws RuntimeException {
        if (value instanceof String) {
            cell.setCellValue((String) value);
        } else if (value instanceof Date) {
            cell.setCellValue(dateFormat.format((Date) value));
        } else if (value instanceof Integer) {
            cell.setCellValue((Integer) value);
        } else if (value instanceof Calendar) {
            cell.setCellValue((Calendar) value);
        } else if (value instanceof RichTextString) {
            cell.setCellValue((RichTextString) value);
        } else {
            throw new RuntimeException("Data type " + value.getClass().getName() + " unsupported");
        }
        return Stylists.create(this, cell);
    }

    public DateFormat getDateFormat() {
        return dateFormat;
    }

    public void setDateFormat(DateFormat dateFormat) {
        this.dateFormat = dateFormat;
    }
}
