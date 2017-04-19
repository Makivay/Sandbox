package ru.Makivay.sandbox.tests.Searcher;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import ru.Makivay.sandbox.searcher.LcsPath;
import ru.Makivay.sandbox.searcher.LcsPathHolder;
import ru.Makivay.sandbox.searcher.Rule;
import ru.Makivay.sandbox.searcher.Searcher;

import java.util.List;
import java.util.PriorityQueue;

/**
 * Created by makivay on 16.02.17.
 */
public class Tests {
    private final static Gson gson = new GsonBuilder().serializeNulls().setPrettyPrinting().create();

    private final static String TEXT = "one two three four five six seven eight nine";
    private final static String RULE = "\"one\" Any() \"three\" Any() \"five\"";
//    private final static String TEXT = "Lorem Ipsum is simply dummy text of the printing and typesetting industry.Lorem Ipsum is simply dummy text of the printing and typesetting industry.Lorem Ipsum is simply dummy text of the printing and typesetting industry.";
//    private final static String RULE = "\"simply\" Any() \"printing\"";
//    private final static String TEXT = "11111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111";
//    private final static String RULE = "\"111111111111\" \"111111111111\" \"111111111111\"";

    public static void main(String[] args){
        final Rule rule = Rule.parse(RULE);
        final LcsPathHolder holder = Searcher.process(TEXT, rule);

        final List<LcsPath> allPaths = holder.getAllPaths();

        final PriorityQueue<LcsPath> lcsPathPriorityQueue = new PriorityQueue<>((o1, o2) -> {
            final int probComparison = Double.compare(o2.getPathProb(), o1.getPathProb());
            final int lenghtComparison = Integer.compare(o2.getPathlenght(), o1.getPathlenght());
            return lenghtComparison == 0 ? probComparison : lenghtComparison;
        });

        lcsPathPriorityQueue.addAll(allPaths);

        System.out.println("Best path: ");
        System.out.println(gson.toJson(lcsPathPriorityQueue.peek()));
    }
}
