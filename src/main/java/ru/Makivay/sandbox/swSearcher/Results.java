package ru.Makivay.sandbox.swSearcher;

import javafx.util.Pair;

import java.util.List;

/**
 * Created by makivay
 * on 16.05.17
 */
final class Results<T> {
    private final int startPosition;
    private final List<T> list;
    private final List<Pair<T, T>> pairs;

    Results(int startPosition, List<T> list, List<Pair<T, T>> pairs) {
        this.startPosition = startPosition;
        this.list = list;
        this.pairs = pairs;
    }

    int getStartPosition() {
        return startPosition;
    }

    List<T> getList() {
        return list;
    }

    List<Pair<T, T>> getPairs() {
        return pairs;
    }
}
