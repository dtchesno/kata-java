package com.dtchesno.kata.test;

import com.dtchesno.kata.struct.Graph;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;
import java.util.HashSet;

import static org.junit.Assert.*;


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
        assertEquals(Arrays.asList(0, 1, 2, 3, 4), Graph.buildOrder(new int[][] { {}, {0}, {0}, {1,2}, {3} }));
        assertEquals(Arrays.asList(3, 0, 1, 2, 4), Graph.buildOrder(new int[][] { {3}, {0}, {0}, {}, {3} }));
    }

    @Test
    public void testFindCheapest() {
        assertEquals(200, Graph.findCheapestPrice(3, new int[][]{{0,1,100}, {1,2,100}, {0,2,500}}, 0, 2, 1));
        assertEquals(500, Graph.findCheapestPrice(3, new int[][]{{0,1,100}, {1,2,100}, {0,2,500}}, 0, 2, 0));
        assertEquals(700, Graph.findCheapestPrice(4, new int[][]{{0,1,100}, {1,2,100}, {2,0,100}, {1,3,600}, {2,3,200}}, 0, 3, 1));
        assertEquals(400, Graph.findCheapestPrice(4, new int[][]{{0,1,100}, {0,2,300}, {0,3,500}, {1,2,100}, {2,3,100}}, 0, 3, 1));
        assertEquals(-1, Graph.findCheapestPrice(
                13,
                new int[][] {{11,12,74},{1,8,91},{4,6,13},{7,6,39},{5,12,8},{0,12,54},{8,4,32},{0,11,4},{4,0,91},{11,7,64},{6,3,88},{8,5,80},{11,10,91},{10,0,60},{8,7,92},{12,6,78},{6,2,8},{4,3,54},{3,11,76},{3,12,23},{11,6,79},{6,12,36},{2,11,100},{2,5,49},{7,0,17},{5,8,95},{3,9,98},{8,10,61},{2,12,38},{5,7,58},{9,4,37},{8,6,79},{9,0,1},{2,3,12},{7,10,7},{12,10,52},{7,2,68},{12,2,100},{6,9,53},{7,4,90},{0,5,43},{11,2,52},{11,8,50},{12,4,38},{7,9,94},{2,7,38},{3,7,88},{9,12,20},{12,0,26},{10,5,38},{12,8,50},{0,2,77},{11,0,13},{9,10,76},{2,6,67},{5,6,34},{9,7,62},{5,3,67}},
                10, 1, 10));
    }

    @Test
    public void testFindShortestPath() {
        int[][] G = new int[][] {
                {}, // 0 empty as starts from 1
                {2, 3},
                {5},
                {4},
                {1, 3},
                {4}
        };
        assertEquals(Arrays.asList(1, 3, 4), Graph.findShortestPath(G, 1, 4));
        assertEquals(Arrays.asList(1, 3), Graph.findShortestPath(G, 1, 3));
        assertEquals(Arrays.asList(4, 1), Graph.findShortestPath(G, 4, 1));
        assertEquals(Arrays.asList(), Graph.findShortestPath(G, 4, 0));
    }

    @Test
    public void testFindAp() {
        int[][] G = new int[][]{
            { 1, 2},
            { 0, 3 },
            { 0, 3 ,4 },
            { 1, 2, 4 },
            { 2, 3, 5, 6 },
            { 4,6 },
            { 4,5 }
        };
        assertEquals(Arrays.asList(4), Graph.findAP(G));

        int[][] G2 = new int[][]{
                { 1, 2, 3 },
                { 0, 2 },
                { 0, 1 },
                { 0, 4 },
                { 3 }
        };
        assertEquals(Arrays.asList(0, 3), Graph.findAP(G2));
    }

    @Test
    public void testAllPathsSourceTarget() {
        Assert.assertEquals(
            new HashSet<>(Arrays.asList(
                Arrays.asList(0,1,3),
                Arrays.asList(0,2,3))),
            new HashSet<>(Graph.allPathsSourceTarget(new int[][] {{1,2},{3},{3},{}})));

        Assert.assertEquals(
            new HashSet<>(Arrays.asList(
                    Arrays.asList(0,4),
                    Arrays.asList(0,3,4),
                    Arrays.asList(0,1,3,4),
                    Arrays.asList(0,1,2,3,4),
                    Arrays.asList(0,1,4))),
            new HashSet<>(Graph.allPathsSourceTarget(new int[][] {{4,3,1},{3,2,4},{3},{4},{}})));
    }

    @Test
    public void testIsBipartite() {
        assertFalse(Graph.isBipartite(new int[][] {{1,2,3},{0,2},{0,1,3},{0,2}}));
        Assert.assertTrue(Graph.isBipartite(new int[][] {{1,3},{0,2},{1,3},{0,2}}));
    }

    @Test
    public void test_canFinish() {
        assertTrue(Graph.canFinish(2, new int[][] {{1,0}}));
        assertFalse(Graph.canFinish(2, new int[][] {{1,0}, {0,1}}));
    }
}
