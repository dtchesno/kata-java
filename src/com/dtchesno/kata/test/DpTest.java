package com.dtchesno.kata.test;

import com.dtchesno.kata.dp.Solution;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

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
        assertEquals(70, Solution.minMessiness(
                Arrays.asList(("I have inserted a large number of new examples from the papers "
                        + "for the Mathematical Tripos during the last twenty years, which should be "
                        + "useful to Cambridge students.").split(" ")),
                36));
        assertEquals(36, Solution.minMessiness(Arrays.asList(
                "aaa bbb c d ee ff ggggggg".split(" ")), 11));
    }

    @Test
    public void testLongestNondecreasingSeq() {
        assertEquals(4, Solution.longestNondecreasingSeq(
                new int[] {0, 8, 4, 12, 2, 10, 6, 14, 1, 9}));
    }

    @Test
    public void testknapsack() {
        assertEquals(22, Solution.knapsack(5, new int[][] {
                {1, 6}, {2, 10}, {3, 12}
        }));
        assertEquals(7, Solution.knapsack(14, new int[][] {
                {5, 3}, {10, 5}, {6, 4}, {5, 2}
        }));
    }
}
