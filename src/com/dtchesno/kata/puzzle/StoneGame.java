package com.dtchesno.kata.puzzle;

/**
 * TOPIC: dynamic programming
 *
 * Given list of stones with 'weight'. On each step player can take one stone from left or right.
 * Whoever has biggest overall 'weight' wins.
 * Can we predict whether 1st players wins if both players use best strategy.
 *
 * https://leetcode.com/problems/predict-the-winner/solution/
 * https://www.geeksforgeeks.org/optimal-substructure-property-in-dynamic-programming-dp-2/
 * https://www.geeksforgeeks.org/overlapping-subproblems-property-in-dynamic-programming-dp-1/
 * https://www.geeksforgeeks.org/solve-dynamic-programming-problem/
 * https://leetcode.com/problems/stone-game/solution/
 */
public class StoneGame {

    public static boolean isWin(int[] stones) {
//        int[][] mem = new int[stones.length][stones.length];
//        for (int i = 0; i < stones.length; i++) {
//            for (int j = 0; j < stones.length; j++) {
//                mem[i][j] = Integer.MIN_VALUE;
//            }
//        }
//        return advantage(stones, 0, stones.length - 1, mem) > 0;
//        return advantage(stones, mem) > 0;
        return advantage2(stones) > 0;
    }

    // recursive
    private static int advantage(int[] stones, int i, int j, int[][] mem) {
        if (i == j) {
            return stones[i];
        }
        if (mem[i][j] != Integer.MIN_VALUE) {
            return mem[i][j];
        }

        return Math.max(
            stones[i] - advantage(stones, i + 1, j, mem),
            stones[j] - advantage(stones, i, j - 1, mem)
        );
    }

    // non-recursive
    private static int advantage(int[] stones, int[][] mem) {
        int n = stones.length;
        for (int i = n - 1; i >= 0; i--) {
            for (int j = i; j < n; j++) {
                if (i == j) {
                    mem[i][j] = stones[i];
                } else {
                    mem[i][j] = Math.max(
                            stones[i] - mem[i + 1][j],
                            stones[j] - mem[i][j - 1]
                    );
                }
            }
        }
        return mem[0][n - 1];
    }

    // non-recursive; use 1-dimension array of size n
    private static int advantage2(int[] stones) {
        int n = stones.length;
        int[] mem = new int[n];
        for (int i = n - 1; i >= 0; i--) {
            for (int j = i; j < n; j++) {
                if (i == j) {
                    mem[j] = stones[i];
                } else {
                    mem[j] = Math.max(
                            stones[i] - mem[j],
                            stones[j] - mem[j - 1]
                    );
                }
            }
        }
        return mem[n - 1];
    }
}
