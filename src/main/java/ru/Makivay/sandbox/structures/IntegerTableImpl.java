package ru.Makivay.sandbox.structures;

import com.google.common.collect.HashBasedTable;
import com.google.common.collect.Table;

import java.util.Collection;
import java.util.Map;
import java.util.Set;

/**
 * Using to store client's per month codes.
 */
public class IntegerTableImpl<R, C> implements IntegerTable<R, C> {

    private final Table<R, C, Integer> table;

    public IntegerTableImpl() {
        this.table = HashBasedTable.create();
    }

//    public static <R, C> IntegerTable<R, C> create(){}

    public Integer incrementValue(R row, C col) {
        Integer val = table.get(row, col);
        val = val == null ? 1 : val + 1;
        return table.put(row, col, val);
    }

    public Integer getOr(R row, C col, Integer def) {
        return table.get(row, col) != null ? table.get(row, col) : def;
    }

    public boolean contains(Object o, Object o1) {
        return table.contains(o, o1);
    }

    public boolean containsRow(Object o) {
        return table.containsRow(o);
    }

    public boolean containsColumn(Object o) {
        return table.containsColumn(o);
    }

    public boolean containsValue(Object o) {
        return table.containsValue(o);
    }

    public Integer get(Object o, Object o1) {
        return table.get(o, o1);
    }

    public boolean isEmpty() {
        return table.isEmpty();
    }

    public int size() {
        return table.size();
    }

    public void clear() {
        table.clear();
    }

    public Integer put(R r, C c, Integer integer) {
        return table.put(r, c, integer);
    }

    @Deprecated
    public void putAll(Table<? extends R, ? extends C, ? extends Integer> table) {
    }

    public Integer remove(Object o, Object o1) {
        return table.remove(o, o1);
    }

    public Map<C, Integer> row(R r) {
        return table.row(r);
    }

    public Map<R, Integer> column(C c) {
        return table.column(c);
    }

    public Set<Cell<R, C, Integer>> cellSet() {
        return table.cellSet();
    }

    public Set<R> rowKeySet() {
        return table.rowKeySet();
    }

    public Set<C> columnKeySet() {
        return table.columnKeySet();
    }

    public Collection<Integer> values() {
        return table.values();
    }

    public Map<R, Map<C, Integer>> rowMap() {
        return table.rowMap();
    }

    public Map<C, Map<R, Integer>> columnMap() {
        return table.columnMap();
    }
}
