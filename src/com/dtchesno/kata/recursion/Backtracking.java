package com.dtchesno.kata.recursion;

import java.util.ArrayList;
import java.util.List;

public class Backtracking {
    // https://leetcode.com/problems/remove-invalid-parentheses/ (hard) [backtracking, bfs]
    public static List<String> removeInvalidParentheses(String s) {
        List<String> result = new ArrayList<>();

        // calculate max number of left/right par to remove
        int left = 0;
        int right = 0;
        for (int i = 0; i < s.length(); i++) {
            if (s.charAt(i) == '(') {
                left++;
            } else if (s.charAt(i) == ')') {
                if (left > 0) {
                    left--;
                } else {
                    right++;
                }
            }
        }

        backtrack(new StringBuilder(s), 0, 0, left, right, result);

        if (result.size() == 0) {
            result.add("");
        }

        return result;
    }

    private static void backtrack(StringBuilder sb, int pos, int balance, int leftRem, int rightRem, List<String> result) {
        if (pos == sb.length()) {
            if (balance == 0) {
                result.add(sb.toString());
            }
            return;
        }

        char c = sb.charAt(pos);
        if (c != '(' && c != ')') {
            backtrack(sb, pos + 1, balance, leftRem, rightRem, result);
        } else if (c == '(') {
            backtrack(sb, pos + 1, balance + 1, leftRem, rightRem, result);

            if (leftRem > 0) {
                sb.deleteCharAt(pos);
                backtrack(sb, pos, balance, leftRem - 1, rightRem, result);
                sb.insert(pos, c);
            }
        } else if (c == ')') {
            if (balance > 0) {
                backtrack(sb, pos + 1, balance - 1, leftRem, rightRem, result);
            }

            if (rightRem > 0) {
                sb.deleteCharAt(pos);
                backtrack(sb, pos, balance, leftRem, rightRem - 1, result);
                sb.insert(pos, c);
            }
        }
    }
}
