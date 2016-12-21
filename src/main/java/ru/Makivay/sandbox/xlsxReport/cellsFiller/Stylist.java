package ru.Makivay.sandbox.xlsxReport.cellsFiller;

import org.apache.poi.ss.usermodel.CellStyle;

public interface Stylist {
    CellsFiller decorate(CellStyle cellStyle);
}
