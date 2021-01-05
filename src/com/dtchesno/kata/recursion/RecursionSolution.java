package com.dtchesno.kata.recursion;

import java.util.*;

public class RecursionSolution {
    public static Set<String> permuteBraces(int n) {
        return permuteBraces(n, n, "", new HashSet<String>());
    }

    private static Set<String> permuteBraces(int l, int r, String str, Set<String> result) {
        if (l == 0 && r == 0) {
            result.add(str);
            return result;
        }
        Set<String> s1 = null;
        Set<String> s2 = null;
        if (l > 0) {
            s1 =  permuteBraces(l - 1, r, str + '(', result);
        }
        if (r > l && r > l) {
            s2 =  permuteBraces(l, r - 1, str + ')', result);
        }
        if (s1 != null) {
            result.addAll(s1);
        }
        if (s2 != null) {
            result.addAll(s2);
        }
        return result;
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
        //sortStack(s);
        sortStackI(s);
        int[] result = new int[s.size()];
        for (int i = 0; i < result.length; i++) {
            result[i] = s.pop();
        }
        return result;
    }

    private static void sortStack(Stack<Integer> s) {
        if (s.size() <= 1) {
            return;
        }
        int top = s.pop();
        sortStack(s);
        while (top > s.peek()) {
            int temp = s.pop();
            s.push(top);
            top = temp;
            sortStack(s);
        }
        s.push(top);
    }

    private static void sortStackI(Stack<Integer> s) {
        Stack<Integer> s2 = new Stack<>();
        while (!s.isEmpty()) {
            int top = s.pop();
            while (!s2.isEmpty() && s2.peek() < top) {
                s.push(s2.pop());
            }
            s2.push(top);
        }
        s.addAll(s2);
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
