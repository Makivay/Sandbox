package ru.Makivay.sandbox.structures;

import com.google.common.collect.Table;

interface ClientSummaryIntegerTable<R, C> extends Table<R, C, Integer> {

    Integer incrementValue(R row, C col);

    Integer getOr(R row, C col, Integer def);
}
