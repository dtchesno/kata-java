package com.dtchesno.kata.recursion;

import java.util.LinkedList;
import java.util.Queue;
import java.util.Stack;

// https://leetcode.com/problems/basic-calculator-iii/
public class Calculator {

    public int calculate(String s) {
        Queue<Character> q = new LinkedList<>();
        for (char c : s.toCharArray()) {
            q.add(c);
        }
        q.add('+');
        return calculate(q);
    }

    private int calculate(Queue<Character> q) {
        Stack<Integer> s = new Stack<>();
        int num = 0;
        char sign = '+';

        while (!q.isEmpty()) {
            char c = q.poll();
            if (Character.isWhitespace(c)) {
                continue;
            }
            if (c == '(') {
                num = calculate(q);
            } else if (Character.isDigit(c)) {
                num = 10 * num + (c - '0');
            } else { // +/-/... or )
                if (sign == '+') {
                    s.push(num);
                } else if (sign == '-') {
                    s.push(-num);
                } else if (sign == '*') {
                    s.push(s.pop() * num);
                } else if (sign == '/') {
                    s.push(s.pop() / num);
                }
                if (c == ')') {
                    break;
                }
                sign = c;
                num = 0;
            }
        }

        int result = 0;
        for (int v : s) {
            result += v;
        }
        return result;
    }
}
