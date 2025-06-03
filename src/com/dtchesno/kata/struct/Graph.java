package com.dtchesno.kata.struct;

import java.lang.reflect.Array;
import java.util.*;

public class Graph {

    private boolean mIsDirected;
    private ArrayList<ArrayList<Edge>> edges;

    public static class Edge {
        public int y;
        public int weight;

        public Edge(int y, int weight) {
            this.y = y;
            this.weight = weight;
        }

        public Edge(int y) {
            this(y, 1);
        }
    }

    public static class Traversal {
        public int[] vertices;
        public int[] parent;


        public Traversal(int[] vertices, int[] parent) {
            this.vertices = vertices;
            this.parent = parent;
        }
    }

    private static class TraversalCtx {
        boolean[] discovered;
        boolean[] processed;
        int[] parent;
        ArrayList<Integer> vertices;

        public TraversalCtx(int size) {
            discovered = new boolean[size];
            processed = new boolean[size];
            parent = new int[size];
            for (int i = 0; i < size; i++) {
                discovered[i] = processed[i] = false;
                parent[i] = -1;
            }
            vertices = new ArrayList<>(size);
        }
    }

    // builds graph of vertices [0..size) with edges from endpoints,
    // where each pair (two consecutive numbers) has two connected vertices (start/send)
    public Graph(boolean isDirected, int size, int[] endpoints) {
        this.edges = new ArrayList<>(size);
        for (int i = 0; i < size; i++) {
            this.edges.add(new ArrayList<>());
        }
        for (int i = 0; i < endpoints.length; i += 2) {
            this.edges.get(endpoints[i]).add(new Edge(endpoints[i + 1]));
            if (!isDirected) {
                this.edges.get(endpoints[i + 1]).add(new Edge(endpoints[i]));
            }
        }
    }

    public int size() {
        return edges.size();
    }

    // inputs: edges
    // outputs: vArray - vertices in bfs; parent array for vertices
    public Traversal bfs(int start) {
        int size = edges.size();
        ArrayList<Integer> vertices = new ArrayList<>(size);
        int[] parent = new int[size];
        int[] state = new int[size]; // 0/1/2 - white/gray/black

        LinkedList<Integer> q = new LinkedList<>();
        q.add(start);
        state[start] = 1;
        while (!q.isEmpty()) {
            int v = q.poll();
            vertices.add(v);
            for (Edge e : edges.get(v)) {
                int u = e.y;
                if (state[u] == 0) {
                    state[u] = 1;
                    parent[u] = v;
                    q.add(u);
                }
            }
            state[v] = 2;
        }

        int[] vArray = new int[vertices.size()];
        for (int i = 0; i < vertices.size(); i++) {
             vArray[i] = vertices.get(i);
        }
        return new Traversal(vArray, parent);
    }

    public Traversal dfs(int start) {
        TraversalCtx ctx = new TraversalCtx(size());
        dfs(start, ctx);

        int[] vArray = new int[ctx.vertices.size()];
        for (int i = 0; i < vArray.length; i++) {
            vArray[i] = ctx.vertices.get(i);
        }
        return new Traversal(vArray, ctx.parent);
    }

    private void dfs(int v, TraversalCtx ctx) {
        ctx.discovered[v] = true;
        ctx.vertices.add(v);
        for (Edge e : edges.get(v)) {
            int u = e.y;
            if (!ctx.discovered[u]) {
                ctx.discovered[u] = true;
                ctx.parent[u] = v;
                dfs(u, ctx);
            }
        }
        ctx.processed[v] = true;
    }

    // return min distance form source to all nodes
    // g[i] is int[][], array of int pairs - vertex connected to i vertex and weight of the edge
    public static int[] dijkstra(int[][][] g, int source) {
        // result distance array
        int[] distance = new int[g.length];

        // cache for quick access to distance-to-vertex
        // this could be map(int, int), but we reuse the same array we use in p.queue for bfs, where we need source->(dest, distance)
        HashMap<Integer, int[]> cache = new HashMap<>();

        // priority queue for bfs over closest vertices
        PriorityQueue<int[]> q = new PriorityQueue<>(g.length, (a, b) -> a[1] - b[1]);

        // build initial distances from source;
        // put that into cache and into queue for bfs traversal
        for (int i = 0; i < g.length; i++) {
            int[] intialDistance = new int[] { i, i == source ? 0 : Integer.MAX_VALUE };
            cache.put(i, intialDistance);
            q.add(intialDistance);
            distance[i] = intialDistance[1];
        }

        while (!q.isEmpty()) {

            // closest vertex to source as of now
            int[] path = q.poll();
            int v = path[0];
            int vDistance = path[1];

            // let's reevaluate neighbours distances
            for (int[] edge : g[v]) {
                int u = edge[0];
                int vToUDistance = edge[1];
                int[] uDistance = cache.get(u);

                // it appears we can reach 'u' from source via 'v' faster
                if (vDistance + vToUDistance < uDistance[1]) {
                    uDistance[1] = vDistance + vToUDistance;
                    distance[u] = uDistance[1];
                    cache.put(u, uDistance);
                    q.add(uDistance);
                }
            }
        }

        return distance;
    }

