package ru.Makivay.sandbox.structures;

import com.google.common.collect.HashBasedTable;
import com.google.common.collect.Table;

import javax.annotation.Nonnull;
import java.util.Collection;
import java.util.Map;
import java.util.Set;

/**
 * Using to store client's per month codes.
 */
class ClientSummaryIntegerTableImpl<R, C> implements ClientSummaryIntegerTable<R, C> {

    private final Table<R, C, Integer> table;

    ClientSummaryIntegerTableImpl() {
        this.table = HashBasedTable.create();
    }

//    public static <R, C> ClientSummaryIntegerTable<R, C> create(){}

    @Override
    public Integer incrementValue(R row, C col) {
        Integer val = table.get(row, col);
        val = val == null ? 1 : val + 1;
        return table.put(row, col, val);
    }

    @Override
    public Integer getOr(R row, C col, Integer def) {
        return table.get(row, col) != null ? table.get(row, col) : def;
    }

    @Override
    public boolean contains(Object o, Object o1) {
        return table.contains(o, o1);
    }

    @Override
    public boolean containsRow(Object o) {
        return table.containsRow(o);
    }

    @Override
    public boolean containsColumn(Object o) {
        return table.containsColumn(o);
    }

    @Override
    public boolean containsValue(Object o) {
        return table.containsValue(o);
    }

    @Override
    public Integer get(Object o, Object o1) {
        return table.get(o, o1);
    }

    @Override
    public boolean isEmpty() {
        return table.isEmpty();
    }

    @Override
    public int size() {
        return table.size();
    }

    @Override
    public void clear() {
        table.clear();
    }

    @Override
    public Integer put(@Nonnull R r, @Nonnull C c, @Nonnull Integer integer) {
        return table.put(r, c, integer);
    }

    @Deprecated
    @Override
    public void putAll(@Nonnull Table<? extends R, ? extends C, ? extends Integer> table) {
    }

    @Override
    public Integer remove(Object o, Object o1) {
        return table.remove(o, o1);
    }

    @Override
    public Map<C, Integer> row(@Nonnull R r) {
        return table.row(r);
    }

    @Override
    public Map<R, Integer> column(@Nonnull C c) {
        return table.column(c);
    }

    @Override
    public Set<Table.Cell<R, C, Integer>> cellSet() {
        return table.cellSet();
    }

    @Override
    public Set<R> rowKeySet() {
        return table.rowKeySet();
    }

    @Override
    public Set<C> columnKeySet() {
        return table.columnKeySet();
    }

    @Override
    public Collection<Integer> values() {
        return table.values();
    }

    @Override
    public Map<R, Map<C, Integer>> rowMap() {
        return table.rowMap();
    }

    @Override
    public Map<C, Map<R, Integer>> columnMap() {
        return table.columnMap();
    }
}
