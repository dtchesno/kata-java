package com.dtchesno.kata.test;

import com.dtchesno.kata.puzzle.Hanoi;
import com.dtchesno.kata.puzzle.Knapsack;
import com.dtchesno.kata.puzzle.Knapsack.Box;
import com.dtchesno.kata.puzzle.Knapsack.Distribution;
import com.dtchesno.kata.puzzle.EQueens;
import com.dtchesno.kata.puzzle.Sudoku;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class PuzzlesTest {

    @Before
    public void setUp() throws Exception {
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void testKnapsack() {
        Box[] boxes = new Box[] { new Box(50, 100), new Box(15, 60), new Box(30, 90) };
        Knapsack pack = new Knapsack(80, boxes);
        Distribution d = pack.fillGreedy();
        ArrayList<Integer> result = new ArrayList<>();
        for (Box b: d.boxes) {
            if (b.count != 0) {
                result.add(b.weight);
            }
        }
        assertTrue(Arrays.equals(new Integer[] { 15, 30 }, result.toArray()));

        Box[] boxes2 = new Box[] {
                new Box(5, 3),
                new Box(10, 5),
                new Box(6, 4),
                new Box(5, 2)
        };
        Knapsack pack2 = new Knapsack(14, boxes2);
        int[][] m = pack2.fillComplete();
    }

    @Test
    public void testHanoi() {
        for (int size = 1; size < 10; size++) {
            Hanoi h = new Hanoi(size);
            int[] expected = new int[size];
            for (int i = 0; i < size; i++) {
                expected[i] = i + 1;
            }
            assertTrue(Arrays.equals(expected, h.move()));
        }
    }

    @Test
    public void testEQueens() {
        EQueens q = new EQueens(8);
        assertEquals(92, q.solve());
    }

    @Test
    public void testSudoku() {
        // solved
        int[][] probe = new int[][] {
                { 6, 7, 3,   8, 9, 4,   5, 1, 2 },
                { 9, 1, 2,   7, 3, 5,   4, 8, 6 },
                { 8, 4, 5,   6, 1, 2,   9, 7, 3 },

                { 7, 9, 8,   2, 6, 1,   3, 5, 4 },
                { 5, 2, 6,   4, 7, 3,   8, 9, 1 },
                { 1, 3, 4,   5, 8, 9,   2, 6, 7 },

                { 4, 6, 9,   1, 2, 8,   7, 3, 5 },
                { 2, 8, 7,   3, 5, 6,   1, 4, 9 },
                { 3, 5, 1,   9, 4, 7,   6, 2, 8 }
        };
        // task from book
        int[][] m = new int[][] {
                { 0, 0, 0,   0, 0, 0,   0, 1, 2 },
                { 0, 0, 0,   0, 3, 5,   0, 0, 0 },
                { 0, 0, 0,   6, 0, 0,   0, 7, 0 },

                { 7, 0, 0,   0, 0, 0,   3, 0, 0 },
                { 0, 0, 0,   4, 0, 0,   8, 0, 0 },
                { 1, 0, 0,   0, 0, 0,   0, 0, 0 },

                { 0, 0, 0,   1, 2, 0,   0, 0, 0 },
                { 0, 8, 0,   0, 0, 0,   0, 4, 0 },
                { 0, 5, 0,   0, 0, 0,   6, 0, 0 }
        };
        assertTrue(Arrays.deepEquals(probe, Sudoku.solve(m, 9)));
    }
}
