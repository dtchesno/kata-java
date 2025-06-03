package com.dtchesno.kata.test;

import com.dtchesno.kata.dp.DpSolution;
import com.dtchesno.kata.dp.Robot;
import org.junit.After;
import org.junit.Assert;
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
//        assertEquals(4, DpSolution.lengthOfLIS(new int[] {0, 8, 4, 12, 2, 10, 6, 14, 1, 9}));
//        assertEquals(4, DpSolution.lengthOfLIS(new int[] {10,9,2,5,3,7,101,18}));
//        assertEquals(4, DpSolution.lengthOfLIS(new int[] {0,1,0,3,2,3}));
//        assertEquals(1, DpSolution.lengthOfLIS(new int[] {7,7,7,7,7,7,7}));
//        assertEquals(3, DpSolution.lengthOfLIS(new int[] {4,10,4,3,8,9}));
        assertEquals(6, DpSolution.lengthOfLIS(new int[] {3,5,6,2,5,4,19,5,6,7,12}));
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
        assertEquals(3, DpSolution.minChange(11, new int[] {1,2,5}));
        assertEquals(-1, DpSolution.minChange(2, new int[] {3}));
        assertEquals(0, DpSolution.minChange(0, new int[] {1}));
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

    @Test
    public void testFibonacci() {
        assertEquals(0, DpSolution.fibonacci(0));
        assertEquals(1, DpSolution.fibonacci(1));
        assertEquals(1, DpSolution.fibonacci(2));
        assertEquals(2, DpSolution.fibonacci(3));
        assertEquals(3, DpSolution.fibonacci(4));
        assertEquals(5, DpSolution.fibonacci(5));
        assertEquals(8, DpSolution.fibonacci(6));
        assertEquals(144, DpSolution.fibonacci(12));
    }

    @Test
    public void testSquareSubMatrix() {
        assertEquals(2, DpSolution.squareSubMatrix(new boolean[][] {
                { false, true, false, false },
                { true, true, true, true },
                { false, true, true, false }}));
        assertEquals(4, DpSolution.squareSubMatrix(new boolean[][] {
                { true, true, true, true, true },
                { true, true, true, true, false },
                { true, true, true, true, false },
                { true, true, true, true, false },
                { true, false, false, false, false }}));
        assertEquals(3, DpSolution.squareSubMatrix(new boolean[][] {
                { true, true, true, true, true },
                { true, true, true, true, false },
                { true, true, true, true, false },
                { false, true, true, true, false },
                { true, false, false, false, false }}));
    }

    @Test
    public void testRobotCount() {
        System.out.println(Robot.count(2,2));
        System.out.println(Robot.count(3,3));
        System.out.println(Robot.count(3,2));
        System.out.println(Robot.count(2,3));
    }

    @Test
    public void testMaxEnvelops() {
        Assert.assertEquals(3, DpSolution.maxEnvelopes(new int[][] {{5,4},{6,4},{6,7},{2,3}}));
        Assert.assertEquals(1, DpSolution.maxEnvelopes(new int[][] {{1,1},{1,1},{1,1}}));
        Assert.assertEquals(3, DpSolution.maxEnvelopes(new int[][] {{1,3},{3,5},{6,7},{6,8},{8,4},{9,5}}));
    }

    @Test
    public void testLongestCommonSubsequence() {
        Assert.assertEquals(3, DpSolution.longestCommonSubsequence("abcde", "ace"));
        Assert.assertEquals(3, DpSolution.longestCommonSubsequence("abc", "abc"));
        Assert.assertEquals(0, DpSolution.longestCommonSubsequence("abc", "def"));
    }

    @Test
    public void testKnapsack() {
        Assert.assertEquals(695, DpSolution.knapsack(
            new int[][] {
                {65,20}, {35,8}, {245,60}, {195,55}, {65,40}, {150,70}, {275,85}, {155,25}, {120,30}, {320,65},
                {75,75}, {40,10}, {200,95}, {100,50}, {220,40}, {99,10} },
            130));
    }

    @Test
    public void testWordBreak() {
        Assert.assertTrue(DpSolution.wordBreak("leetcode", Arrays.asList("leet","code")));
        Assert.assertTrue(DpSolution.wordBreak("applepenapple", Arrays.asList("apple","pen")));
        Assert.assertFalse(DpSolution.wordBreak("catsandog", Arrays.asList("cats","dog","sand","and","cat")));
    }

    @Test
    public void testLongestIncreasingPath() {
        assertEquals(4, DpSolution.longestIncreasingPath(new int[][] {{9,9,4},{6,6,8},{2,1,1}}));
        assertEquals(4, DpSolution.longestIncreasingPath(new int[][] {{3,4,5},{3,2,6},{2,2,1}}));
        assertEquals(1, DpSolution.longestIncreasingPath(new int[][] {{1}}));
    }

//    @Test
//    public void testIsValidPalindrome() {
//        assertTrue(DpSolution.isValidPalindrome("abcdeca", 2));
//        assertTrue(DpSolution.isValidPalindrome("abbababa", 1));
//        assertFalse(DpSolution.isValidPalindrome("bacabaaa", 2));
//    }
}
