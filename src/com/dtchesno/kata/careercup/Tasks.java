package com.dtchesno.kata.careercup;

import java.util.Arrays;
import java.util.Comparator;

public class Tasks {

    private static class Pair {
        String first;
        String second;

        boolean isEqual(Pair p) {
            return first.equals(p.first) && second.equals(p.second);
        }
    }

    public static int getDistinctStrings(String[] words) {
        Pair[] pairs = new Pair[words.length];
        for (int i = 0; i < words.length; i++) {
            pairs[i] = createPair(words[i]);
        }
        Arrays.sort(pairs, new Comparator<Pair>() {
            @Override
            public int compare(Pair o1, Pair o2) {
                int c1 = o1.first.compareTo(o2.first);
                int c2 = o1.second.compareTo(o2.second);
                return (c1 != 0) ? c1 : c2;
            }
        });

        int count = 0;
        if (pairs.length > 0) {
            count = 1;
        }
        for (int i = 1; i < pairs.length; i++) {
            if (!pairs[i].isEqual(pairs[i - 1])) {
                count++;
            }
        }

        return count;
    }

    private static Pair createPair(String word) {
        Pair pair = new Pair();
        char[] buf = new char[100];
        int length = word.length();
        int oddLen = (length + 1) / 2;
        int evenLen = length / 2;

        for (int i = 0; i < oddLen; i++) {
            buf[i] = word.charAt(i * 2);
        }
        Arrays.sort(buf, 0, oddLen);
        pair.first = String.copyValueOf(buf, 0, oddLen);

        for (int i = 0; i < evenLen; i++) {
            buf[i] = word.charAt(i * 2 + 1);
        }
        Arrays.sort(buf, 0, evenLen);
        pair.second = String.copyValueOf(buf, 0, evenLen);

        return pair;
    }
}
