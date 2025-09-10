package com.dtchesno.kata.traversal;

import javafx.util.Pair;

import java.util.*;
import java.util.stream.Collectors;

import static java.util.Map.of;

public class Traversal {

    private static final int[][] directions = new int[][]{{0, -1}, {-1, 0}, {0, 1}, {1, 0}};

    // 317. Shortest Distance from All Buildings
    // 317 https://leetcode.com/problems/shortest-distance-from-all-buildings/description/
    public static int shortestDistance(int[][] grid) {
        int minDistance = Integer.MAX_VALUE;
        int[][] distance = new int[grid.length][grid[0].length];
        int emptyLand = 0;

        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid[0].length; j++) {
                if (grid[i][j] != 1) continue;
                minDistance = shortestDistanceBfs(i, j, grid, distance, emptyLand);
                if (minDistance == Integer.MAX_VALUE) return -1;
                emptyLand--;
            }
        }
        return minDistance == Integer.MAX_VALUE ? -1 : minDistance;
    }

    private static int shortestDistanceBfs(int i, int j, int[][] grid, int[][] distance, int emptyLand) {
        int minDistance = Integer.MAX_VALUE;
        Queue<int[]> q = new LinkedList<>();
        q.add(new int[]{i, j});
        q.add(null);
        int d = 1;

        while (!q.isEmpty()) {
            int[] top = q.poll();

            if (top == null) continue;

            for (int[] dir : directions) {
                int i1 = top[0] + dir[0];
                int j1 = top[1] + dir[1];

                if (i1 < 0 || i1 >= grid.length || j1 < 0 || j1 >= grid[0].length || grid[i1][j1] != emptyLand)
                    continue;

                q.add(new int[]{i1, j1});
                distance[i1][j1] += d;
                minDistance = Math.min(minDistance, distance[i1][j1]);
                grid[i1][j1] = emptyLand - 1;
            }

            if (q.peek() == null) {
                d++;
                q.add(null);
            }
        }
        return minDistance;
    }


    // 296. Best Meeting Point
    // https://leetcode.com/problems/best-meeting-point/description/
    public static int minTotalDistance(int[][] grid) {
        List<Integer> rows = new ArrayList<>();
        List<Integer> cols = new ArrayList<>();

        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid[0].length; j++) {
                if (grid[i][j] == 1) {
                    rows.add(i);
                    cols.add(j);
                }
            }
        }
        Collections.sort(cols);
        return minTotalDistanceFromMedian(rows) + minTotalDistanceFromMedian(cols);
    }

    private static int minTotalDistanceFromMedian(List<Integer> l) {
        int i = 0;
        int j = l.size() - 1;
        int distance = 0;
        while (i < j) {
            distance += l.get(j--) - l.get(i++);
        }
        return distance;
    }


    // 1293. Shortest Path in a Grid with Obstacles Elimination
    // https://leetcode.com/problems/shortest-path-in-a-grid-with-obstacles-elimination/
