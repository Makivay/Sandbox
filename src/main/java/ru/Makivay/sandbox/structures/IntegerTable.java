package ru.Makivay.sandbox.structures;

import com.google.common.collect.Table;

public interface IntegerTable<R, C> extends Table<R, C, Integer> {

    Integer incrementValue(R row, C col);

    Integer getOr(R row, C col, Integer def);
}
