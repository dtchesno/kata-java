package com.dtchesno.kata.misc;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Arrays;
import java.util.Comparator;
import java.util.PriorityQueue;
import java.util.Stack;


public class MiscSolution {

    public static boolean isBracesBalanced(String str) {
        int count = 0;
        for (char c: str.toCharArray()) {
            switch (c) {
                case '(':
                    count++;
                    break;
                case ')':
                    count--;
                    break;
                default:
                    return false;
            }
            if (count < 0) {
                return false;
            }
        }
        return count == 0;
    }

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

    public static void mergeStreams(OutputStream os, InputStream[] inputs) {
        class Pair {
            InputStream is;
            int value;

            Pair(InputStream is, int value) {
                this.is = is;
                this.value = value;
            }
        }

        //PriorityQueue<Pair> q = new PriorityQueue<>((p1, p2) -> p1.value - p2.value);
        PriorityQueue<Pair> q = new PriorityQueue<>(Comparator.comparing((p)->p.value));

        for (InputStream is: inputs) {
            try {
                int value = is.read();
                if (value != -1) {
                    q.add(new Pair(is, value));
                }
            } catch (IOException e) {}
        }

        while (!q.isEmpty()) {
            try {
                Pair p = q.poll();
                os.write(p.value);

                int newValue = p.is.read();
                if (newValue != -1) {
                    q.add(new Pair(p.is, newValue));
                    continue;
                }
            } catch (IOException e) {}

            for (InputStream is: inputs) {
                try {
                    int value = is.read();
                    if (value != -1) {
                        q.add(new Pair(is, value));
                        continue;
                    }
                } catch (IOException e) {}
            }
        }
    }

    // build 2d 'spiral' array of size n; e.g.
    //
    // 1 2 3
    // 8 9 4
    // 7 6 5
    public static int[][] spiral(int n) {
        int [][] arr = new int[n][n];
        for (int[] a: arr) {
            Arrays.fill(a, 0);
        }

        int i = 0;
        int j = 0;
        arr[i][j] = 1;
        int direction = 0; // 0/1/2/3 - right/down/left/up
        for (int k = 2; k <= n * n; k++) {
            while (true) {
                int i1 = 0, j1 = 0;
                switch (direction) {
                    // right
                    case 0: i1 = i; j1 = j + 1; break;

                    // down
                    case 1: i1 = i + 1; j1 = j; break;

                    // left
                    case 2: i1 = i; j1 = j - 1; break;

                    // up
                    case 3: i1 = i - 1; j1 = j; break;
                }

                if (i1 >= 0 && i1 < n && j1 >= 0 && j1 < n && arr[i1][j1] == 0) {
                    i = i1;
                    j = j1;
                    arr[i][j] = k;
                    break;
                }

                direction = (direction + 1) % 4;
            }
        }

        return arr;
    }

    // https://www.careercup.com/question?id=5926214520799232
    public static boolean isOneEditAway(String s1, String s2) {
        int i = 0;

        while (i < s1.length() && i < s2.length() && s1.charAt(i) == s2.charAt(i)) {
            i++;
        }

        if (s1.length() == s2.length()) {
            if (i == s1.length()) {
                return true;
            }
            return s1.substring(i + 1).equals(s2.substring(i + 1));
        }

        return (s1.length() > s2.length()) ? s1.substring(i + 1).equals(s2.substring(i))
                : s1.substring(i).equals(s2.substring(i + 1));
    }

    // https://www.careercup.com/question?id=15519735
    public static String lookAndSay(int n) {
        String str = "1";
        for (int i = 1; i < n; i++) {
            str = nextNumber(str);
        }
        return str;
    }

    private static String nextNumber(String str) {
        StringBuffer next = new StringBuffer();
        for (int i = 0; i < str.length(); i++) {
            int count = 1;
            while (i + 1 < str.length() && str.charAt(i) == str.charAt(i + 1)) {
                count++;
                i++;
            }
            next.append(count).append(str.charAt(i));
        }
        return next.toString();
    }

    // calculate real square root with given error tolerance
    // Aziz #12.5 pg.195
    public static double squareRoot(double x, double epsilon) {
        if (x < 0) return -1;
        if (x == 0) return 0;
        double left;
        double right;
        if (x < 1.0) {
            left = x;
            right = 1.0;
        } else {
            left = 1.0;
            right = x;
        }

        //while (compare(left, right, epsilon) < 0) {
        while (left < right) {
            double mid = (left + right) / 2;
            double midSquared = mid * mid;
            if (compare(midSquared, x, epsilon) == 0) {
                return mid;
            }
            if (x > midSquared) {
                left = mid;
            } else {
                right = mid;
            }
        }
        return left;
    }

    private static int compare(double v1, double v2, double epsilon) {
        double diff = v1 - v2;
        if (diff < 0 && -diff > epsilon) {
            return -1;
        }
        if (diff > 0 && diff > epsilon) {
            return 1;
        }
        return 0;
    }

    // DONE
    // https://leetcode.com/problems/largest-rectangle-in-histogram/
}
