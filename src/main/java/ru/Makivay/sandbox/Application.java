package ru.Makivay.sandbox;

import com.google.common.collect.HashMultiset;
import com.google.common.collect.Lists;
import com.google.common.collect.Multiset;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import javax.annotation.Nonnull;
import java.util.*;
import java.util.stream.IntStream;
import java.util.stream.StreamSupport;

public class Application {

    private final static Gson gson = new GsonBuilder().serializeNulls().setPrettyPrinting().create();
    private final static Random random = new Random(System.currentTimeMillis());

    public static void main(String args[]) {

        final List<double[]> weights = new ArrayList<>();

        weights.add(new double[]{0.25d, 0.25d, 0.25d, 0.25d});
        weights.add(new double[]{0.25d, 0.25d, 0.5d, 0.0d});

        weights.forEach(doubles ->System.out.println(calcWeight(doubles)));

    }

    final

    private static double calcWeight(@Nonnull double weights[]){
        double w = weights[0];
        int count = 1;
        for (int i = 1; i < weights.length; i++) {
            if(weights[i] > 0.0d){
                w *= weights[i];
                count++;
            }
        }
        final double penalty = (double) count/weights.length;
        return Math.pow(w, 1.0/count) * penalty;
    }

    private static double fn(@Nonnull double weights[]){
//        final OptionalDouble multiply = Arrays.stream(weights).map(operand -> Math.pow(Math.E, operand)).reduce((a, b) -> a * b);
            final OptionalDouble multiply = Arrays.stream(weights).average();
        return multiply.isPresent() ? multiply.getAsDouble() * Math.log(weights.length) : 0.0d;
    }
}