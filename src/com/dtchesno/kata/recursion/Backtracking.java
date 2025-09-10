package com.dtchesno.kata.recursion;

import java.util.*;

public class Backtracking {

    private static final int[][] directions = new int[][] { {0, -1}, {-1, 0}, {0, 1}, {1, 0}};

    // 301. Remove Invalid Parentheses
    // https://leetcode.com/problems/remove-invalid-parentheses/ (hard) [backtracking, bfs]
    public static List<String> removeInvalidParentheses(String s) {
        int left = 0;
        int right = 0;

        for (char c : s.toCharArray()) {
            if (c == '(') {
                left++;
            } else if (c == ')') {
                if (left > 0) {
                    left--;
                } else {
                    right++;
                }
            }
        }
        List<String> result = new ArrayList<>();
        removeInvalidParenthesesBT(new StringBuilder(s), 0, 0, left, right, result);
        return result;
    }

    private static void removeInvalidParenthesesBT(StringBuilder sb, int offset, int balance, int left, int right, List<String> result) {
        if (offset == sb.length()) {
            if (balance == 0) result.add(sb.toString());
            return;
        }

        char c = sb.charAt(offset);
        if (c == '(') {
            removeInvalidParenthesesBT(sb, offset + 1, balance + 1, left, right, result);
            if (left > 0) {
                sb.deleteCharAt(offset);
                removeInvalidParenthesesBT(sb, offset, balance, left - 1, right, result);
                sb.insert(offset, c);
            }
        } else if (c == ')') {
            if (balance > 0) {
                removeInvalidParenthesesBT(sb, offset + 1, balance - 1, left, right, result);
            }
            if (right > 0) {
                sb.deleteCharAt(offset);
                removeInvalidParenthesesBT(sb, offset, balance, left, right - 1, result);
                sb.insert(offset, c);
            }
        } else {
            removeInvalidParenthesesBT(sb, offset + 1, balance, left, right, result);
        }
    }


    // 39. Combination Sum
    // https://leetcode.com/problems/combination-sum/description/
    // backtracking vs DP - we look for ALL unique combinations
    public static List<List<Integer>> combinationSum(int[] candidates, int target) {
        List<List<Integer>> result = new ArrayList<>();
        List<Integer> acc = new ArrayList<>();
        Arrays.sort(candidates);
        combinationSumBT(candidates, target, acc, result);
        return result;
    }

    private static void combinationSumBT(int[] candidates, int target, List<Integer> acc, List<List<Integer>> result) {
        if (target == 0) {
            result.add(new ArrayList<>(acc));
            return;
        }

        for (int c : candidates) {
            if (c > target) break;
            if (!acc.isEmpty() && c < acc.get(acc.size() - 1)) continue;
            acc.add(c);
            combinationSumBT(candidates, target - c, acc, result);
            acc.remove(acc.size() - 1);
        }
    }

    // 79. Word Search
    // leetcode 79 - https://leetcode.com/problems/word-search/description/
    // Using backtrack approach since we need to 'test' all possible start points before we found the word exists
    // or return false if not.
    // Since we need only get true or false - we can optimize w/o building candidates explicitly and iterate over,
    // by simply call method recursively for possible candidate - true-or-false.
    public static boolean wordSearchExist(char[][] board, String word) {
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[0].length; j++) {
                if (wordSearchExistBT(board, word, 0, i, j)) return true;
            }
        }
        return false;
    }

    private static boolean wordSearchExistBT(char[][] board, String word, int offset, int i, int j) {
        if (offset == word.length()) return true;
        if (i < 0 || i == board.length || j < 0 || j == board[0].length|| board[i][j] != word.charAt(offset)) return false;

        board[i][j] = '$';
        for (int[] d : directions) {
            int i1 = i + d[0];
            int j1 = j + d[1];
            if (wordSearchExistBT(board, word, offset + 1, i1, j1)) return true;
        }
        board[i][j] = word.charAt(offset);
        return false;
    }


    // 51. N-Queens
    // https://leetcode.com/problems/n-queens/description/
    public static List<List<String>> solveNQueens(int n) {
        int[][] board = new int[n][n];
        List<List<String>> result = new ArrayList<>();
        solveNQueensBT(board, 0, result);
        return result;
    }

    private static void solveNQueensBT(int[][] board, int row, List<List<String>> result) {
        if (row == board.length) {
            result.add(buildResult(board));
            return;
        }
        List<Integer> cols = getCandidates(board, row);
        for (int col : cols) {
            board[row][col] = 1;
            solveNQueensBT(board, row + 1, result);
            board[row][col] = 0;
        }
    }

    private static List<Integer> getCandidates(int[][] board, int row) {
        List<Integer> candidates = new ArrayList<>();
        for (int col = 0; col < board.length; col++) {
            if (isSafeTop(board, row, col) && isSafeLeft(board, row, col) && isSafeRight(board, row, col)) {
                candidates.add(col);
            }
        }
        return candidates;
    }

    private static boolean isSafeTop(int[][] board, int row, int col) {
        for (int i = 0; i < row; i++) {
            if (board[i][col] == 1) {
                return false;
            }
        }
        return true;
    }

    private static boolean isSafeLeft(int[][] board, int row, int col) {
        int i = row - 1;
        int j = col - 1;
        while (i >= 0 && j >= 0) {
            if (board[i--][j--] == 1) {
                return false;
            }
        }
        return true;
    }

    private static boolean isSafeRight(int[][] board, int row, int col) {
        int i = row - 1;
        int j = col + 1;
        while (i >= 0 && j < board.length) {
            if (board[i--][j++] == 1) {
                return false;
            }
        }
        return true;
    }

    private static List<String> buildResult(int[][] board) {
        List<String> result = new ArrayList<>();
        for (int i = 0; i < board.length; i++) {
            StringBuilder sb = new StringBuilder();
            for (int j = 0; j < board.length; j++) {
                sb.append(board[i][j] == 1 ? 'Q' : '.');
            }
            result.add(sb.toString());
        }
        return result;
    }
}
