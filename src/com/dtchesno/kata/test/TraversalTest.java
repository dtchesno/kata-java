package com.dtchesno.kata.test;

import com.dtchesno.kata.struct.ArrayStringTasks;
import com.dtchesno.kata.traversal.Traversal;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;


import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class TraversalTest {

    @Before
    public void setUp() throws Exception {
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void testShortestDistance() {
        assertEquals(-1, Traversal.shortestDistance(new int[][]{{1}}));
        assertEquals(1, Traversal.shortestDistance(new int[][]{{1, 0}}));
        assertEquals(7, Traversal.shortestDistance(new int[][]{{1, 0, 2, 0, 1}, {0, 0, 0, 0, 0}, {0, 0, 1, 0, 0}}));
    }

    @Test
    public void testMinTotalDistance() {
        assertEquals(1, Traversal.minTotalDistance(new int[][]{{1, 1}}));
        assertEquals(6, Traversal.minTotalDistance(new int[][]{{1, 0, 0, 0, 1}, {0, 0, 0, 0, 0}, {0, 0, 1, 0, 0}}));
    }

    @Test
    public void testShortestPath() {
        Assert.assertEquals(6, Traversal.shortestPath(new int[][]{{0, 0, 0}, {1, 1, 0}, {0, 0, 0}, {0, 1, 1}, {0, 0, 0}}, 1));
        Assert.assertEquals(-1, Traversal.shortestPath(new int[][]{{0, 1, 1}, {1, 1, 1}, {1, 0, 0}}, 1));
        Assert.assertEquals(13, Traversal.shortestPath(new int[][]{{0, 1, 0, 0, 0, 1, 0, 0}, {0, 1, 0, 1, 0, 1, 0, 1}, {0, 0, 0, 1, 0, 0, 1, 0}}, 1));
    }

    @Test
    public void testIsValidNumber() {
        Assert.assertTrue(Traversal.isValidNumber("2"));
        Assert.assertTrue(Traversal.isValidNumber("0089"));
        Assert.assertTrue(Traversal.isValidNumber("-0.1"));
        Assert.assertTrue(Traversal.isValidNumber("+3.14"));
        Assert.assertTrue(Traversal.isValidNumber("4."));
        Assert.assertTrue(Traversal.isValidNumber("-.9"));
        Assert.assertTrue(Traversal.isValidNumber("2e10"));
        Assert.assertTrue(Traversal.isValidNumber("-90E3"));
        Assert.assertTrue(Traversal.isValidNumber("3e+7"));
        Assert.assertTrue(Traversal.isValidNumber("+6e-1"));
        Assert.assertTrue(Traversal.isValidNumber("53.5e93"));
        Assert.assertTrue(Traversal.isValidNumber("-123.456e789"));

        Assert.assertFalse(Traversal.isValidNumber("abc"));
        Assert.assertFalse(Traversal.isValidNumber("1a"));
        Assert.assertFalse(Traversal.isValidNumber("1e"));
        Assert.assertFalse(Traversal.isValidNumber("e3"));
        Assert.assertFalse(Traversal.isValidNumber("99e2.5"));
        Assert.assertFalse(Traversal.isValidNumber("--6"));
        Assert.assertFalse(Traversal.isValidNumber("-+3"));
        Assert.assertFalse(Traversal.isValidNumber("95a54e53"));
        Assert.assertFalse(Traversal.isValidNumber("e"));
        Assert.assertFalse(Traversal.isValidNumber("."));
    }

    @Test
    public void testShortestPathBinaryMatrix() {
        Assert.assertEquals(2, Traversal.shortestPathBinaryMatrix(new int[][]{{0, 1}, {1, 0}}));
        Assert.assertEquals(4, Traversal.shortestPathBinaryMatrix(new int[][]{{0, 0, 0}, {1, 1, 0}, {1, 1, 0}}));
        Assert.assertEquals(-1, Traversal.shortestPathBinaryMatrix(new int[][]{{1, 0, 0}, {1, 1, 0}, {1, 1, 0}}));
        Assert.assertEquals(25, Traversal.shortestPathBinaryMatrix(new int[][]{
            {0,1,1,1,1,1,1,1},
            {0,1,1,0,0,0,0,0},
            {0,1,0,1,1,1,1,0},
            {0,1,0,1,1,1,1,0},
            {0,1,1,0,0,1,1,0},
            {0,1,1,1,1,0,1,0},
            {0,0,0,0,0,1,1,0},
            {1,1,1,1,1,1,1,0}
        }));
    }

    @Test
    public void testFindBuildings() {
        assertTrue(Arrays.equals(new int[]{0, 2, 3}, Traversal.findBuildings(new int[]{4, 2, 3, 1})));
        assertTrue(Arrays.equals(new int[]{0, 1, 2, 3}, Traversal.findBuildings(new int[]{4, 3, 2, 1})));
        assertTrue(Arrays.equals(new int[]{3}, Traversal.findBuildings(new int[]{1, 3, 2, 4})));
    }

    @Test
    public void testLargestIsland() {
        assertEquals(3, Traversal.largestIsland(new int[][]{{1, 0}, {0, 1}}));
        assertEquals(4, Traversal.largestIsland(new int[][]{{1, 1}, {1, 0}}));
        assertEquals(4, Traversal.largestIsland(new int[][]{{1, 1}, {1, 1}}));
    }

    @Test
    public void test_maximumBooks() {
        assertEquals(19, Traversal.maximumBooks(new int[]{8, 5, 2, 7, 9}));
        assertEquals(12, Traversal.maximumBooks(new int[]{7, 0, 3, 4, 5}));
        assertEquals(13, Traversal.maximumBooks(new int[]{8, 2, 3, 7, 3, 4, 0, 1, 4, 3}));
    }

    @Test
    public void test_platesBetweenCandles() {
        assertTrue(Arrays.equals(new int[]{2, 3}, Traversal.platesBetweenCandles("**|**|***|", new int[][]{{2, 5}, {5, 9}})));
        assertTrue(Arrays.equals(new int[]{9, 0, 0, 0, 0}, Traversal.platesBetweenCandles("***|**|*****|**||**|*", new int[][]{{1, 17}, {4, 5}, {14, 17}, {5, 11}, {15, 16}})));
    }

    @Test
    public void test_minMovesToMakePalindrome() {
        assertEquals(2, Traversal.minMovesToMakePalindrome("aabb"));
        assertEquals(2, Traversal.minMovesToMakePalindrome("letelt"));
    }

    @Test
    public void test_wordBreak() {
        assertEquals(
            Set.of("cats and dog","cat sand dog"),
            new HashSet(Traversal.wordBreak("catsanddog", List.of("cat","cats","and","sand","dog"))));
        assertEquals(
            Set.of("pine apple pen apple","pineapple pen apple","pine applepen apple"),
            new HashSet(Traversal.wordBreak("pineapplepenapple", List.of("apple","pen","applepen","pine","pineapple"))));
        assertEquals(
            Set.of(),
            new HashSet(Traversal.wordBreak("catsandog", List.of("cats","dog","sand","and","cat"))));
    }

    @Test
    public void test_orangesRotting() {
        assertEquals(4, Traversal.orangesRotting(new int[][] {{2,1,1},{1,1,0},{0,1,1}}));
        assertEquals(-1, Traversal.orangesRotting(new int[][] {{2,1,1},{0,1,1},{1,0,1}}));
        assertEquals(0, Traversal.orangesRotting(new int[][] {{0,2}}));
    }

    @Test
    public void test_findAllConcatenatedWordsInADict() {
        assertEquals(
            List.of("catsdogcats","dogcatsdog","ratcatdogcat"),
            Traversal.findAllConcatenatedWordsInADict(new String[] {"cat","cats","catsdogcats","dog","dogcatsdog","hippopotamuses","rat","ratcatdogcat"}));
        assertEquals(
                List.of("catdog"),
                Traversal.findAllConcatenatedWordsInADict(new String[] {"cat","dog","catdog"}));
    }

    @Test
    public void test_goodDaysToRobBank() {
        assertEquals(List.of(2, 3), Traversal.goodDaysToRobBank(new int[] {5,3,3,3,5,6,2}, 2));
        assertEquals(List.of(0, 1, 2, 3, 4), Traversal.goodDaysToRobBank(new int[] {1,1,1,1,1}, 0));
        assertEquals(List.of(), Traversal.goodDaysToRobBank(new int[] {1,2,3,4,5,6}, 2));
    }

    @Test
    public void test_minimumSwaps() {
        assertEquals(6, ArrayStringTasks.minimumSwaps(new int[] {3,4,5,5,3,1}));
        assertEquals(0, ArrayStringTasks.minimumSwaps(new int[] {9}));
    }
}