    // exercises

    public static List<Integer> bfs(int G[][]) {
        ArrayList<Integer> result = new ArrayList<>();
        int[] state = new int[G.length];
        LinkedList<Integer> q = new LinkedList<>();
        q.add(0);
        while (!q.isEmpty()) {
            int v = q.poll();
            state[v] = 1;
            for (int u : G[v]) {
                if (state[u] == 0) {
                    state[u] = 1;
                    q.add(u);
                }
            }
            state[v] = 2;
            result.add(v);
        }
        return result;
    }

    public static List<Integer> dfs(int[][] G) {
        ArrayList<Integer> res = new ArrayList<>();
        int[] state = new int[G.length];
        dfsRec(0, G, state, res);
        return res;
    }

    private static void dfsRec(int v, int[][] G, int[] state, List<Integer> res) {
        state[v] = 1;
        for (int u: G[v]) {
            if (state[u] == 0) {
                dfsRec(u, G, state, res);
            }
        }
        state[v] = 2;
        res.add(v);
    }


    // 802. Find Eventual Safe States
    // https://leetcode.com/articles/find-eventual-safe-states/
    public static Integer[] listEventualSafeNodes(int[][] g) {
        List<Integer> result = new ArrayList<>();
        int[] state = new int[g.length];
        for (int v = 0; v < g.length; v++) {
            if (listEventualSafeNodesDFS(v, g, state)) result.add(v);
        }
        return result.toArray(new Integer[0]);
    }

    private static boolean listEventualSafeNodesDFS(int v, int[][] g, int[] state) {
        if (state[v] == 1) return false;
        if (state[v] == 2) return true;

        state[v] = 1;
        for (int u : g[v]) {
            if (state[u] == 1) return false;
            if (state[u] == 2) continue;
            if (!listEventualSafeNodesDFS(u, g, state)) return false;
        }
        state[v] = 2;
        return true;
    }


    // build order byte-by-byte
    public static List<Integer> buildOrder(int[][] steps) {
        List<Integer> result = new ArrayList<>();
        int[] state = new int[steps.length];
        for (int v = 0; v < steps.length; v++) {
            if (state[v] != 0) {
                continue;
            }
            buildOrderDFS(v, steps, state, result);
        }
        return result;
    }

    private static void buildOrderDFS(int v, int[][] steps, int[] state, List<Integer> result) {
        state[v] = 1;
        for (int u : steps[v]) {
            if (state[u] != 0) continue;
            buildOrderDFS(u, steps, state, result);
        }
        result.add(v);
        state[v] = 2;
    }


    // 787. Cheapest Flights Within K Stops
    // find cheapest price from src to dst up to k stops in directed weighted graph
    // e.g. [[0,1,100], [1,2,100], [0,2,500]], src=0, dst=2, k=1; result=200 (0->1->2)
    // https://leetcode.com/problems/cheapest-flights-within-k-stops/
    public static int findCheapestPrice(int n, int[][] flights, int src, int dst, int k) {
        Map<Integer, List<int[]>> connections = new HashMap<>();
        for (int[] f : flights) {
            int v1 = f[0];
            int v2 = f[1];
            int price = f[2];
            List<int[]> c = connections.computeIfAbsent(v1, (key) -> new ArrayList<>());
            c.add(new int[] {v2, price});
        }

        // city, cost, r.stops
        Queue<int[]> q = new PriorityQueue<>((a, b) -> a[1] - b[1]);
        q.add(new int[] {src, 0, k});
        while (!q.isEmpty()) {
            int[] top = q.poll();
            if (top[0] == dst) return top[1];
            if (top[2] < 0) continue;
            List<int[]> c = connections.get(top[0]);
            if (c == null) continue;
            for (int[] connection : c) {
                q.add(new int[] { connection[0], top[1] + connection[1], top[2] - 1});
            }
        }
        return -1;
    }

