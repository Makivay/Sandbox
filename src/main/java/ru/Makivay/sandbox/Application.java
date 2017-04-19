package ru.Makivay.sandbox;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Application {

    private final static Gson gson = new GsonBuilder().serializeNulls().setPrettyPrinting().create();
    private final static Random random = new Random(System.currentTimeMillis());

    public static void main(String args[]) {

        final List<A> aList = new ArrayList<>();

        aList.add(new B("a", "b"));
        aList.add(new C("a", "c"));

        System.out.println(gson.toJson(aList));
    }
    
    private static abstract class A {
        final String a;

        public A(String a) {
            this.a = a;
        }
    }

    private static final class B extends A {
        final String b;

        private B(String a, String b) {
            super(a);
            this.b = b;
        }
    }
    
    private static final class C extends A {
        final String c;

        private C(String a, String c) {
            super(a);
            this.c = c;
        }
    }

}