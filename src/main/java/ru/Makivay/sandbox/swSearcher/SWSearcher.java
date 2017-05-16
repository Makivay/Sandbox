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

// https://en.wikipedia.org/wiki/Smith%E2%80%93Waterman_algorithm

public class SWSearcher<T> {

    private final Equalizer<T> equalizer;

    public SWSearcher(Equalizer<T> equalizer) {
        this.equalizer = equalizer;
    }

    public int findSequenceStartPosition(final List<T> where, final List<T> what) {
        final Path path = new PathBuilder<T>().build(where, what, equalizer);

        int curX = path.getMaxX();
        int curY = path.getMaxY();
        int h = path.getMaxValue();
        while (h > 0) {
            if (equalizer.equals(where.get(curX), what.get(curY))) {
                h = path.get(--curX, --curY);
            } else {
                h = path.get(curX, --curY);
            }
        }

        return curX + 1;
    }

    public List<Pair<T, T>> findSequence(final List<T> where, final List<T> what) {
        final Path path = new PathBuilder<T>().build(where, what, equalizer);
        final List<Pair<T, T>> result = new ArrayList<>();

        // findSequenceStartPosition sequence start position
        int curX = path.getMaxX();
        int curY = path.getMaxY();
        int h = path.getMaxValue();
        while (h > 0) {
            if (where.get(curX).equals(what.get(curY))) {
                result.add(new Pair<>(where.get(curX), what.get(curY)));
                h = path.get(--curX, --curY);
            } else {
                h = path.get(curX, --curY);
            }
        }

        return Lists.reverse(result);
    }

}
