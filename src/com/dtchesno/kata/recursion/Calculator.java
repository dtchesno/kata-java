package com.dtchesno.kata.recursion;

import com.intellij.util.xml.converters.values.CharacterValueConverter;

import java.util.LinkedList;
import java.util.Queue;
import java.util.Stack;

// 772. Basic Calculator III
// https://leetcode.com/problems/basic-calculator-iii/
public class Calculator {

    public int calculate3(String s) {
        Queue<Character> q = new LinkedList<>();
        for (var c : s.toCharArray()) {
            if (!Character.isWhitespace(c)) {
                q.add(c);
            }
        }
        return calculate3(q);
    }

    private int calculate3(Queue<Character> q) {
        Stack<Integer> s = new Stack<>();
        int value = 0;
        char sign = '+';

        while (!q.isEmpty()) {
            char c = q.poll();

            if (c == '(') {
                value = calculate3(q);
            } else if (c == ')') {
                break;
            } else if (Character.isDigit(c)) {
                value = value * 10 + (c - '0');
            } else {
                calculate3PushValue(s, sign, value);
                value = 0;
                sign = c;
            }
        }
        calculate3PushValue(s, sign, value);

        int result = 0;
        while (!s.isEmpty()) {
            result += s.pop();
        }
        return result;
    }

    private static void calculate3PushValue(Stack<Integer> s, char sign, int value) {
        if (sign == '+') {
            s.push(value);
        } else if (sign == '-') {
            s.push(-value);
        } else if (sign == '*') {
            s.push(s.pop() * value);
        } else if (sign == '/') {
            s.push(s.pop() / value);
        }
    }


    // 227. Basic Calculator II
    // https://leetcode.com/problems/basic-calculator-ii/
    public static int calculate2(String s) {
        Stack<Integer> stack = new Stack<>();

        int value = 0;
        char lastOp = '+';

        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);

            if (Character.isWhitespace(c)) continue;

            if (Character.isDigit(c)) {
                value = value * 10 + (c - '0');
            } else { // * / + -
                calculator2AddValue(stack, lastOp, value);
                lastOp = c;
                value = 0;
            }
        }

        // using queue with string chars and '+' added into end avoids this additional call outside of loop
        calculator2AddValue(stack, lastOp, value);

        int result = 0;
        while (!stack.isEmpty()) {
            result += stack.pop();
        }
        return result;
    }

    private static void calculator2AddValue(Stack<Integer> stack, char lastOp, int value) {
        if (lastOp == '*') {
            stack.push(stack.pop() * value);
        } else if (lastOp == '/') {
            stack.push(stack.pop() / value);
        } else {
            stack.push(lastOp == '+' ? value : -value);
        }
    }
}
