package ru.Makivay.sandbox.swSearcher.path;

/**
 * Created by makivay
 * on 16.05.17
 */
public class Path {
    private final int path[][];
    private final int maxX;
    private final int maxY;
    private final int maxValue;

    Path(int[][] path, int maxX, int maxY, int maxValue) {
        this.path = path;
        this.maxX = maxX;
        this.maxY = maxY;
        this.maxValue = maxValue;
    }

    public int getMaxX() {
        return maxX;
    }

    public int getMaxY() {
        return maxY;
    }

    public int getMaxValue() {
        return maxValue;
    }

    public int get(final int x, final int y) {
        return x >= 0 && x < path.length && y >= 0 && y < path[x].length ? path[x][y] : 0;
    }
}
