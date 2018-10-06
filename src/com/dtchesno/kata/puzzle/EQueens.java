package com.dtchesno.kata.puzzle;

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
        return solve(board, size);
    }

    private int solve(boolean[][] board, int size) {
        if (size == 0) {
            return 1;
        }
        int count = 0;
        int i = this.size - size;
        boolean[][] temp = board.clone();
        for (int j = 0; j < size; j++) {
            if (checkHorizontal(j) || checkDiagonal(i, j)) {
                continue;
            }
            temp[i][j] = true;
            count += solve(temp, size - 1);
            temp[i][j] = false;
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
