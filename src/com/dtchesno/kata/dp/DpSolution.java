package com.dtchesno.kata.dp;

import javafx.util.Pair;
import org.jvnet.staxex.BinaryText;

import java.util.*;

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
    // 300. Longest Increasing Subsequence
    // https://leetcode.com/problems/longest-increasing-subsequence/
    // [selected - 3]
    public static int lengthOfLIS(int[] array) {
        List<Integer> result = new ArrayList<>();
        result.add(array[0]);
        for (int i = 1; i < array.length; i++) {
            if (array[i] > result.get(result.size() - 1)) {
                result.add(array[i]);
            } else {
                lengthOfLISInsert(array[i], result);
            }
        }
        return result.size();
    }

    private static void lengthOfLISInsert(int value, List<Integer> result) {
        int left = 0;
        int right = result.size() - 1;

        while (left < right) {
            int mid = (left + right) / 2;
            if (value == result.get(mid)) {
                return;
            } if (value < result.get(mid)) {
                right = mid;
            } else {
                left = mid + 1;
            }
        }
        result.set(left, value);
    }


    // knapsack 0-1 (each item could be taken 0 or 1 times)
    // Aziz 17.6 pg.317
    // [selected - 1]
    // items[number][2] - weight, value
    public static int knapsack(int maxWeight, int[][] items) {
        int[][] mem = new int[maxWeight + 1][items.length];
        return knapsackDP(maxWeight, items, 0, mem);
    }

    private static int knapsackDP(int weight, int[][] items, int i, int[][] mem) {
        if (i == items.length) {
            return 0;
        }

        if (mem[weight][i] != 0) {
            return mem[weight][i];
        }

        mem[weight][i] = items[i][0] <= weight
                ? Math.max(
                items[i][1] + knapsackDP(weight - items[i][0], items, i + 1, mem),
                knapsackDP(weight, items, i + 1, mem))
                : knapsackDP(weight, items, i + 1, mem);

        return mem[weight][i];
    }


    // find path with max product from top-left to bottom-right; move allowed - right & down
    // [NOTE] don't forget about sign of values
    // byte-by-byte #3 pg.7
    // [selected - 2]
    public static int maxMatrixProduct(int[][] matrix) {
        int[][][] mem = new int[matrix.length][matrix.length][];
        int len = matrix.length;
        int last = matrix[len - 1][len - 1];
        mem[len - 1][len - 1] = last >= 0 ? new int[]{last, 0} : new int[]{0, last};
        return maxMatrixProductDP(matrix, 0, 0, mem)[0];
    }

    // return int[2] max positive & min negative results
    private static int[] maxMatrixProductDP(int[][] matrix, int i, int j, int[][][] mem) {
        if (i == matrix.length || j == matrix.length) {
            return new int[]{Integer.MIN_VALUE, Integer.MAX_VALUE};
        }

        if (mem[i][j] != null) return mem[i][j];

        int current = matrix[i][j];
        int[] right = maxMatrixProductDP(matrix, i, j + 1, mem);
        int[] down = maxMatrixProductDP(matrix, i + 1, j, mem);

        mem[i][j] = (current >= 0)
                ? new int[]{current * Math.max(right[0], down[0]), current * Math.min(right[1], down[1])}
                : new int[]{current * Math.min(right[1], down[1]), current * Math.max(right[0], down[0])};
        return mem[i][j];
    }


    // 322. Coin Change
    // find min # of coins to change amount
    // https://leetcode.com/problems/coin-change/
    // byte-by-byte #26 pg.25
    // [selected - 1]
    public static int minChange(int sum, int[] coins) {
        int[] mem = new int[sum + 1];
        for (int coin : coins) {
            if (coin > sum) continue;
            mem[coin] = 1;
        }
        int n = minChangeDP(sum, coins, mem);
        return n == Integer.MAX_VALUE ? -1 : n;
    }

    private static int minChangeDP(int sum, int[] coins, int[] mem) {
        if (sum == 0) {
            return 0;
        }
        if (mem[sum] != 0) {
            return mem[sum];
        }
        mem[sum] = Integer.MAX_VALUE;
        for (int coin : coins) {
            if (coin > sum) {
                continue;
            }
            if (coin == sum) {
                mem[sum] = 1;
            }
            int n = minChangeDP(sum - coin, coins, mem);
            if (n == Integer.MAX_VALUE) {
                continue;
            }
            mem[sum] = Math.min(mem[sum], n + 1);
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
        for (int[] arr : mem) {
            Arrays.fill(arr, -1);
        }
        //return minDistanceDP(w1, w1.length, w2, w2. length, mem);
        //return w1.length + w2.length - 2 * lcs(w1, w1.length, w2, w2.length, mem);
        return deleteDistanceDP(w1, w2, 0, 0, mem);
    }

    private static int deleteDistanceDP(char[] w1, char[] w2, int pos1, int pos2, int[][] mem) {
        if (pos1 == w1.length) {
            return w2.length - pos2;
        }
        if (pos2 == w2.length) {
            return w1.length - pos1;
        }

        if (mem[pos1][pos2] != -1) {
            return mem[pos1][pos2];
        }

        if (w1[pos1] == w2[pos2]) {
            mem[pos1][pos2] = deleteDistanceDP(w1, w2, pos1 + 1, pos2 + 1, mem);
        } else {
            mem[pos1][pos2] = 1 + Math.min(
                    deleteDistanceDP(w1, w2, pos1 + 1, pos2, mem),
                    deleteDistanceDP(w1, w2, pos1, pos2 + 1, mem));
        }

        return mem[pos1][pos2];
    }


    // 1143. Longest Common Subsequence
    // https://leetcode.com/problems/longest-common-subsequence
    public static int longestCommonSubsequence(String text1, String text2) {
        int[][] mem = new int[text1.length()][text2.length()];
        for (int i = 0; i < mem.length; i++) {
            for (int j = 0; j < mem[0].length; j++) {
                mem[i][j] = -1;
            }
        }
        lcs(text1.toCharArray(), text2.toCharArray(), 0, 0, mem);
        return mem[0][0];
    }

    private static int lcs(char[] word1, char[] word2, int i, int j, int[][] mem) {
        if (i == word1.length || j == word2.length) {
            return 0;
        }

        if (mem[i][j] != -1) {
            return mem[i][j];
        }

        if (word1[i] == word2[j]) {
            mem[i][j] = 1 + lcs(word1, word2, i + 1, j + 1, mem);
        } else {
            mem[i][j] = Math.max(
                    lcs(word1, word2, i, j + 1, mem),
                    lcs(word1, word2, i + 1, j, mem));
        }
        return mem[i][j];
    }


    // 72. Edit Distance
    // find min # of operations (insert, delete, replace) to make word1 & word2 the same
    // https://leetcode.com/problems/edit-distance/
    // [selected - 1]
    public static int minDistance(String word1, String word2) {
        int[][] dp = new int[word1.length()][word2.length()];
        for (int i = 0; i < dp.length; i++) {
            Arrays.fill(dp[i], -1);
        }
        minDistanceDP(word1, word2, 0, 0, dp);
        return dp[0][0];
    }

    private static int minDistanceDP(String word1, String word2, int i, int j, int[][] dp) {
        if (i == word1.length()) return word2.length() - j;
        if (j == word2.length()) return word1.length() - i;

        if (dp[i][j] != -1) return dp[i][j];

        if (word1.charAt(i) == word2.charAt(j)) {
            dp[i][j] = minDistanceDP(word1, word2, i + 1, j + 1, dp);
        } else {
            // delete/insert
            int d1 = Math.min(
                minDistanceDP(word1, word2, i + 1, j, dp),
                minDistanceDP(word1, word2, i, j + 1, dp)
            );

            int d2 = minDistanceDP(word1, word2, i + 1, j + 1, dp);

            dp[i][j] = 1 + Math.min(d1, d2);
        }
        return dp[i][j];
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


    // https://leetcode.com/problems/russian-doll-envelopes/description/
    // plain DP exceeds time limit - use longest increasing sequence (LIS)
    public static int maxEnvelopes(int[][] envelops) {
        PriorityQueue<int[]> q = new PriorityQueue<int[]>((a, b) -> (a[0] != b[0] ? a[0] - b[0] : b[1] - a[1]));
        for (int[] e : envelops) {
            q.add(e);
        }
        int[] seq = new int[envelops.length];
        for (int i = 0; i < envelops.length; i++) {
            seq[i] = q.poll()[1];
        }
        List<Integer> result = new ArrayList<>();
        result.add(seq[0]);
        for (int i = 1; i < seq.length; i++) {
            if (seq[i] > result.get(result.size() - 1)) {
                result.add(seq[i]);
            } else {
                int j = maxEnvelopesFindLis(result, seq[i]);
                result.set(j, seq[i]);
            }
        }
        return result.size();
    }

    private static int maxEnvelopesFindLis(List<Integer> result, int val) {
        int left = 0;
        int right = result.size();
        while (left < right) {
            int mid = (left + right) / 2;
            if (val == result.get(mid)) {
                return mid;
            } else if (val < result.get(mid)) {
                right = mid;
            } else {
                left = mid + 1;
            }
        }
        return left;
    }

    // Aziz
    // (price, weight)
    public static int knapsack(int[][] clocks, int size) {
        int[][] mem = new int[clocks.length][size + 1];
        for (int i = 0; i < clocks.length; i++) {
            Arrays.fill(mem[i], -1);
        }
        return knapsackDP(clocks, 0, size, mem);
    }

    private static int knapsackDP(int[][] clocks, int i, int size, int[][] mem) {
        if (size == 0 || i == clocks.length) {
            return 0;
        }
        if (mem[i][size] != -1) {
            return mem[i][size];
        }
        if (clocks[i][1] > size) {
            mem[i][size] = knapsackDP(clocks, i + 1, size, mem);
        } else {
            mem[i][size] = Math.max(
                    knapsackDP(clocks, i + 1, size - clocks[i][1], mem) + clocks[i][0],
                    knapsackDP(clocks, i + 1, size, mem));
        }
        return mem[i][size];
    }


    // 139. Word Break
    // https://leetcode.com/problems/word-break
    public static boolean wordBreak(String s, List<String> wordDict) {
        int[] mem = new int[s.length()];
        return wordBreakDP(s, 0, wordDict, mem);
    }

    private static boolean wordBreakDP(String s, int offset, List<String> wordDict, int[] mem) {
        if (offset == s.length()) return true;
        if (mem[offset] != 0) return mem[offset] == 1;

        mem[offset] = -1;
        for (String w : wordDict) {
            if (s.startsWith(w, offset) && wordBreakDP(s, offset + w.length(), wordDict, mem)) {
                mem[offset] = 1;
                break;
            }
        }
        return mem[offset] == 1;
    }


    // 329. Longest Increasing Path in a Matrix
    // https://leetcode.com/problems/longest-increasing-path-in-a-matrix/description/
    public static int longestIncreasingPath(int[][] matrix) {
        int[][] mem = new int[matrix.length][matrix[0].length];
        for (int i = 0; i < mem.length; i++) {
            Arrays.fill(mem[i], -1);
        }
        int maxPath = Integer.MIN_VALUE;
        for (int i = 0; i < mem.length; i++) {
            for (int j = 0; j < mem[0].length; j++) {
                mem[i][j] = longestIncreasingPathDP(-1, i, j, matrix, mem);
                maxPath = Math.max(maxPath, mem[i][j]);
            }
        }
        return maxPath;
    }

    private static int longestIncreasingPathDP(int prev, int i, int j, int[][] matrix, int[][] mem) {
        if (i < 0 || i == matrix.length || j < 0 || j == matrix[0].length || matrix[i][j] <= prev) return 0;

        if (mem[i][j] != -1) return mem[i][j];

        int left = longestIncreasingPathDP(matrix[i][j], i, j - 1, matrix, mem);
        int right = longestIncreasingPathDP(matrix[i][j], i, j + 1, matrix, mem);
        int up = longestIncreasingPathDP(matrix[i][j], i - 1, j, matrix, mem);
        int down = longestIncreasingPathDP(matrix[i][j], i + 1, j, matrix, mem);
        mem[i][j] = 1 + Math.max(Math.max(left, right), Math.max(up, down));
        return mem[i][j];
    }

    // 1216. Valid Palindrome III
    // https://leetcode.com/problems/valid-palindrome-iii/description/
//    public static boolean isValidPalindrome(String s, int k) {
//        Map<Integer, Integer> mem = new HashMap<>();
//        return isValidPalindromeDP(s, 0, s.length() - 1, k, mem);
//    }
//
//    // "bacabaaa", 2
//    private static boolean isValidPalindromeDP(String s, int start, int end, int k, Map<Integer, Boolean> mem) {
//        if (start >= end) return true;
//        if (k < 0) return false;
//
//        Integer k1 = mem.get(start * 10000 + end);
//        if (k1 != null && ) return m;
//
//        boolean isValid;
//        if (s.charAt(start) == s.charAt(end)) {
//            isValid = isValidPalindromeDP(s, start + 1, end - 1, k, mem);
//        } else {
//            isValid = isValidPalindromeDP(s, start + 1, end, k - 1, mem)
//                || isValidPalindromeDP(s, start, end - 1, k - 1, mem);
//        }
//        mem.put(start * 10000 + end, isValid);
//        return isValid;
//    }

    // 494. Target Sum
    // https://leetcode.com/problems/target-sum/
    public static int findTargetSumWays(int[] nums, int target) {
        // (offset, sum), count
        Map<Pair<Integer, Integer>, Integer> mem = new HashMap<>();
        return findTargetSumWaysDP(nums, 0, 0, target, mem);
    }

    private static int findTargetSumWaysDP(int[] nums, int offset, int sum, int target, Map<Pair<Integer, Integer>, Integer> mem) {
        if (offset == nums.length) {
            return sum == target ? 1 : 0;
        }

        Pair<Integer, Integer> key = new Pair(offset, sum);
        if (mem.containsKey(key)) return mem.get(key);

        int addCount = findTargetSumWaysDP(nums, offset + 1, sum + nums[offset], target, mem);
        int subtractCount = findTargetSumWaysDP(nums, offset + 1, sum - nums[offset], target, mem);
        int count = addCount + subtractCount;
        mem.put(key, count);
        return count;
    }


    // 2328. Number of Increasing Paths in a Grid
    // https://leetcode.com/problems/number-of-increasing-paths-in-a-grid
    public static int countPaths(int[][] grid) {
        int count = 0;
        int[][] mem = new int[grid.length][grid[0].length];
        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid[0].length; j++) {
                count += countPathsDP(-1, i, j, grid, mem);
            }
        }
        return count;
    }

    private static int countPathsDP(int prev, int i, int j, int[][] grid, int[][] mem) {
        if (i < 0 || i == grid.length || j < 0 || j == grid[0].length || grid[i][j] <= prev) return 0;

        if (mem[i][j] != 0) return mem[i][j];

        int left = countPathsDP(grid[i][j], i, j - 1, grid, mem);
        int right = countPathsDP(grid[i][j], i, j + 1, grid, mem);
        int up = countPathsDP(grid[i][j], i - 1, j, grid, mem);
        int down = countPathsDP(grid[i][j], i + 1, j, grid, mem);
        mem[i][j] = 1 + left + right + up + down;
        return mem[i][j];
    }
}
