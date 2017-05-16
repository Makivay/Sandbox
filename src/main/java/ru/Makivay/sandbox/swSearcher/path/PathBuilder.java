package ru.Makivay.sandbox.swSearcher.path;

import ru.Makivay.sandbox.swSearcher.Equalizer;

import java.util.List;

import static java.lang.Math.max;

/**
 * Created by makivay
 * on 16.05.17
 */
public class PathBuilder<T> {
    private final static int W = 2;

    private static int getFromPath(final int[][] path, final int x, final int y) {
        return x >= 0 && x < path.length && y >= 0 && y < path[x].length ? path[x][y] : 0;
    }

    public Path build(final List<T> where, final List<T> what, Equalizer<T> equalizer) {
        final int path[][] = new int[where.size()][what.size()];
        int maxX = -1;
        int maxY = -1;
        int maxValue = -1;

        for (int x = 0; x < where.size(); x++) {
            for (int y = 0; y < what.size(); y++) {
                final int h1 = getFromPath(path, x - 1, y) - W;
                final int h2 = getFromPath(path, x, y - 1) - W;
                final int s = equalizer.equals(where.get(x), what.get(y)) ? 3 : -3;
                final int h3 = getFromPath(path, x - 1, y - 1) + s;
                final int value = max(max(h1, h2), max(h3, 0));
                path[x][y] = value;
                if (value > maxValue) {
                    maxX = x;
                    maxY = y;
                    maxValue = value;
                }
            }
        }

        return new Path(path, maxX, maxY, maxValue);
    }
}
