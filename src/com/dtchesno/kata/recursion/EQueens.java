package com.dtchesno.kata.recursion;

public class EQueens {
    private boolean[][] board;
    private int size;

    public EQueens(int size) {
        this.size = size;
        board = new boolean[size][size];
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                board[i][j] = false;
            }
        }
    }

    public int solve() {
        return solve(0);
    }

    private int solve(int i) {
        if (i == size) {
            return 1;
        }
        int count = 0;
        for (int j = 0; j < size; j++) {
            if (checkHorizontal(j) || checkDiagonal(i, j)) {
                continue;
            }
            board[i][j] = true;
            count += solve(i + 1);
            board[i][j] = false;
        }
        return count;
    }

//    private boolean checkVertical(int i) {
//        for (int j = 0; j < size; j++) {
//            if (board[i][j]) {
//                return true;
//            }
//        }
//        return false;
//    }

    private boolean checkHorizontal(int j) {
        for (int i = 0; i < size; i++) {
            if (board[i][j]) {
                return true;
            }
        }
        return false;
    }

    private boolean checkDiagonal(int row, int col) {
        int i = row;
        int j = col;
        while (i > 0 && j > 0) {
            i--;
            j--;
        }
        while (i < size && j < size) {
            if (board[i][j]) {
                return true;
            }
            i++;
            j++;
        }
        i = row;
        j = col;
        while (i < size - 1 && j > 0) {
            i++;
            j--;
        }
        while (i >= 0 && j < size) {
            if (board[i][j]) {
                return true;
            }
            i--;
            j++;
        }
        return false;
    }
}
