package com.dtchesno.kata.test;

import com.dtchesno.kata.dp.DpSolution;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;

import static org.junit.Assert.*;

public class DpTest {

    @Before
    public void setUp() throws Exception {
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void testMinMessiness() {
        assertEquals(70, DpSolution.minMessiness(
                Arrays.asList(("I have inserted a large number of new examples from the papers "
                        + "for the Mathematical Tripos during the last twenty years, which should be "
                        + "useful to Cambridge students.").split(" ")),
                36));
        assertEquals(36, DpSolution.minMessiness(Arrays.asList(
                "aaa bbb c d ee ff ggggggg".split(" ")), 11));
    }

    @Test
    public void testLongestNondecreasingSeq() {
        assertEquals(4, DpSolution.longestNondecreasingSeq(
                new int[] {0, 8, 4, 12, 2, 10, 6, 14, 1, 9}));
    }

    @Test
    public void testknapsack() {
        assertEquals(22, DpSolution.knapsack(5, new int[][] {
                {1, 6}, {2, 10}, {3, 12}
        }));
        assertEquals(7, DpSolution.knapsack(14, new int[][] {
                {5, 3}, {10, 5}, {6, 4}, {5, 2}
        }));
        assertEquals(8, DpSolution.knapsack(14, new int[][] {
                {5, 3}, {10, 5}, {13, 8}, {6, 4}, {5, 2}, {5, 3}
        }));
    }

    @Test
    public void testMaxMatrixProduct() {
        assertEquals(1080, DpSolution.maxMatrixProduct(new int[][] {
                {-1, 2, 3},
                {4, 5, -6},
                {7, 8, 9}
        }));
        assertEquals(2016, DpSolution.maxMatrixProduct(new int[][] {
                {1, 2, 3},
                {4, 5, 6},
                {7, 8, 9}
        }));
    }

    @Test
    public void testMinChange() {
        int[] coins = new int[] { 1, 5, 10, 25 };
        assertEquals(1, DpSolution.minChange(1, coins));
        assertEquals(3, DpSolution.minChange(3, coins));
        assertEquals(2, DpSolution.minChange(6, coins));
        assertEquals(3, DpSolution.minChange(7, coins));
        assertEquals(4, DpSolution.minChange(32, coins));
    }

    @Test
    public void testMinDistance() {
        assertEquals(3, DpSolution.minDistance("horse", "ros"));
        assertEquals(5, DpSolution.minDistance("intention", "execution"));
    }

    @Test
    public void testDeleteDistance() {
        assertEquals(4, DpSolution.deleteDistance("horse", "ros"));
        assertEquals(2, DpSolution.deleteDistance("sea", "eat"));
    }
}
