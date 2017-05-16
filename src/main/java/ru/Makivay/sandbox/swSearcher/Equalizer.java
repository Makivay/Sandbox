package ru.Makivay.sandbox.swSearcher;

/**
 * Created by makivay
 * on 16.05.17
 */
@FunctionalInterface
public interface Equalizer<T> {

    boolean equals(T o1, T o2);
}
