package com.dtchesno.kata.puzzle;

public class Sudoku {

    private static class Board {
        int dimension;
        int[][] m;
        int freecount;

        // aux structs
        Point[] move;
        boolean[] possible;
        boolean[] bestPossible;

        Board(int[][] m, int dimension) {
            this.dimension = dimension;
            this.m = new int[dimension][dimension];
            this.move = new Point[dimension * dimension];
            this.possible = new boolean[dimension + 1];
            this.bestPossible = new boolean[dimension + 1];

            freecount = dimension * dimension;
            for (int i = 0; i < dimension; i++) {
                for (int j = 0; j < dimension; j++) {
                    this.m[i][j] = m[i][j];
                    if (m[i][j] != 0) {
                        freecount--;
                    }
                }
            }
        }
    }

    private static class Point {
        int i, j;

        Point(int i, int j) {
            this.i = i;
            this.j = j;
        }
    }

    public static int[][] solve(int[][] m, int dimension) {
        int[] a = new int[dimension * dimension];
        Board board = new Board(m, dimension);
        backtrack(a, 0, board);
        return board.m;
    }

    private static boolean isFinished = false;

    private static void backtrack(int[] a, int k, Board board) {
        if (isSolution(a, k, board)) {
            processSolution(a, k, board);
            return;
        }

        k++;
        int c[] = new int[board.dimension];
        int ncandidates = getCandidates(a, k, c, board);
        for (int i = 0; i < ncandidates; i++) {
            a[k] = c[i];
            makeMove(a, k, board);
            backtrack(a, k, board);
            if (isFinished) {
                return;
            }
            unmakeMove(a, k, board);
        }
    }

    private static boolean isSolution(int[] a, int k, Board board) {
        return board.freecount == 0;
    }

    private static void makeMove(int[] a, int k, Board board) {
        board.m[board.move[k].i][board.move[k].j] = a[k];
        board.freecount--;
    }

    private static void unmakeMove(int[] a, int k, Board board) {
        board.m[board.move[k].i][board.move[k].j] = 0;
        board.freecount++;
    }

    private static int getCandidates(int[] a, int k, int[] c, Board board) {
        int ncandidates = 0;
        Point move = getNextMove(board);
        if (move.i < 0 || move.j < 0) {
            return 0;
        }
        board.move[k] = move;

        // this is done as part of getNextMove
        //getPossible(move, board);
        for (int value = 1; value < board.bestPossible.length; value++) {
            if (board.bestPossible[value]) {
                c[ncandidates++] = value;
            }
        }
        return ncandidates;
    }

    private static Point getNextMove(Board board) {
        // use most constrained to reduce number of paths
        int iMin = -1;
        int jMin = -1;
        int countMin = board.dimension + 100;
        Point move = new Point(-1, -1);
        for (int i = 0; i < board.dimension; i++) {
            for (int j = 0; j < board.dimension; j++) {
                if (board.m[i][j] != 0) {
                    continue;
                }
                move.i = i;
                move.j = j;
                int count = getPossible(move, board);
                if (count < countMin && count > 0) {
                    countMin = count;
                    iMin = i;
                    jMin = j;
                    for (int n = 0; n < board.possible.length; n++) {
                        board.bestPossible[n] = board.possible[n];
                    }
                }
            }
        }
        move.i = iMin;
        move.j = jMin;
        return move;
    }

    private static int getPossible(Point move, Board board) {
        int count = 0;
        for (int value = 1; value < board.possible.length; value++) {
            if (!checkRow(board, move.i, move.j, value)
                    || !checkCol(board, move.i, move.j, value)
                    || !checkRegion(board, move.i, move.j, value)) {
                board.possible[value] = false;
                continue;
            }
            count++;
            board.possible[value] = true;
        }
        return count;
    }

    private static boolean checkRow(Board board, int pi, int pj, int value) {
        for (int j = 0; j < board.dimension; j++) {
            if (board.m[pi][j] == value) {
                return false;
            }
        }
        return true;
    }

    private static boolean checkCol(Board board, int pi, int pj, int value) {
        for (int i = 0; i < board.dimension; i++) {
            if (board.m[i][pj] == value) {
                return false;
            }
        }
        return true;
    }

    private static boolean checkRegion(Board board, int pi, int pj, int value) {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (board.m[i + pi - (pi % 3)][j + pj - (pj % 3)] == value) {
                    return false;
                }
            }
        }
        return true;
    }

    // debug aux function
//    private static boolean hasDuplicates(Board board) {
//        for (int i = 0; i < board.dimension; i++) {
//            for (int j = 0; j < board.dimension; j++) {
//                if (board.m[i][j] == 0) {
//                    return false;
//                }
//                int temp = board.m[i][j];
//                board.m[i][j] = -1;
//                if (!checkRow(board, i, j, temp)
//                        || !checkCol(board, i, j, temp)
//                        || !checkRegion(board, i, j, temp)) {
//                    System.out.println("DUPLICATE: (" + i + ", " + j + ")");
//                    printBoard(board);
//                    checkRow(board, i, j, temp);
//                    checkCol(board, i, j, temp);
//                    checkRegion(board, i, j, temp);
//                    board.m[i][j] = temp;
//                    return true;
//                }
//                board.m[i][j] = temp;
//            }
//        }
//        return false;
//    }

    private static void processSolution(int[] a, int k, Board board) {
        //printBoard(board);
        isFinished = true;
    }

    private static void printBoard(Board board) {
        for (int i = 0; i < board.dimension; i++) {
            for (int j = 0; j < board.dimension; j++) {
                System.out.print(board.m[i][j]);
                if (j % 3 == 2) {
                    System.out.print(" | ");
                } else {
                    System.out.print(" ");
                }
            }
            if (i % 3 == 2) {
                System.out.println("\n-----------------------");
            } else {
                System.out.println("");
            }
        }
    }
}
