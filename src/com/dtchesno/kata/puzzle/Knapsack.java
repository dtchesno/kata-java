package com.dtchesno.kata.puzzle;

import java.util.Arrays;

public class Knapsack {
    private int weight;
    private Box[] boxes;

    public static class Box {
        public int weight = 0;
        public int cost = 0;
        public int count = 0;

        public Box(int weight, int cost) {
            this.weight = weight;
            this.cost = cost;
        }

        private int value;
    }

    public static class Distribution {
        public int weight = 0;
        public int cost = 0;
        public Box[] boxes;

        public String toString() {
            StringBuffer buf = new StringBuffer();
            for (Box b: boxes) {
                buf.append("[w=" + b.weight + ",n=" + b.count + "], ");
            }
            return "Distribution: weight = " + weight + ", cost = " + cost + ", boxes = " + buf.toString();
        }
    }

    public Knapsack(int weight, Box[] boxes) {
        this.weight = weight;
        this.boxes = boxes;
    }

    public Distribution fillGreedy() {
        for (Box b: boxes) {
            b.value = (1000 * b.cost) / b.weight;
        }
        Arrays.sort(boxes, (b1, b2) -> b2.value - b1.value);

        Distribution res = new Distribution();
        int i = 0;
        while (res.weight < weight && i < boxes.length) {
            if (res.weight + boxes[i].weight <= weight) {
                res.weight += boxes[i].weight;
                res.cost += boxes[i].cost;
                boxes[i].count = 1;
            }
            i++;
        }
        res.boxes = boxes;
        return res;
    }

    public int[][] fillComplete() {
        int[][] m = new int[boxes.length][weight + 1];

        for (int j = 0; j < weight; j++) {
            m[0][j] = 0;
        }

        for (int i = 1; i < boxes.length; i++) {
            for (int j = 0; j <= weight; j++) {
                if (boxes[i].weight > j) {
                    m[i][j] = m[i - 1][j];
                } else {
                    m[i][j] = Math.max(m[i - 1][j], m[i - 1][j - boxes[i].weight] + boxes[i].cost);
                }
            }
        }

        return m;
    }
}
