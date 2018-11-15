package com.dtchesno.kata.test;

import com.dtchesno.kata.graph.Graph;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;


public class GraphTest {

    @Before
    public void setUp() throws Exception {
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void testCreateGraph() {
        assertNotEquals(null, new Graph(false, 3, new int[0]));
    }

    @Test
    public void testBFS() {
        Graph g = new Graph(false, 5, new int[] { 0, 2, 0, 3, 1, 3, 1, 4, 2, 4, 4, 0});
        Graph.Traversal t = g.bfs(0);
        assertTrue(Arrays.equals(new int[] { 0, 2, 3, 4, 1 }, t.vertices));
    }

    @Test
    public void testDFS() {
        Graph g = new Graph(false, 5, new int[] { 0, 2, 0, 3, 1, 3, 1, 4, 2, 4, 4, 0});
        Graph.Traversal t = g.dfs(0);
        assertTrue(Arrays.equals(new int[] { 0, 2, 4, 1, 3 }, t.vertices));
    }

    @Test
    public void testEventualSafe() {
        int[][] g = new int[][] {
                new int[] { 1, 2 },
                new int[] { 2, 3 },
                new int[] { 5 },
                new int[] { 0 },
                new int[] { 5 },
                new int[0],
                new int[0]
        };
        assertTrue(Arrays.equals(new Integer[] { 2, 4, 5, 6 }, Graph.listEventualSafeNodes(g)));
    }
}
