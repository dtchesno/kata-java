package com.dtchesno.kata.traversal;

import groovy.lang.Tuple4;
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
                minDistance = shortestDistanceBfs(i, j, grid, emptyLand, distance);
                if (minDistance == Integer.MAX_VALUE) return -1;
                emptyLand--;
            }
        }
        return minDistance == Integer.MAX_VALUE ? -1 : minDistance;
    }

    private static int shortestDistanceBfs(int i, int j, int[][] grid, int emptyLand, int[][] distance) {
        int minDistance = Integer.MAX_VALUE;
        Queue<Pair<Integer, Integer>> q = new LinkedList<>();
        q.add(new Pair(i, j));
        q.add(null);
        int d = 1;
        while (!q.isEmpty()) {
            Pair<Integer, Integer> top = q.poll();
            if (top == null) continue;
            for (int[] direction : directions) {
                int i1 = top.getKey() + direction[0];
                int j1 = top.getValue() + direction[1];
                if (i1 < 0 || i1 >= grid.length || j1 < 0 || j1 >= grid[0].length || grid[i1][j1] != emptyLand) continue;
                q.add(new Pair(i1, j1));
                distance[i1][j1] += d;
                grid[i1][j1] = emptyLand - 1;
                minDistance = Math.min(minDistance, distance[i1][j1]);
            }
            if (q.peek() == null) {
                q.add(null);
                d++;
            }
        }
        return minDistance;
    }


    // 296 https://leetcode.com/problems/best-meeting-point/description/
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

    private static int minTotalDistanceFromMedian(List<Integer> list) {
        int distance = 0;
        int i = 0;
        int j = list.size() - 1;
        while (i < j) {
            distance += list.get(j--) - list.get(i++);
        }
        return distance;
    }


    // 1293. Shortest Path in a Grid with Obstacles Elimination
    // https://leetcode.com/problems/shortest-path-in-a-grid-with-obstacles-elimination/
    public static int shortestPath(int[][] grid, int K) {
        // i, j, k, distance
        Queue<Tuple4<Integer, Integer, Integer, Integer>> q = new LinkedList<>();
        Set<Tuple4<Integer, Integer, Integer, Integer>> seen = new HashSet<>();
        var start = new Tuple4<>(0, 0, K, 0);
        q.add(start);
        seen.add(start);
        while (!q.isEmpty()) {
            var top = q.poll();
            int i = top.getFirst();
            int j = top.getSecond();
            int k = top.getThird();
            int d = top.getFourth();

            if (i == grid.length - 1 && j == grid[0].length - 1) return top.getFourth();

            for (int[] dir : directions) {
                int i1 = i + dir[0];
                int j1 = j + dir[1];

                if (i1 < 0 || i1 >= grid.length || j1 < 0 || j1 >= grid[0].length) continue;

                int k1 = grid[i1][j1] == 1 ? k - 1 : k;
                var next = new Tuple4(i1, j1, k1, d + 1);

                if (k1 < 0 || seen.contains(next)) continue;

                q.add(next);
                seen.add(next);
            }
        }
        return -1;
    }


    // 65 https://leetcode.com/problems/valid-number/
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
}
