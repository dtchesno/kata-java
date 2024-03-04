package com.dtchesno.kata.traversal;

import javafx.util.Pair;

import java.util.*;

public class Traversal {

    private static final int[][] directions = new int[][] { {0, -1}, {-1, 0}, {0, 1}, {1, 0}};

    // https://leetcode.com/problems/shortest-distance-from-all-buildings/description/
    public static int shortestDistance(int[][] grid) {
        int minDistance = Integer.MAX_VALUE;
        int[][] partial = new int[grid.length][grid[0].length];
        int emptyLandValue = 0;
        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid[0].length; j++) {
                if (grid[i][j] != 1) {
                    continue;
                }
                minDistance = shortestDistanceBfs(grid, i, j, partial, emptyLandValue);
                if (minDistance == Integer.MAX_VALUE) {
                    return -1;
                }
                emptyLandValue--;
            }
        }
        return minDistance;
    }

    private static int shortestDistanceBfs(int[][] grid, int i, int j, int[][] partial, int emptyLandValue) {
        int minDistance = Integer.MAX_VALUE;
        Queue<Pair<Integer, Integer>> q = new LinkedList<>();
        q.add(new Pair(i, j));
        q.add(null);
        int distance = 1;
        while (!q.isEmpty() && q.peek() != null) {
            Pair<Integer, Integer> top = q.poll();
            for (int[] dir : directions) {
                int i1 = top.getKey() + dir[0];
                int j1 = top.getValue() + dir[1];
                if (i1 < 0 || i1 >= grid.length || j1 < 0 || j1 >= grid[0].length || grid[i1][j1] != emptyLandValue) {
                    continue;
                }
                grid[i1][j1] = emptyLandValue - 1;
                q.add(new Pair(i1, j1));
                partial[i1][j1] += distance;
                minDistance = Math.min(minDistance, partial[i1][j1]);
            }
            if (q.peek() == null) {
                q.remove();
                q.add(null);
                distance++;
            }
        }
        return minDistance;
    }

    public static int minTotalDistance(int[][] grid) {
        List<Integer> rows = minTotalDistanceCollectRows(grid);
        List<Integer> cols = minTotalDistanceCollectCols(grid);
        return minTotalDistanceFromMedian(rows) + minTotalDistanceFromMedian(cols);
    }

    private static List<Integer> minTotalDistanceCollectRows(int[][] grid) {
        List<Integer> result = new ArrayList<>();
        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid[0].length; j++) {
                if (grid[i][j] != 1) {
                    continue;
                }
                result.add(i);
            }
        }
        return result;
    }

    private static List<Integer> minTotalDistanceCollectCols(int[][] grid) {
        List<Integer> result = new ArrayList<>();
        for (int j = 0; j < grid[0].length; j++) {
            for (int i = 0; i < grid.length; i++) {
                if (grid[i][j] != 1) {
                    continue;
                }
                result.add(j);
            }
        }
        return result;
    }

    private static int minTotalDistanceFromMedian(List<Integer> list) {
        int i = 0;
        int j = list.size() - 1;
        int distance = 0;
        while (i < j) {
            distance += list.get(j--) - list.get(i++);
        }
        return distance;
    }

    // https://leetcode.com/problems/shortest-path-in-a-grid-with-obstacles-elimination/
    public static int shortestPath(int[][] grid, int K) {
        Queue<ShortestPathState> q = new LinkedList<>();
        ShortestPathState start = new ShortestPathState(0, 0, K, 0);
        q.add(start);
        Set<ShortestPathState> seen = new HashSet<>();
        seen.add(start);
        while (!q.isEmpty()) {
            ShortestPathState top = q.poll();
            if (top.i == grid.length - 1 && top.j == grid[0].length - 1) {
                return top.steps;
            }
            for (int[] dir : directions) {
                int i0 = top.i + dir[0];
                int j0 = top.j + dir[1];
                if (i0 < 0 || i0 >= grid.length || j0 < 0 || j0 >= grid[0].length) {
                    continue;
                }
                int k = top.k - (grid[i0][j0] == 1 ? 1 : 0);
                ShortestPathState next = new ShortestPathState(i0, j0, k, top.steps + 1);
                if (k < 0 || seen.contains(next)) {
                    continue;
                }
                q.add(next);
                seen.add(next);
            }
        }
        return -1;
    }

    private static class ShortestPathState {
        int i;
        int j;
        int k;
        int steps;

        private ShortestPathState(int i, int j, int k, int steps) {
            this.i = i;
            this.j = j;
            this.k = k;
            this.steps = steps;
        }

        @Override
        public int hashCode() {
            return Objects.hash(i, j, k);
        }

        @Override
        public boolean equals(Object other) {
            if (!(other instanceof ShortestPathState)) {
                return false;
            }
            ShortestPathState that = (ShortestPathState) other;
            return i == that.i && j == that.j && k == that.k;
        }
    }
}
