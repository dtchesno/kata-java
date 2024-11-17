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
}
