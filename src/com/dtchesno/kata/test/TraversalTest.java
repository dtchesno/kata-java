package com.dtchesno.kata.test;

import com.dtchesno.kata.traversal.Traversal;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;


import java.util.Arrays;

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
        assertEquals(-1, Traversal.shortestDistance(new int[][] {{1}}));
        assertEquals(1, Traversal.shortestDistance(new int[][] {{1,0}}));
        assertEquals(7, Traversal.shortestDistance(new int[][] {{1,0,2,0,1}, {0,0,0,0,0}, {0,0,1,0,0}}));
    }

    @Test
    public void testMinTotalDistance() {
        assertEquals(1, Traversal.minTotalDistance(new int[][] {{1,1}}));
        assertEquals(6, Traversal.minTotalDistance(new int[][] {{1,0,0,0,1}, {0,0,0,0,0}, {0,0,1,0,0}}));
    }

    @Test
    public void testShortestPath() {
        Assert.assertEquals(6, Traversal.shortestPath(new int[][] { {0,0,0},{1,1,0}, {0,0,0}, {0,1,1},{0,0,0}}, 1));
        Assert.assertEquals(-1, Traversal.shortestPath(new int[][] { {0,1,1},{1,1,1}, {1,0,0}}, 1));
        Assert.assertEquals(13, Traversal.shortestPath(new int[][] {  {0,1,0,0,0,1,0,0},{0,1,0,1,0,1,0,1},{0,0,0,1,0,0,1,0} }, 1));
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
        Assert.assertEquals(2, Traversal.shortestPathBinaryMatrix(new int [][] {{0,1},{1,0}}));
        Assert.assertEquals(4, Traversal.shortestPathBinaryMatrix(new int [][] {{0,0,0},{1,1,0},{1,1,0}}));
        Assert.assertEquals(-1, Traversal.shortestPathBinaryMatrix(new int [][] {{1,0,0},{1,1,0},{1,1,0}}));
    }

    @Test
    public void testFindBuildings() {
        assertTrue(Arrays.equals(new int[] {0,2,3}, Traversal.findBuildings(new int[] {4,2,3,1})));
        assertTrue(Arrays.equals(new int[] {0,1,2,3}, Traversal.findBuildings(new int[] {4,3,2,1})));
        assertTrue(Arrays.equals(new int[] {3}, Traversal.findBuildings(new int[] {1,3,2,4})));
    }

    @Test
    public void testLargestIsland() {
        assertEquals(3, Traversal.largestIsland(new int[][] {{1, 0}, {0, 1}}));
        assertEquals(4, Traversal.largestIsland(new int[][] {{1, 1}, {1, 0}}));
        assertEquals(4, Traversal.largestIsland(new int[][] {{1, 1}, {1, 1}}));
    }
}
