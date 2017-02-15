package ru.Makivay.sandbox;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import ru.Makivay.sandbox.searcher.*;

import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.OptionalInt;
import java.util.stream.Collectors;

public class Application {

    private final static Gson gson = new GsonBuilder().serializeNulls().setPrettyPrinting().create();

    private final static String TEXT = "Lorem Ipsum is simply dummy text of the printing and typesetting industry.Lorem Ipsum is simply dummy text of the printing and typesetting industry.Lorem Ipsum is simply dummy text of the printing and typesetting industry.";
    private final static String RULE = "\"simply\" Any() \"printing\"";
//    private final static String TEXT = "11111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111";
//    private final static String RULE = "\"111111111111\" \"111111111111\" \"111111111111\"";

    public static void main(String args[]) throws IOException {
        final Rule rule = Rule.parse(RULE);
        final LcsPathHolder holder = Searcher.process(TEXT, rule);

        final List<LcsPath> allPaths = holder.getAllPaths();


        final OptionalInt maxPathLenght = allPaths.stream().mapToInt(LcsPath::getPathlenght).max();
        if(maxPathLenght.isPresent()) {
            final double bestProp = allPaths.stream().filter(lcsPath -> lcsPath.getPathlenght() == maxPathLenght.getAsInt()).mapToDouble(LcsPath::getPathProb).max().orElse(0.0);
            if(bestProp != 0.0) {
                final List<LcsPath> bestPath = allPaths.stream().filter(lcsPath -> lcsPath.getPathProb() == bestProp && lcsPath.getPathlenght() == maxPathLenght.getAsInt()).collect(Collectors.toList());
                System.out.println("Best path: ");
                System.out.println(gson.toJson(bestPath));
            }
        }
//        System.out.println(gson.toJson(allPaths);

//        final Rule rule = Rule.parse(RULE);
//        final LcsPathHolder holder = new LcsPathHolder();
//        for (int i = 0; i < 2; i++) {
//            for (Expression expression : rule.getExpressions()) {
//                holder.put(i, expression, new LcsPath("new", 1.0));
//            }
//        }
//
//        System.out.println(gson.toJson(holder));
    }
}