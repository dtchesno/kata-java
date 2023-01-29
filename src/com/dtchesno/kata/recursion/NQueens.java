package com.dtchesno.kata.recursion;

import java.util.ArrayList;
import java.util.List;

// leetcode 51; https://leetcode.com/problems/n-queens/description/
//
// this is backtracking problem as we need to generate all variant as opposite to DP when we need to find single optimal solution
//  - recursion
//  - if we reached end index - this is solution
//  - otherwise: generate candidates, for each candidate - make move, backtrack recursively, unmake move
public class NQueens {
    public List<List<String>> solveNQueens(int n) {
        boolean[][] board = new boolean[n][n];
        List<List<String>> result = new ArrayList<>();
        backtrack(board, 0, result);
        return result;
    }

    private void backtrack(boolean[][] board, int j, List<List<String>> result) {
        if (j == board.length) {
            addResult(board, result);
            return;
        }

        List<Integer> candidates = getCandidates(board, j);
        for (int i : candidates) {
            board[i][j] = true;
            backtrack(board, j + 1, result);
            board[i][j] = false;
        }
    }

    private List<Integer> getCandidates(boolean[][] board, int j) {
        List<Integer> candidates = new ArrayList<>();
        for (int i = 0; i < board.length; i++) {
            if (checkHorizontal(board, i, j) && checkDiagonal(board, i, j)) {
                candidates.add(i);
            }
        }
        return candidates;
    }

    private boolean checkHorizontal(boolean[][] board, int i, int j) {
        for (int k = 0; k < j; k++) {
            if (board[i][k]) {
                return false;
            }
        }
        return true;
    }

    private boolean checkDiagonal(boolean[][] board, int i, int j) {
        for (int k = 0; k < j; k++) {
            if (i - (j - k) >= 0 && board[i - (j - k)][k]) { // up
                return false;
            }
            if (i + (j - k) < board.length && board[i + (j - k)][k]) { // down
                return false;
            }
        }
        return true;
    }

    private void addResult(boolean[][] board, List<List<String>> result) {
        List<String> solution = new ArrayList<>();
        for (int i = 0; i < board.length; i++) {
            StringBuilder sb = new StringBuilder();
            for (int j = 0; j < board.length; j++) {
                sb.append(board[i][j] ? 'Q' : '.');
            }
            solution.add(sb.toString());
        }
        result.add(solution);
    }
}
