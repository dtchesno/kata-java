package com.dtchesno.kata.recursion;

import java.util.LinkedList;
import java.util.Queue;
import java.util.Stack;

// https://leetcode.com/problems/basic-calculator-iii/
public class Calculator {
    public int calculate(String s) {
        Queue<Character> q = new LinkedList<>();
        for (char c : s.toCharArray()) {
            if (!Character.isWhitespace(c)) q.add(c);
        }
        q.add('+');
        return calculate(q);
    }

    private int calculate(Queue<Character> q) {
        Stack<Integer> s = new Stack<>();
        int value = 0;
        char sign = '+';

        while (!q.isEmpty()) {
            char c = q.poll();

            if (c == '(') {
                value = calculate(q);
            } else if (Character.isDigit(c)) {
                value = value * 10 + (c - '0');
            } else {
                if (sign == '+') {
                    s.push(value);
                } else if (sign == '-') {
                    s.push(-value);
                } else if (sign == '/') {
                    s.push(s.pop() / value);
                } else if (sign == '*') {
                    s.push(s.pop() * value);
                }
                if (c == ')') break;
                sign = c;
                value = 0;
            }
        }

        value = 0;
        for (int v : s) {
            value += v;
        }
        return value;
    }

//    public int calculate(String s) {
//        Queue<Character> q = new LinkedList<>();
//        for (char c : s.toCharArray()) {
//            if (Character.isWhitespace(c)) continue;
//            q.add(c);
//        }
//        q.add('+');
//        return calculate(q);
//    }
//
//    private static int calculate(Queue<Character> q) {
//        int num = 0;
//        Stack<Integer> s = new Stack<>();
//        char sign = '+';
//        while (!q.isEmpty()) {
//            char c = q.poll();
//            if (c == '(') {
//                num = calculate(q);
//            } else if (Character.isDigit(c)) {
//                num = num * 10 + (c - '0');
//            } else {
//                if (sign == '+') {
//                    s.push(num);
//                } else if (sign == '-') {
//                    s.push(-num);
//                } else if (sign == '*') {
//                    s.push(s.pop() * num);
//                } else if (sign == '/') {
//                    s.push(s.pop() / num);
//                }
//                if (c == ')') break;
//                sign = c;
//                num = 0;
//            }
//        }
//        int result = 0;
//        for (int val : s) {
//            result += val;
//        }
//        return result;
//    }
}