    // NOTE: as above, but checking already visited with less stops - does it help though???
//    public static int findCheapestPrice(int n, int[][] flights, int src, int dst, int k) {
//        Map<Integer, List<int[]>> connections = new HashMap<>();
//        for (int[] con : flights) {
//            int v1 = con[0];
//            int v2 = con[1];
//            int price = con[2];
//            List<int[]> adj = connections.get(v1);
//            if (adj == null) {
//                adj = new ArrayList<>();
//                connections.put(v1, adj);
//            }
//            adj.add(new int[] { v2, price});
//        }
//
//        // city, cost, rem.stops
//        Queue<int[]> q = new PriorityQueue<>((a, b) -> a[1] - b[1]);
//        q.add(new int[] {src, 0, 0});
//        int[] stops = new int[n];
//        Arrays.fill(stops, Integer.MAX_VALUE);
//        while (!q.isEmpty()) {
//            int[] top = q.poll();
//            if (top[2] > stops[top[0]] || top[2] > k + 1) {
//                continue;
//            }
//            if (top[0] == dst) {
//                return top[1];
//            }
//            stops[top[0]] = top[2];
//            List<int[]> adj = connections.get(top[0]);
//            if (adj == null) {
//                continue;
//            }
//            for (int[] c : adj) {
//                q.add(new int[] {c[0], top[1] + c[1], top[2] + 1});
//            }
//        }
//        return -1;
//    }


    // find shortest path between graph nodes
    // byte-by-byte #16 pg.16
    public static List<Integer> findShortestPath(int[][] G, int src, int dst) {
        Map<Integer, Integer> parent = new HashMap<>();
        Queue<Integer> q = new LinkedList<>();
        q.add(src);
        while (!q.isEmpty()) {
            int v = q.poll();
            if (v == dst) break;
            for (int u : G[v]) {
                if (parent.containsKey(u)) continue;
                q.add(u);
                parent.put(u, v);
            }
        }
        if (!parent.containsKey(dst)) {
            return new ArrayList<>();
        }
        ArrayList<Integer> result = new ArrayList<>();
        int cur = dst;
        while (cur != src) {
            result.add(cur);
            cur = parent.get(cur);
        }
        result.add(src);
        Collections.reverse(result);
        return result;
    }


    // find articulation point
    public static List<Integer> findAP(int[][] G) {
        boolean[] isAP = new boolean[G.length];
        Arrays.fill(isAP, false);
        int[] discovery = new int[G.length];
        Arrays.fill(discovery, -1);
        discovery[0] = 0;
        int[] low = new int[G.length];
        low[0] = -1;

        findAPDfs(0, 0, G, -1, discovery, low, isAP);

        List<Integer> result = new ArrayList<>();
        for (int i = 0; i < isAP.length; i++) {
            if (isAP[i]) {
                result.add(i);
            }
        }
        return result;
    }

    private static void findAPDfs(int u, int time, int[][] G, int parent, int[] discovery, int[] low, boolean[] isAP) {
        int children = 0;
        discovery[u] = low[u] = time;
        for (int v : G[u]) {
            if (discovery[v] == -1) {
                children++;
                findAPDfs(v, time + 1, G, u, discovery, low, isAP);
                low[u] = Math.min(low[u], low[v]);
                if (parent != -1 && low[v] >= discovery[u]) {
                    isAP[u] = true;
                }
            } else if (v != parent) {
                // we should exclude connection to parent as we look for cycle
                // we use discovery, but not low, otherwise we could get low thru ancestor (not parent)
                low[u] = Math.min(low[u], discovery[v]);
            }
        }

        // check for root
        if (parent == -1 && children > 1) {
            isAP[u] = true;
        }
    }

    public static List<List<Integer>> allPathsSourceTarget(int[][] graph) {
        List<List<Integer>> result = new ArrayList<>();
        List<Integer> acc = new ArrayList<>();
        acc.add(0);
        allPathsSourceTargetDFS(graph, 0, acc, result);
        return result;
    }

    private static void allPathsSourceTargetDFS(
            int[][] graph,
            int v,
            List<Integer> acc,
            List<List<Integer>> result) {
        if (v == graph.length - 1) {
            result.add(new ArrayList<>(acc));
            return;
        }
        for (int u : graph[v]) {
            acc.add(u);
            allPathsSourceTargetDFS(graph, u, acc, result);
            acc.remove(acc.size() - 1);
        }
    }


    // 785. Is Graph Bipartite?
    // https://leetcode.com/problems/is-graph-bipartite/submissions/
    public static boolean isBipartite(int[][] graph) {
        int[] state = new int[graph.length];
        Arrays.fill(state, -1);
        for (int u = 0; u < graph.length; u++) {
            if (state[u] != -1) continue;
            state[u] = 0;
            if (!isBipartiteDFS(u, graph, state)) return false;
        }
        return true;
    }

    private static boolean isBipartiteDFS(int u, int[][] graph, int[] state) {
        for (int v : graph[u]) {
            if (state[u] == state[v]) {
                return false;
            } else if (state[v] == -1) {
                state[v] = (state[u] + 1) % 2;
                if (!isBipartiteDFS(v, graph, state)) return false;
            }
        }
        return true;
    }
}