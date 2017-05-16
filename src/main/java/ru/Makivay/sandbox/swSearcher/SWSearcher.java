package ru.Makivay.sandbox.swSearcher;

import com.google.common.collect.Lists;
import javafx.util.Pair;
import ru.Makivay.sandbox.swSearcher.path.Path;
import ru.Makivay.sandbox.swSearcher.path.PathBuilder;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by makivay
 * on 16.05.17
 */

// https://en.wikipedia.org/wiki/Smith–Waterman_algorithm

public class SWSearcher<T> {
    private final Equalizer<T> equalizer;

    private List<T> lastWhere;
    private List<T> lastWhat;
    private Results<T> lastResult;

    public SWSearcher(Equalizer<T> equalizer) {
        this.equalizer = equalizer;
        this.lastWhere = null;
        this.lastWhat = null;
        this.lastResult = null;
    }

    private Results<T> calcResults(final List<T> where, final List<T> what) {
        if (where == null || what == null) {
            throw new IllegalArgumentException();
        }

        final Path path = new PathBuilder<T>().build(where, what, equalizer);
        final List<T> list = new ArrayList<>();
        final List<Pair<T, T>> pairs = new ArrayList<>();

        int curX = path.getMaxX();
        int curY = path.getMaxY();
        int h = path.getMaxValue();
        while (h > 0) {
            final T whereElement = where.get(curX);
            final T whatElement = what.get(curY);
            if (equalizer.equals(whereElement, whatElement)) {
                list.add(whereElement);
                pairs.add(new Pair<>(whereElement, whatElement));
                h = path.get(--curX, --curY);
            } else {
                h = path.get(curX, --curY);
            }
        }

        return new Results<>(curX + 1, Lists.reverse(list), Lists.reverse(pairs));
    }

    private boolean cached(final List<T> where, final List<T> what) {
        return lastWhere != null && lastWhat != null && lastWhere.equals(where) && lastWhat.equals(what);
    }

    public int findSequenceStartPosition(final List<T> where, final List<T> what) {
        if (!cached(where, what)) {
            lastResult = calcResults(where, what);
        }
        return lastResult.getStartPosition();
    }

    public List<T> findSequence(final List<T> where, final List<T> what) {
        if (!cached(where, what)) {
            lastResult = calcResults(where, what);
        }
        return lastResult.getList();
    }


    public List<Pair<T, T>> findSequenceOfPairs(final List<T> where, final List<T> what) {
        if (!cached(where, what)) {
            lastResult = calcResults(where, what);
        }
        return lastResult.getPairs();
    }

}
