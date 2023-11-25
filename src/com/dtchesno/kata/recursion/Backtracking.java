package com.dtchesno.kata.recursion;

import java.util.ArrayList;
import java.util.Arrays;
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

    // leetcode 39 - https://leetcode.com/problems/combination-sum/description/
    // backtracking vs DP - we look for ALL unique combinations
    public List<List<Integer>> combinationSum(int[] candidates, int target) {
        List<List<Integer>> result = new ArrayList<>();
        List<Integer> path = new ArrayList<>();
        Arrays.sort(candidates);
        backtrackCombinationSum(candidates, target, path, result);
        return result;
    }

    private static void backtrackCombinationSum(int[] candidates, int target, List<Integer> path, List<List<Integer>> result) {
        if (target == 0) {
            result.add(new ArrayList<Integer>(path));
            return;
        }

        for (int c : candidates) {
            if (c > target) {
                continue;
            }
            if (!path.isEmpty() && path.get(path.size() - 1) > c) {
                continue;
            }
            path.add(c);
            backtrackCombinationSum(candidates, target - c, path, result);
            path.remove(path.size() - 1);
        }
    }

    // leetcode 79 - https://leetcode.com/problems/word-search/description/
    // Using backtrack approach since we need to 'test' all possible start points before we found the word exists
    // or return false if not.
    // Since we need only get true or false - we can optimize w/o building candidates explicitly and iterate over,
    // by simply call method recursively for possible candidate - tru-or-false.
    public static boolean wordSearchExist(char[][] board, String word) {
        for (int row = 0; row < board.length; row++) {
            for (int col = 0; col < board[0].length; col++) {
                if (wordSearchBacktrack(word, 0, board, row, col)) return true;
            }
        }
        return false;
    }

    private static boolean wordSearchBacktrack(String word, int pos, char[][] board, int row, int col) {
        if (pos == word.length()) return true;

        if (row < 0 || row >= board.length || col < 0 || col >= board[0].length) return false;

        if (word.charAt(pos) != board[row][col]) return false;

        board[row][col] = '$';

        if (wordSearchBacktrack(word, pos + 1, board, row - 1, col)
                || wordSearchBacktrack(word, pos + 1, board, row + 1, col)
                || wordSearchBacktrack(word, pos + 1, board, row, col - 1)
                || wordSearchBacktrack(word, pos + 1, board, row, col + 1)) {
            return true;
        }

        board[row][col] = word.charAt(pos);
        return false;
    }
}
