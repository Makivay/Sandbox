package ru.Makivay.sandbox.searcher;

import javax.annotation.Nullable;

/**
 * Created by makivay on 14.02.17.
 */
public class LcsPath {
    private final String text;
    private final int startPosition;
    private final int charLenght;
    private final double prob;
    private final double pathProb;
    private final int pathlenght;
    private final LcsPath parent;

    public LcsPath(String text, int startPosition, double prob) {
        this.text = text;
        this.startPosition = startPosition;
        this.charLenght = text.length();
        this.prob = prob;
        this.parent = null;
        this.pathProb = prob;
        this.pathlenght = 1;
    }

    public LcsPath(String text, int startPosition, double prob, LcsPath parent) {
        this.text = text;
        this.startPosition = startPosition;
        this.charLenght = text.length();
        this.prob = prob;
        this.parent = parent;
        this.pathProb = parent.getPathProb() * prob;
        this.pathlenght = parent.getPathlenght() + 1;
    }

    public String getText() {
        return text;
    }

    public int getStartPosition() {
        return startPosition;
    }

    public int getCharLenght() {
        return charLenght;
    }

    public double getProb() {
        return prob;
    }

    @Nullable
    public LcsPath getParent() {
        return parent;
    }

    public double getPathProb() {
        return pathProb;
    }

    public int getPathlenght() {
        return pathlenght;
    }
}
