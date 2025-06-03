package com.dtchesno.kata.traversal;

import javafx.util.Pair;

import java.util.*;
import static java.util.Map.of;

public class Traversal {

    private static final int[][] directions = new int[][] { {0, -1}, {-1, 0}, {0, 1}, {1, 0}};

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
        q.add(new int[] {i, j});
        q.add(null);
        int d = 1;

        while (!q.isEmpty()) {
            int[] top = q.poll();

            if (top == null) continue;

            for (int[] dir : directions) {
                int i1 = top[0] + dir[0];
                int j1 = top[1] + dir[1];

                if (i1 < 0 || i1 >= grid.length || j1 < 0 || j1 >= grid[0].length || grid[i1][j1] != emptyLand) continue;

                q.add(new int[] {i1, j1});
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

        var start = new int[] {0, 0, 0, K};
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

                int[] candidate = new int[] {i1, j1, d + 1, k1};

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
        dfa.add(of(Literal.DIGIT, 2, Literal.DOT, 3, Literal.E,6));
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

        final int[][] directions = new int[][] { {-1, -1}, {-1, 0}, {-1, 1}, {0, -1}, {0, 1}, {1, -1}, {1, 0}, {1, 1} };

        // row, col
        Queue<int[]> q = new LinkedList<>();
        q.add(new int[] {0, 0});

        // current distance from 0,0; and guard against revisit
        grid[0][0] = 1;

        while (!q.isEmpty()) {
            int[] top = q.poll();
            int row = top[0];
            int col = top[1];
            int distance = grid[row][col];

            if (row == grid.length - 1 && col == grid[0].length - 1) return distance;

            for (int[] d : directions) {
                int i = row + d[0];
                int j = col + d[1];

                if (i < 0 || i >= grid.length || j < 0 || j >= grid[0].length || grid[i][j] != 0) continue;

                q.add(new int[] {i, j});
                grid[i][j] = distance + 1;
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

        int islandNumber = 1;
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
        Set<Pair<Integer, Integer>> seen = new HashSet<>();
        Pair<Integer, Integer> start = new Pair(i, j);
        q.add(start);
        seen.add(start);
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
                        || seen.contains(candidate)
                        || grid[i1][j1] != 1) continue;

                size++;
                grid[i1][j1] = islandNumber;
                q.add(candidate);
                seen.add(candidate);
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
}
