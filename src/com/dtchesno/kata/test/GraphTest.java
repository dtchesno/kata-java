package com.dtchesno.kata.test;

import com.dtchesno.kata.struct.Graph;
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
    public void testBFS2() {
        int[][] G = new int[][] {
                { 1, 2, 3 },
                { 4 },
                { 3, 4},
                { 5 },
                { 0, 5 },
                {}
        };
        assertEquals(Arrays.asList(0,1,2,3,4,5), Graph.bfs(G));
    }

    @Test
    public void testDFS2() {
        int[][] G = new int[][] {
                { 1, 2, 3 },
                { 4 },
                { 3, 4},
                { 5 },
                { 0, 5 },
                {}
        };
        assertEquals(Arrays.asList(5,4,1,3,2,0), Graph.dfs(G));
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

    @Test
    public void testDijkstra() {
        int[][][] g = new int[][][] {
                { {1, 1}, {2, 1}, {3, 3} },
                { {4, 2} },
                { {3, 1}, {4, 1}},
                { {5, 3} },
                { {0, 2}, {5, 3} },
                {}
        };
        int[] result = Graph.dijkstra(g, 0);
        //System.out.println(Arrays.toString(result));
        assertTrue(Arrays.equals(new int[] { 0, 1, 1, 2, 2, 5 }, result));
    }

    @Test
    public void testBuildOrder() {
        assertEquals(Arrays.asList(0, 1, 2, 3, 4),
                Graph.buildOrder(new int[][] { {}, {0}, {0}, {1,2}, {3} }));
        assertEquals(Arrays.asList(3, 0, 1, 2, 4),
                Graph.buildOrder(new int[][] { {3}, {0}, {0}, {}, {3} }));
    }

    @Test
    public void testFindCheapest() {
        assertEquals(200, Graph.findCheapest(new int[][]{{0,1,100}, {1,2,100}, {0,2,500}}, 0, 2, 1));
        assertEquals(500, Graph.findCheapest(new int[][]{{0,1,100}, {1,2,100}, {0,2,500}}, 0, 2, 0));
    }
}
