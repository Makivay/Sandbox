package ru.Makivay.sandbox.xlsxReport.cellsFiller;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;

class Stylists {

    static Stylist create(CellsFiller cellsFiller, Cell cell){
        return new CellStylist(cellsFiller, cell);
    }

    static Stylist create(CellsFiller cellsFiller, Row row, int startColumnPosition, int length) {
        return new RowStylist(cellsFiller, row, startColumnPosition, length);
    }

    static Stylist create(CellsFiller cellsFiller, Sheet sheet, int startRowPosition, int startColumnPosition, int rowLength, int columnLength) {
        return new TableStylist(cellsFiller, sheet, startRowPosition, startColumnPosition, rowLength, columnLength);
    }

    private static class CellStylist implements Stylist{
        private final CellsFiller cellsFiller;
        private final Cell cell;

        private CellStylist(CellsFiller cellsFiller, Cell cell) {
            this.cellsFiller = cellsFiller;
            this.cell = cell;
        }

        @Override
        public CellsFiller decorate(CellStyle cellStyle) {
            cell.setCellStyle(cellStyle);
            return cellsFiller;
        }
    }

    private static class RowStylist implements Stylist{
        private final CellsFiller cellsFiller;
        private final Row row;
        private final int startColumnPosition;
        private final int length;

        private RowStylist(CellsFiller cellsFiller, Row row, int startColumnPosition, int length) {
            this.cellsFiller = cellsFiller;
            this.row = row;
            this.startColumnPosition = startColumnPosition;
            this.length = length;
        }

        static void decorate(Row row, int startColumnPosition, int length, CellStyle cellStyle){
            for (int i = startColumnPosition; i < startColumnPosition + length; i++) {
                Extractor.extract(row, i).setCellStyle(cellStyle);
            }
        }

        @Override
        public CellsFiller decorate(CellStyle cellStyle) {
            decorate(row, startColumnPosition, length, cellStyle);
            return cellsFiller;
        }
    }

    private static class TableStylist implements Stylist{
        private final CellsFiller cellsFiller;
        private final Sheet sheet;
        private final int startRowPosition;
        private final int rowLength;
        private final int startColumnPosition;
        private final int columnLength;

        private TableStylist(CellsFiller cellsFiller, Sheet sheet, int startRowPosition, int startColumnPosition, int rowLength, int columnLength) {
            this.cellsFiller = cellsFiller;
            this.sheet = sheet;
            this.startRowPosition = startRowPosition;
            this.startColumnPosition = startColumnPosition;
            this.rowLength = rowLength;
            this.columnLength = columnLength;
        }

        @Override
        public CellsFiller decorate(CellStyle cellStyle){
            Row row;
            for (int rowNumber = startRowPosition; rowNumber < startRowPosition + rowLength; rowNumber++) {
                row = Extractor.extract(sheet, rowNumber);
                RowStylist.decorate(row, startColumnPosition, columnLength, cellStyle);
            }
            return cellsFiller;
        }
    }
}
