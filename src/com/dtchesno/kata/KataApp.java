package com.dtchesno.kata;

import com.dtchesno.kata.bitops.BitsSolution;

public class KataApp {
    public static void main(String[] args) {
        System.out.println("Hello, world!");
        for (String arg: args) {
            System.out.println(arg);
        }
        double avg;
        long originalAvg = 63;
        avg = originalAvg / 1000;
        System.out.println("result - " + avg);

        System.out.println(BitsSolution.printBinary("1.5"));
        System.out.println(BitsSolution.printBinary("1.8125"));
    }
}
