package ru.Makivay.sandbox.utils;

import java.util.stream.IntStream;

public class SomeUselessStuff {

    public static void cpuHeat(){
        IntStream.range(0, Runtime.getRuntime().availableProcessors()).parallel().forEach(i -> {while (true){}});
    }
}
