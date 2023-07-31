package com.dtchesno.kata.dp;

import java.util.Arrays;

public class Robot {

    // n = m = 2
    // i = 2, j = 2
    // 6 3 1
    // 3 2 1
    // 1 1 1
    public static int count(int n, int m) {
//        int[][] mem = new int[n + 1][m +1];
//
//        for (int[] a : mem) {
//            Arrays.fill(a, -1);
//        }
//        return dp(0, 0, mem);

        // we use the fact that last column will have 1s
        // then elements of our current row will be sum of next/right element in that row (j + 1)
        // and previous value of that element (same column, down row)
        // so, we keep incrementing value by next/right value
        int[] mem = new int[m];
        Arrays.fill(mem, 1);
        for (int i = n - 2; i >= 0; i--) {
            for (int j = m - 2; j >= 0; j--) {
                mem[j] = mem[j] + mem[j + 1];
            }
        }
        return mem[0];
    }

    private static int dp(int i, int j, int[][] mem) {
        if (i == mem.length && j == mem[0].length) return 1;

        if (mem[i][j] != -1) return mem[i][j];

        int right = 0;
        if (j < mem[0].length ) {
            right = dp(i, j + 1, mem);
        }
        int down = 0;
        if (i < mem.length) {
            down = dp(i + 1, j, mem);
        }
        mem[i][j] = right + down;
        return mem[i][j];
    }
}
