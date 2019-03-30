package com.dtchesno.kata.dp;

import java.util.Arrays;
import java.util.List;

public class Solution {

    // TODO: add description
    public static int minMessiness(List<String> words, int lineLen) {
        int[] M = new int[words.size()];
        Arrays.fill(M, Integer.MAX_VALUE);

        // calculate M[0] - ends on 0th word
        int space = lineLen - words.get(0).length();
        M[0] = space * space;

        // calculate M for all 'end' words (1-size) based on prev.calculations;
        // if M[i] is optimal then M[i-1] should be optimal too;
        // we try to combine current i-th word with preceding, calculating current line M and check M[i-...]
        for (int i = 1; i < words.size(); i++) {
            space = lineLen - words.get(i).length();
            M[i] = M[i - 1] + space * space;
            for (int j = i - 1; j >= 0; j--) {
                space -= (words.get(j).length() + 1);
                if (space < 0) {
                    break;
                }
                M[i] = Math.min(M[i], space * space + (j - 1 >= 0 ? M[j - 1] : 0));
            }
        }
        return M[words.size() - 1];
    }

    public static int longestNondecreasingSeq(int[] array) {
        int[] length = new int[array.length];
        int max = 1;
        length[0] = 1;
        for (int i = 1; i < array.length; i++) {
            for (int j = 0; j < i; j++) {
                if (array[j] <= array[i]) {
                    length[i] = Math.max(length[i], length[j] + 1);
                }
            }
            max = Math.max(max, length[i]);
        }
        return max;
    }

    // items[number][2] - weight, value
    public static int knapsack(int maxWeight, int[][] items) {
        int[][] mem = new int[items.length][maxWeight + 1];
        for (int[] arr: mem) {
            Arrays.fill(arr, -1);
            //arr[0] = 0;
        }
        return knapsackDP(maxWeight, items.length - 1, items, mem);
    }

    // capacity - remaining capacity; k - 0..k range of items, initially items.length - 1
    private static int knapsackDP(int capacity, int k, int[][] items, int[][] mem) {
        if (k < 0) {
            return 0;
        }
        if (mem[k][capacity] == -1) {
            mem[k][capacity] = Math.max(
                    capacity < items[k][0]
                        ? 0
                        : items[k][1] + knapsackDP(capacity - items[k][0], k - 1, items, mem),
                    knapsackDP(capacity, k - 1, items, mem)
            );
        }
        return mem[k][capacity];
    }

    public static int maxMatrixProduct(int[][] matrix) {
        int[][][] mem = new int[matrix.length][matrix.length][];
        return maxMatrixProductDP(matrix, 0, 0, mem)[0];
    }

    // return int[2] max positive & min negative results
    private static int[] maxMatrixProductDP(int[][] matrix, int i, int j, int[][][] mem) {
        if (i >= matrix.length || j >= matrix.length) {
            return new int[] {Integer.MIN_VALUE, Integer.MAX_VALUE};
        }
        if (i == matrix.length - 1 && j == matrix.length - 1) {
            return matrix[i][j] >= 0 ? new int[] { matrix[i][j], 0 } : new int[] { 0, matrix[i][j]};
        }

        if (mem[i][j] == null) {
            int[] right = maxMatrixProductDP(matrix, i, j + 1, mem);
            int[] down = maxMatrixProductDP(matrix, i + 1, j, mem);

            int current = matrix[i][j];
            if (current >= 0) {
                mem[i][j] = new int[] {
                        current * Math.max(right[0], down[0]),
                        current * Math.min(right[1], down[1])
                };
            } else {
                mem[i][j] = new int[] {
                        current * Math.min(right[1], down[1]),
                        current * Math.max(right[0], down[0])
                };
            }
        }
        return mem[i][j];
    }

    public static int minChange(int sum, int[] coins) {
        int[] mem = new int[sum + 1];
        Arrays.fill(mem, Integer.MAX_VALUE);
        for (int c: coins) {
            if (c >= mem.length) {
                break;
            }
            mem[c] = 1;
        }
        return minChangeDP(sum, coins, mem);
    }

    private static int minChangeDP(int sum, int[] coins, int[] mem) {
        if (sum == 0) {
            return 0;
        }
        if (mem[sum] == Integer.MAX_VALUE) {
            for (int c: coins) {
                if (c > sum) {
                    break;
                }
                mem[sum] = Math.min(mem[sum], 1 + minChangeDP(sum - c, coins, mem));
            }
        }
        return mem[sum];
    }
}
