package com.dtchesno.kata.recursion;

import java.util.*;

public class RecursionSolution {


    public static List<String> permuteBraces(int n) {
        List<String> result = new ArrayList<>();
        permuteBraces(n, n, "", result);
        return result;
    }

    private static void permuteBraces(int l, int r, String str, List<String> result) {
        if (l == 0 && r == 0) {
            result.add(str);
        }
        if (l > 0) {
            permuteBraces(l - 1, r, str + "(", result);
        }
        if (r > 0 && r > l) {
            permuteBraces(l, r - 1, str + ")", result);
        }
        return;
    }

    // reverse stack: byte-by-byte #20
    // sort stacks: byte-by-byte #28
    // [selected - 1]
    public static void reverseStack(Stack<Integer> s) {
        if (s.isEmpty()) {
            return;
        }
        int top = s.pop();
        reverseStack(s);
        pushToBottom(top, s);
    }

    private static void pushToBottom(int val, Stack<Integer> s) {
        if (s.isEmpty()) {
            s.push(val);
            return;
        }
        int temp = s.pop();
        pushToBottom(val, s);
        s.push(temp);
    }


    // sort stacks: byte-by-byte #28 pg.27
    // [selected - 1]
    public static int[] sortStack(int[] values) {
        Stack<Integer> s = new Stack<>();
        for (int val: values) {
            s.push(val);
        }
        sortStackI(s);
        int[] result = new int[s.size()];
        for (int i = 0; i < result.length; i++) {
            result[i] = s.pop();
        }
        return result;
    }

    private static void sortStackI(Stack<Integer> s) {
        Stack<Integer> s2 = new Stack<>();
        while (!s.isEmpty()) {
            int top = s.pop();
            while (!s2.isEmpty() && s2.peek() > top) {
                s.push(s2.pop());
            }
            s2.push(top);
        }

        while (!s2.isEmpty()) {
            s.push(s2.pop());
        }
    }

    // generate all permutations of given list: byte-by-byte #12 pg.12
    // [selected - 2]
    public static List<List<Integer>> getAllPermutations(List<Integer> input) {
        ArrayList<List<Integer>> result = new ArrayList<>();
        getAllPermutations(input, 0, result);
        return result;
    }

    private static void getAllPermutations(List<Integer> input, int k, List<List<Integer>> result) {
        if (k == input.size()) {
            result.add(new ArrayList<>(input));
            return;
        }
        for (int i = k; i < input.size(); i++) {
            Collections.swap(input, k, i);
            getAllPermutations(input, k + 1, result);
            Collections.swap(input, k, i);
        }
    }
}
