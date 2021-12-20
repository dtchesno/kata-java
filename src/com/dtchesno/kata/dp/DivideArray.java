package com.dtchesno.kata.dp;

/**
 * TOPIC: dynamic programming
 *
 * Given array of values. We need to answer on question, can we distribute values to two sets with equal sums.
 * E.g. (1, 2, 3) - yes, (1, 2, 3, 2) - yes, (1, 2, 2) - no.
 */
public class DivideArray {

    public static boolean isSplittable(int[] values) {
        int n = values.length;

        // calculate sum
        int sum = 0;
        for (int val: values) {
            sum += val;
        }

        if (sum % 2 != 0) {
            return false;
        }

        // mem[i][j]: true if values[0..j] has subset with sum of i
        boolean[][] mem = new boolean[sum / 2 + 1][n + 1];
        for (int j = 0; j <= n; j++) {
            mem[0][j] = true;
        }
        for (int i = 1; i <= sum / 2; i++) {
            mem[i][0] = false;
        }

        for (int i = 1; i <= sum / 2; i++) {
            for (int j = 1; j <= n; j++) {
                if (i - values[j - 1] >= 0) {
                    mem[i][j] = mem[i - values[j - 1]][j -1] || mem[i][j - 1];
                } else {
                    mem[i][j] = mem[i][j - 1];
                }
            }
        }

        return mem[sum / 2][n];
    }
}
