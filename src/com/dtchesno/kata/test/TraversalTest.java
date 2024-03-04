package com.dtchesno.kata.test;

import com.dtchesno.kata.misc.MiscSolution;
import com.dtchesno.kata.struct.ArrayStringTasks;
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
        Assert.assertEquals(6, Traversal.shortestPath(new int[][] {
                {0,0,0},{1,1,0}, {0,0,0}, {0,1,1},{0,0,0}}, 1));
        Assert.assertEquals(-1, Traversal.shortestPath(new int[][] {
                {0,1,1},{1,1,1}, {1,0,0}}, 1));
    }
}