//    public static int shortestPath(int[][] grid, int K) {
//        // row, col, distance, remaining K
//        Queue<Tuple4<Integer, Integer, Integer, Integer>> q = new LinkedList<>();
//        Set<Tuple4<Integer, Integer, Integer, Integer>> seen = new HashSet<>();
//
//        var start = new Tuple4(0, 0, 0, K);
//        q.add(start);
//        seen.add(start);
//
//        while (!q.isEmpty()) {
//            var top = q.poll();
//            int i = top.getFirst();
//            int j = top.getSecond();
//            int d = top.getThird();
//            int k = top.getFourth();
//
//            if (i == grid.length - 1 && j == grid[0].length - 1) return d;
//
//            for (int[] direction : directions) {
//                int i1 = i + direction[0];
//                int j1 = j + direction[1];
//
//                if (i1 < 0 || i1 >= grid.length || j1 < 0 || j1 >= grid[0].length) continue;
//
//                int k1 = (grid[i1][j1] == 1 ? k - 1 : k);
//                var next = new Tuple4<>(i1, j1, d + 1, k1);
//
//                if (k1 < 0 || seen.contains(next)) continue;
//
//                q.add(next);
//                seen.add(next);
//            }
//        }
//        return -1;
//    }
    public static int shortestPath(int[][] grid, int K) {
        // i, j, distance, k
        Queue<int[]> q = new LinkedList<>();
        Set<int[]> seen = new HashSet<>();

        var start = new int[]{0, 0, 0, K};
        q.add(start);

        while (!q.isEmpty()) {
            int[] top = q.poll();
            int i = top[0];
            int j = top[1];
            int d = top[2];
            int k = top[3];

            if (i == grid.length - 1 && j == grid[0].length - 1) return d;

            for (int[] direction : directions) {
                int i1 = i + direction[0];
                int j1 = j + direction[1];

                if (i1 < 0 || i1 >= grid.length || j1 < 0 || j1 >= grid[0].length) continue;

                int k1 = grid[i1][j1] == 1 ? k - 1 : k;

                int[] candidate = new int[]{i1, j1, d + 1, k1};

                if (k1 < 0 || seen.contains(candidate)) continue;

                q.add(candidate);
                seen.add(candidate);
            }
        }

        return -1;
    }

    // 65. Valid Number
    // https://leetcode.com/problems/valid-number/
    public static boolean isValidNumber(String s) {
        List<Map<Literal, Integer>> dfa = new ArrayList<>();

        // init dfa
        dfa.add(of(Literal.SIGN, 1, Literal.DIGIT, 2, Literal.DOT, 4));
        dfa.add(of(Literal.DIGIT, 2, Literal.DOT, 4));
        dfa.add(of(Literal.DIGIT, 2, Literal.DOT, 3, Literal.E, 6));
        dfa.add(of(Literal.DIGIT, 5));
        dfa.add(of(Literal.DIGIT, 5));
        dfa.add(of(Literal.DIGIT, 5, Literal.E, 6));
        dfa.add(of(Literal.SIGN, 7, Literal.DIGIT, 8));
        dfa.add(of(Literal.DIGIT, 8));
        dfa.add(of(Literal.DIGIT, 8));

        int state = 0;
        for (char c : s.toCharArray()) {
            Literal element = null;
            if (c >= '0' && c <= '9') {
                element = Literal.DIGIT;
            } else if (c == '-' || c == '+') {
                element = Literal.SIGN;
            } else if (c == '.') {
                element = Literal.DOT;
            } else if (c == 'e' || c == 'E') {
                element = Literal.E;
            } else {
                return false;
            }
            Integer newState = dfa.get(state).get(element);
            if (newState == null) return false;
            state = newState;
        }

        return (state == 2) || (state == 3) || (state == 5) || (state == 8);
    }

    private enum Literal {
        DIGIT,
        SIGN,
        DOT,
        E
    }

    // 1091. Shortest Path in Binary Matrix
    // https://leetcode.com/problems/shortest-path-in-binary-matrix
    public static int shortestPathBinaryMatrix(int[][] grid) {
        if (grid[0][0] != 0 || grid[grid.length - 1][grid[0].length - 1] != 0) return -1;

        final int[][] directions = new int[][]{{-1, -1}, {-1, 0}, {-1, 1}, {0, -1}, {0, 1}, {1, -1}, {1, 0}, {1, 1}};

        Queue<int[]> q = new LinkedList<>();
        q.add(new int[] {0, 0});
        q.add(null);
        int distance = 1;
        grid[0][0] = 1;

        while (!q.isEmpty()) {
            int[] top = q.poll();

            if (top == null) continue;

            if (top[0] == grid.length - 1 && top[1] == grid[0].length - 1) return distance;

            for (int[] dir : directions) {
                int i = top[0] + dir[0];
                int j = top[1] + dir[1];

                if (i < 0 || i >= grid.length || j < 0 || j >= grid[0].length || grid[i][j] != 0) continue;

                q.add(new int[] {i, j});
                grid[i][j] = 1;
            }

            if (q.peek() == null) {
                distance++;
                q.add(null);
            }
        }

        return -1;
    }


    // 1762. Buildings With an Ocean View
    // https://leetcode.com/problems/buildings-with-an-ocean-view/
    public static int[] findBuildings(int[] heights) {
        Stack<Integer> s = new Stack<>();
        s.push(0);
        for (int i = 1; i < heights.length; i++) {
            while (!s.isEmpty() && heights[s.peek()] <= heights[i]) s.pop();
            s.add(i);
        }
        int[] result = new int[s.size()];
        for (int i = result.length - 1; i >= 0; i--) {
            result[i] = s.pop();
        }
        return result;
    }

    // 827. Making A Large Island
    // https://leetcode.com/problems/making-a-large-island
    public static int largestIsland(int[][] grid) {
        int maxArea = 0;
        Map<Integer, Integer> islands = new HashMap<>();

        int islandNumber = 2;
        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid[0].length; j++) {
                if (grid[i][j] != 1) continue;
                int area = largestIslandBfs(i, j, islandNumber, grid);
                islands.put(islandNumber, area);
                maxArea = Math.max(maxArea, area);
                islandNumber++;
            }
        }

        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid[0].length; j++) {
                if (grid[i][j] != 0) continue;
                maxArea = Math.max(maxArea, largestIslandGetAreaWithBridge(i, j, islands, grid));
            }
        }
        return maxArea;
    }

    private static int largestIslandBfs(int i, int j, int islandNumber, int[][] grid) {
        Queue<Pair<Integer, Integer>> q = new LinkedList<>();
        Pair<Integer, Integer> start = new Pair(i, j);
        q.add(start);
        grid[i][j] = islandNumber;
        int size = 1;

        while (!q.isEmpty()) {
            var top = q.poll();

            for (int[] dir : directions) {
                int i1 = top.getKey() + dir[0];
                int j1 = top.getValue() + dir[1];
                var candidate = new Pair(i1, j1);

                if (i1 < 0 || i1 == grid.length
                        || j1 < 0 || j1 == grid[0].length
                        || grid[i1][j1] != 1) continue;

                size++;
                grid[i1][j1] = islandNumber;
                q.add(candidate);
            }
        }
        return size;
    }

    private static int largestIslandGetAreaWithBridge(int i, int j, Map<Integer, Integer> islands, int[][] grid) {
        int totalArea = 0;
        Set<Integer> seen = new HashSet<>();

        for (int[] dir : directions) {
            int i1 = i + dir[0];
            int j1 = j + dir[1];

            if (i1 < 0 || i1 == grid.length || j1 < 0 || j1 == grid[0].length || seen.contains(grid[i1][j1])) continue;
            totalArea += islands.getOrDefault(grid[i1][j1], 0);
            seen.add(grid[i1][j1]);
        }
        return totalArea + 1;
    }

    // 2355. Maximum Number of Books You Can Take
    // https://leetcode.com/problems/maximum-number-of-books-you-can-take
    public static long maximumBooks(int[] books) {
        Stack<Integer> s = new Stack<>();
        long[] dp = new long[books.length];

        for (int i = 0; i < books.length; i++) {
            while (!s.isEmpty() && books[s.peek()] >= books[i] - (i - s.peek())) s.pop();

            if (s.isEmpty()) {
                dp[i] = maximumBooksCalculateSum(books, 0, i);
            } else {
                int j = s.peek();
                dp[i] = dp[j] + maximumBooksCalculateSum(books, j + 1, i);
            }
            s.push(i);
        }
        return Arrays.stream(dp).max().getAsLong();
    }

    private static long maximumBooksCalculateSum(int[] books, int l, int r) {
        long cnt = Math.min(books[r], r - l + 1);
        return (2 * books[r] - (cnt - 1)) * cnt / 2;
    }

    // 2055. Plates Between Candles
    // https://leetcode.com/problems/plates-between-candles
    // Input: s = "**|**|***|", queries = [[2,5],[5,9]]
    // Output: [2,3]
    // Explanation:
    // - queries[0] has two plates between candles.
    // - queries[1] has three plates between candles.
    public static int[] platesBetweenCandles(String s, int[][] queries) {
        int[] plates = new int[s.length()];
        int plateCount = 0;
        for (int i = 0; i < s.length(); i++) {
            plateCount += (s.charAt(i) == '*' ? 1 : 0);
            plates[i] = plateCount;
        }

        int[] lCandles = new int[s.length()];
        int pos = -1;
        for (int i = 0; i < s.length(); i++) {
            if (s.charAt(i) == '|') pos = i;
            lCandles[i] = pos;
        }

        int[] rCandles = new int[s.length()];
        pos = -1;
        for (int i = s.length() - 1; i >= 0; i--) {
            if (s.charAt(i) == '|') pos = i;
            rCandles[i] = pos;
        }

        int[] result = new int[queries.length];
        for (int i = 0; i < queries.length; i++) {
            int leftCandlePos = rCandles[queries[i][0]];
            int rightCandlePos = lCandles[queries[i][1]];

            if (leftCandlePos == -1 || rightCandlePos == -1 || leftCandlePos >= rightCandlePos) {
                result[i] = 0;
            } else {
                result[i] = plates[rightCandlePos] - plates[leftCandlePos];
            }
        }
        return result;
    }


    // 2193. Minimum Number of Moves to Make Palindrome
    // https://leetcode.com/problems/minimum-number-of-moves-to-make-palindrome
    public static int minMovesToMakePalindrome(String s) {
        int swaps = 0;
        int left = 0;
        int right = s.length() - 1;
        char[] chars = s.toCharArray();

        while (left < right) {
            int l = left;
            int r = right;

            while (chars[l] != chars[r]) r--;

            // unique/median char
            if (l == r) {
                swap(chars, l, l + 1);
                swaps++;
                continue;
            }

            for (int i = r; i < right; i++) {
                swap(chars, i, i + 1);
                swaps++;
            }
            left++;
            right--;
        }

        return swaps;
    }


    private static void swap(char[] arr, int left, int right) {
        char temp = arr[left];
        arr[left] = arr[right];
        arr[right] = temp;
    }


    // 140. Word Break II
    // https://leetcode.com/problems/word-break-ii
    public static List<String> wordBreak(String s, List<String> wordDict) {
        List<List<String>> result = new ArrayList<>();
        wordBreakBacktrack(s, 0, wordDict, new ArrayList<>(), result);
        return result.stream().map(list -> String.join(" ", list)).collect(Collectors.toList());
    }

    private static void wordBreakBacktrack(String s, int offset, List<String> wordDict, List<String> acc, List<List<String>> result) {
        if (offset == s.length()) {
            result.add(new ArrayList<>(acc));
            return;
        }

        for (String w : wordDict) {
            if (!s.substring(offset).startsWith(w)) continue;
            acc.add(w);
            wordBreakBacktrack(s, offset + w.length(), wordDict, acc, result);
            acc.remove(acc.size() - 1);
        }
    }


    // 994. Rotting Oranges
    // https://leetcode.com/problems/rotting-oranges
    public static int orangesRotting(int[][] grid) {
        int freshCount = 0;
        Queue<int[]> q = new LinkedList<>();

        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid[0].length; j++) {
                if (grid[i][j] == 1) {
                    freshCount++;
                } else if (grid[i][j] == 2) {
                    q.add(new int[] {i, j});
                }
            }
        }

        if (freshCount == 0) return 0;

        q.add(null);
        int minutes = 0;
        while (!q.isEmpty()) {
            int[] top = q.poll();

            if (top == null) continue;

            for (int[] dir : directions) {
                int i = top[0] + dir[0];
                int j = top[1] + dir[1];

                if (i >= 0 && i < grid.length && j >= 0 && j < grid[0].length && grid[i][j] == 1) {
                    q.add(new int[] {i,j});
                    grid[i][j] = 2;
                    freshCount--;
                }
            }

            if (q.peek() == null) {
                q.add(null);
                minutes++;
            }
        }

        return freshCount == 0 ? minutes - 1 : -1;
    }

    // 472. Concatenated Words
    // https://leetcode.com/problems/concatenated-words/
    public static List<String> findAllConcatenatedWordsInADict(String[] words) {
        List<String> result = new ArrayList<>();
        Set<String> dict = new HashSet<>(Arrays.asList(words));
        for (String w : words) {
            boolean[] visited = new boolean[w.length()];
            if (findAllConcatenatedWordsInADictDfs(w, 0, dict, visited)) {
                result.add(w);
            }
        }
        return result;
    }

    private static boolean findAllConcatenatedWordsInADictDfs(String w, int offset, Set<String> dict, boolean[] visited) {
        if (offset == w.length()) return true;
        if (offset > w.length()) return false;
        if (visited[offset]) return false;

        for (int i = offset + 1; i <= w.length(); i++) {
            String candidate = w.substring(offset, i - (offset == 0 ? 1 : 0));
            if (dict.contains(candidate)
                    && findAllConcatenatedWordsInADictDfs(w, offset + candidate.length(), dict, visited)) {
                return true;
            }
        }
        visited[offset] = true;
        return false;
    }

    // 2100. Find Good Days to Rob the Bank
    // https://leetcode.com/problems/find-good-days-to-rob-the-bank/
    public static List<Integer> goodDaysToRobBank(int[] security, int time) {
        int[] left = new int[security.length];
        for (int i = 1; i < security.length; i++) {
            if (security[i] <= security[i - 1]) left[i] = left[i - 1] + 1;
        }

        int[] right = new int[security.length];
        for (int i = security.length - 2; i > 0; i--) {
            if (security[i + 1] >= security[i]) right[i] = right[i + 1] + 1;
        }

        List<Integer> result = new ArrayList<>();
        for (int i = time; i < security.length - time; i++) {
            if (left[i] >= time && right[i] >= time) result.add(i);
        }
        return result;
    }
}