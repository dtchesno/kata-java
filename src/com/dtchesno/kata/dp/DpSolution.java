package com.dtchesno.kata.dp;

import java.util.Arrays;
import java.util.List;

public class DpSolution {

    // find min messiness (sum of squared # of trailing spaces) of break text (provided as list of words) to lines
    // Aziz 17.11 pg327
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

    // find max length of non-decreasing seq, next element doesn't have to immediately follow previous
    // e.g. 0,8,4,12,2,10,6,14,1,9 -> [0,4,10,14] or [0,2,6,9]
    // Aziz 17.12 pg.330
    // [selected - 3]
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


    // knapsack 0-1 (each item could be taken 0 or 1 times)
    // Aziz 17.6 pg.317
    // [selected - 1]
    // items[number][2] - weight, value
    public static int knapsack(int maxWeight, int[][] items) {
        int[][] mem = new int[maxWeight + 1][items.length];
        for (int[] arr: mem) {
            Arrays.fill(arr, -1);
        }
        return knapsackDP(maxWeight, items, 0, mem);
    }

    private static int knapsackDP(int weight, int[][] items, int i, int[][] mem) {
        if (i == items.length) {
            return 0;
        }
        if (mem[weight][i] == -1) {
            mem[weight][i] = items[i][0] > weight
                    ? knapsackDP(weight, items, i + 1, mem)
                    : Math.max(
                        items[i][1] + knapsackDP(weight - items[i][0], items, i + 1, mem),
                        knapsackDP(weight, items, i + 1, mem));
        }
        return mem[weight][i];
    }


    // find path with max product from top-left to bottom-right; move allowed - right & down
    // [NOTE] don't forget about sign of values
    // byte-by-byte #3 pg.7
    // [selected - 2]
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


    // find min # of coins to change amount
    // byte-by-byte #26 pg.25
    // [selected - 1]
    public static int minChange(int sum, int[] coins) {
        int[] mem = new int[sum + 1];
        for (int coin: coins) {
            if (coin > sum) continue;
            mem[coin] = 1;
        }
        return minChangeDP(sum, coins, mem);
    }

    private static int minChangeDP(int sum, int[] coins, int[] mem) {
        if (sum == 0) {
            return 0;
        }
        if (mem[sum] == 0) {
            mem[sum] = Integer.MAX_VALUE;
            for (int coin: coins) {
                if (coin > sum) continue;
                mem[sum] = Integer.min(mem[sum], 1 + minChangeDP(sum - coin, coins, mem));
            }
        }
        return mem[sum];
    }


    // find min # of deletes to make word1 & word2 the same
    // https://leetcode.com/problems/delete-operation-for-two-strings/
    // [selected - 1]
    public static int deleteDistance(String word1, String word2) {
        char[] w1 = word1.toCharArray();
        char[] w2 = word2.toCharArray();
        int[][] mem = new int[w1.length + 1][w2.length + 1];
        for (int[] arr: mem) {
            Arrays.fill(arr, -1);
        }
        return //minDistanceDP(w1, w1.length, w2, w2. length, mem);
        w1.length + w2.length - 2 * lcs(w1, w1.length, w2, w2. length, mem);
    }

    private static int lcs(char[] word1, int len1, char[] word2, int len2, int[][] mem) {
        if (len1 == 0 || len2 == 0) {
            return 0;
        }
        if (mem[len1][len2] == -1) {
            if (word1[len1 - 1] == word2[len2 - 1]) {
                mem[len1][len2] = 1 + lcs(word1, len1 - 1, word2, len2 - 1, mem);
            } else {
                mem[len1][len2] = Math.max(
                        lcs(word1, len1 - 1, word2, len2, mem),
                        lcs(word1, len1, word2, len2 - 1, mem)
                );
            }
        }
        return mem[len1][len2];
    }

    // find min # of operations (insert, delete, replace) to make word1 & word2 the same
    // https://leetcode.com/problems/edit-distance/
    // [selected - 1]
    public static int minDistance(String word1, String word2) {
        char[] w1 = word1.toCharArray();
        char[] w2 = word2.toCharArray();
        int[][] mem = new int[w1.length + 1][w2.length + 1];
        for (int[] arr: mem) {
            Arrays.fill(arr, -1);
        }
        return minDistanceDP(w1, w1.length, w2, w2. length, mem);
    }

    private static int minDistanceDP(char[] word1, int len1, char[] word2, int len2, int[][] mem) {
        if (len1 == 0) {
            return len2;
        }
        if (len2 == 0) {
            return len1;
        }
        if (mem[len1][len2] == -1) {
            if (word1[len1 - 1] == word2[len2 - 1]) {
                mem[len1][len2] = minDistanceDP(word1, len1 - 1, word2, len2 - 1, mem);
            } else {
                // delete & insert
                int d1 = 1 + Math.min(
                    minDistanceDP(word1, len1 - 1, word2, len2, mem),
                    minDistanceDP(word1, len1, word2, len2 - 1, mem)
                );

                // replace
                int d2 = 1 + minDistanceDP(word1, len1 - 1, word2, len2 - 1, mem);

                mem[len1][len2] = Math.min(d1, d2);
            }
        }
        return mem[len1][len2];
    }

    public static int fibonacci(int index) {
        if (index == 0) {
            return 0;
        }
        if (index == 1) {
            return 1;
        }
        int f1 = 0;
        int f2 = 1;
        int f = 0;
        for (int i = 2; i <= index; i++) {
            f = f1 + f2;
            f1 = f2;
            f2 = f;
        }
        return f;
    }

    public static int squareSubMatrix(boolean[][] matrix) {
        int[][] mem = new int[matrix.length][matrix[0].length];
        for (int i = 0; i < mem.length; i++) {
            for (int j = 0; j < mem[0].length; j++) {
                mem[i][j] = -1;
            }
        }

        int max = 0;
        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix[0].length; j++) {
                max = Math.max(max, squareSubMatrix(matrix, i, j, mem));
            }
        }
        return max;
    }

    private static int squareSubMatrix(boolean[][] matrix, int i, int j, int[][] mem) {
        if (i == matrix.length || j == matrix[0].length)
            return 0;

        if (mem[i][j] != -1) {
            return mem[i][j];
        }

        mem[i][j] = matrix[i][j]
            ? 1 + Math.min(squareSubMatrix(matrix, i + 1, j + 1, mem),
                    Math.min(squareSubMatrix(matrix, i + 1, j, mem),
                            squareSubMatrix(matrix, i, j + 1, mem)))
            : 0;

        return mem[i][j];
    }
}
