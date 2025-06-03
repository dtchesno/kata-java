package com.dtchesno.kata.misc;

import org.apache.xmlbeans.impl.tool.StreamInstanceValidator;
import org.junit.Assert;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.CollationElementIterator;
import java.util.*;


public class MiscSolution {

    public static boolean areBracesBalanced(String str) {
        int balance = 0;
        for (char c : str.toCharArray()) {
            if (c == '(') {
                balance++;
            } else if (c == ')') {
                balance--;
            }
            if (balance < 0) return false;
        }
        return balance == 0;
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
        int[][] arr = new int[n][n];
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
            return i == s1.length() ? false : s1.substring(i + 1).equals(s2.substring(i + 1));
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

        while (left < right) {
            double mid = (left + right) / 2;
            double midSquared = mid * mid;
            if (Math.abs(x - midSquared) < epsilon) {
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


    // 84. Largest Rectangle in Histogram
    // https://leetcode.com/problems/largest-rectangle-in-histogram/
    // use stack to store indices, starting with -1;
    // we use indices to calculate weight, height could be taken from input by popped index;
    // on each iteration we will remove higher bars as current will be dominant going further;
    // have -1 at the bottom of the stack lets us calculate area when everything on left of current are higher
    public static int largestRectangleAreaStack(int[] heights) {
        int maxArea = 0;
        Stack<Integer> s = new Stack<>();
        s.push(-1);
        for (int i = 0; i < heights.length; i++) {
            while (s.peek() != -1 && heights[s.peek()] >= heights[i]) {
                int j = s.pop();
                int w = i - s.peek() - 1;
                maxArea = Math.max(maxArea, w * heights[j]);
            }
            s.push(i);
        }
        while (s.peek() != -1) {
            int j = s.pop();
            int w = heights.length - s.peek() - 1;
            maxArea = Math.max(maxArea, w * heights[j]);
        }
        return maxArea;
    }


    // 42. Trapping Rain Water
    // https://leetcode.com/problems/trapping-rain-water/
    public static int trap(int[] heights) {
        int total = 0;
        Stack<Integer> s = new Stack<>();
        for (int i = 0; i < heights.length; i++) {
            while (!s.isEmpty() && heights[s.peek()] <= heights[i]) {
                int j = s.pop();
                if (s.isEmpty()) break;
                int h = Math.min(heights[s.peek()], heights[i]) - heights[j];
                int w = i - s.peek() - 1;
                total += h * w;
            }
            s.push(i);
        }
        return total;
    }


    // 31. Next Permutation
    // https://leetcode.com/problems/next-permutation
    // 556. Next Greater Element III
    // https://leetcode.com/problems/next-greater-element-iii
    public static int nextLargestNumber(int input) {
        List<Integer> digits = new ArrayList<>();

        // 120 -> 0, 2, 1
        while (input > 0) {
            digits.add(input % 10);
            input /= 10;
        }

        // find where to swap on high end - (i + 1)
        int i = 0;
        while (i + 1 < digits.size() && digits.get(i + 1) >= digits.get(i)) i++;
        if (i + 1 == digits.size()) return -1;

        // find where to swap on low end
        int j = i;
        while (j > 0 && digits.get(i + 1) < digits.get(j - 1)) j--;

        // swap and revers
        Collections.swap(digits, i + 1, j);
        reverse(digits, 0, i);

        long result = 0;
        for (int k = digits.size() - 1; k >= 0; k--) {
            result = result * 10 + digits.get(k);
        }
        return result > Integer.MAX_VALUE ? -1 : (int)result;
    }

    // 1842. Next Palindrome Using Same Digits
    // https://leetcode.com/problems/next-palindrome-using-same-digits/description/
    public static String nextPalindrome(String input) {
        int number = Integer.parseInt(input.substring(0, input.length() / 2));
        int nextNumber = nextLargestNumber(number);

        if (nextNumber == -1) return "";

        var sb = new StringBuilder();

        // 123 -> 3, 2, 1 (low digits)
        while (nextNumber != 0) {
            sb.append(nextNumber % 10);
            nextNumber /= 10;
        }

        String low = sb.toString();
        String high = sb.reverse().toString();
        String mid = input.length() % 2 == 1 ? String.valueOf(input.charAt(input.length() / 2 + 1)) : "";

        return high + mid + low;
    }

    private static void swap(Integer[] array, int i, int j) {
        int temp = array[i];
        array[i] = array[j];
        array[j] = temp;
    }

    private static void swap(int[] array, int i, int j) {
        int temp = array[i];
        array[i] = array[j];
        array[j] = temp;
    }

    // now reverse all digits up to i
    private static void reverse(Integer[] array, int i, int j) {
        while (i < j) {
            swap(array, i++, j--);
        }
    }

    // now reverse all digits up to i
    private static void reverse(int[] array, int i, int j) {
        while (i < j) {
            swap(array, i++, j--);
        }
    }

    private static void reverse(List<Integer> l, int i, int j) {
        while (i < j) {
            Collections.swap(l, i++, j--);
        }
    }
}
