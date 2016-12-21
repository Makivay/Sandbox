package ru.Makivay.sandbox.utils;

import java.util.stream.IntStream;

public class SomeUselesStuff {

    public static void cpuHeat(){
        IntStream.range(0, Runtime.getRuntime().availableProcessors()).parallel().forEach(i -> {while (true){}});
    }
}
