package ru.Makivay.sandbox.utils;

/**
 * Created by makivay on 16.12.16.
 */
public class Counter {
    private Integer i = 0;

    public Integer getI() {
        return i;
    }

    public synchronized Integer inc() {
        return i++;
    }
}
