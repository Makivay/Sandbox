package ru.Makivay.sandbox.utils;

import java.util.stream.IntStream;

public class SomeUselessStuff {

    public static void infinityCpuHeat() {
        IntStream.range(0, Runtime.getRuntime().availableProcessors()*2).parallel().forEach(i -> {
            while (true) {
                for (int index = 1; index < Integer.MAX_VALUE; index++) {
                    final int result = Integer.MAX_VALUE % index;
                }
            }
        });
    }
}
