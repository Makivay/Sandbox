package ru.Makivay.sandbox;

import ru.Makivay.sandbox.tests.PdfTests;
import ru.Makivay.sandbox.utils.SomeUselesStuff;
import sun.nio.ch.ThreadPool;

import java.io.IOException;
import java.util.*;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Application {


    private final static Map<String, String> LKM = getLineKeyMap();
    private final static int arraySize = Integer.MAX_VALUE >> 7;

    public static void main(String args[]) throws IOException {

        SomeUselesStuff.cpuHeat();

    }

    private static void logMem() {
        log("mem: " + ((Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory()) / 1048576) + "mb");
    }

    private static void log(Object message) {
        System.out.println(new Date() + " " + String.valueOf(message));
    }

    private static Map<String, String> getLineKeyMap() {
        final Map<String, String> lineKey = new HashMap<>();

//        lineKey.put("CoveredIndividualIndicator", "CoveredIndividualIndicator"); //Yes|Off
//        lineKey.put("VOID", "VOID"); // On|Off
//        lineKey.put("CORRECTED", "CORRECTED"); // On|Off
        lineKey.put("FirstClass Mail", "FirstClass Mail");
        lineKey.put("FROM", "FROM");
        lineKey.put("INDICIA", "INDICIA");
        lineKey.put("Plan Start Month", "Plan Start Month");
        lineKey.put("7 Name of employer", "7 Name of employer");
        lineKey.put("1", "1 Employee Name");
        lineKey.put("2 Employee SSN", "2 Employee SSN");
        lineKey.put("3 Employee Street Address", "3 Employee Street Address");
        lineKey.put("4 Employee City", "4 Employee City");
        lineKey.put("5 Employee State", "5 Employee State");
        lineKey.put("6 Country and ZIP", "6 Country and ZIP");
        lineKey.put("9 Employer Street Address", "9 Employer Street Address");
        lineKey.put("8 Employer EIN", "8 Employer EIN");
        lineKey.put("10 Contact telephone number", "10 Contact telephone number");
        lineKey.put("11 Employer City", "11 Employer City");
        lineKey.put("12 Employer State", "12 Employer State");
        lineKey.put("13 Employer Country and ZIP", "13 Employer Country and ZIP");
        lineKey.put("Counter", "Counter");
        lineKey.put("Message", "Message");

        for (int i = 14; i < 17; i++) {
            lineKey.put("Line " + i + " All 12 Months", "Line " + i + " All 12 Months");
            lineKey.put("Line " + i + " January", "Line " + i + " January");
            lineKey.put("Line " + i + " February", "Line " + i + " February");
            lineKey.put("Line " + i + " March", "Line " + i + " March");
            lineKey.put("Line " + i + " April", "Line " + i + " April");
            lineKey.put("Line " + i + " May", "Line " + i + " May");
            lineKey.put("Line " + i + " June", "Line " + i + " June");
            lineKey.put("Line " + i + " July", "Line " + i + " July");
            lineKey.put("Line " + i + " August", "Line " + i + " August");
            lineKey.put("Line " + i + " September", "Line " + i + " September");
            lineKey.put("Line " + i + " October", "Line " + i + " October");
            lineKey.put("Line " + i + " November", "Line " + i + " November");
            lineKey.put("Line " + i + " December", "Line " + i + " December");
        }

        for (int i = 17; i < 27; i++) {
            lineKey.put("DOB" + i, "DOB" + i);
            lineKey.put("SSN" + i, "SSN" + i);
            lineKey.put("CoveredIndividualNameRow" + i, "CoveredIndividualNameRow" + i);
//            lineKey.put("Line" + i + "AnnCov", "Line" + i + "AnnCov"); // Yes|Off
//            lineKey.put("Line" + i + "JanCov", "Line" + i + "JanCov"); // Yes|Off
//            lineKey.put("Line" + i + "FebCov", "Line" + i + "FebCov"); // Yes|Off
//            lineKey.put("Line" + i + "MarCov", "Line" + i + "MarCov"); // Yes|Off
//            lineKey.put("Line" + i + "AprCov", "Line" + i + "AprCov"); // Yes|Off
//            lineKey.put("Line" + i + "MayCov", "Line" + i + "MayCov"); // Yes|Off
//            lineKey.put("Line" + i + "JunCov", "Line" + i + "JunCov"); // Yes|Off
//            lineKey.put("Line" + i + "JulCov", "Line" + i + "JulCov"); // Yes|Off
//            lineKey.put("Line" + i + "AugCov", "Line" + i + "AugCov"); // Yes|Off
//            lineKey.put("Line" + i + "SepCov", "Line" + i + "SepCov"); // Yes|Off
//            lineKey.put("Line" + i + "OctCov", "Line" + i + "OctCov"); // Yes|Off
//            lineKey.put("Line" + i + "NovCov", "Line" + i + "NovCov"); // Yes|Off
        }

        return lineKey;
    }
}
