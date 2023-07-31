package com.dtchesno.kata.struct;

import org.apache.commons.compress.archivers.ar.ArArchiveEntry;

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

    // https://leetcode.com/articles/find-eventual-safe-states/
    public static Integer[] listEventualSafeNodes(int[][] g) {
        // 0/1/2 - white/gray/black - undiscovered/discovered/processed
        int[] color = new int[g.length];
        ArrayList<Integer> result = new ArrayList<>();
        for (int i = 0; i < g.length; i++) {
            if (listEventualSafeNodesDFS(i, g, color)) {
                result.add(i);
            }
        }
        return result.toArray(new Integer[0]);
    }

    private static boolean listEventualSafeNodesDFS(int node, int[][] g, int[] color) {
        if (color[node] == 1) {
            return false;
        }
        if (color[node] == 2) {
            return true;
        }

        color[node] = 1;

        for (int u : g[node]) {
            if (color[u] == 1) {
                return false; // we've been here and haven't reached terminal state (2)
            }
            if (color[u] == 2) {
                continue; // it leads to terminal state, but we need to check all 'childs'
            }
            if (!listEventualSafeNodesDFS(u, g, color)) {
                return false; // dfs, and fail if there is a loop via child
            }
        }

        color[node] = 2;
        return true;
    }

    // build order byte-by-byte
    public static List<Integer> buildOrder(int[][] steps) {
        ArrayList<Integer> order = new ArrayList<>();
        int[] state = new int[steps.length];
        for (int v = 0; v < steps.length; v++) {
            if (state[v] != 0) {
                continue;
            }
            dfsBuildOrder(v, steps, state, order);
        }
        return order;
    }

    private static void dfsBuildOrder(int v, int[][] steps, int[] state, List<Integer> order) {
        state[v] = 1;
        for (int u: steps[v]) {
            if (state[u] != 0) {
                continue;
            }
            dfsBuildOrder(u, steps, state, order);
        }
        state[v] = 2;
        order.add(v);
    }


    // find cheapest price from src to dst up to k stops in directed weighted graph
    // e.g. [[0,1,100], [1,2,100], [0,2,500]], src=0, dst=2, k=1; result=200 (0->1->2)
    // https://leetcode.com/problems/cheapest-flights-within-k-stops/
    public static int findCheapest(int[][] flights, int src, int dst, int stops) {
        int n = 0;
        // src->(dst,price)
        Map<Integer, List<int[]>> connections = new HashMap<>();
        for (int[] flight : flights) {
            connections.putIfAbsent(flight[0], new ArrayList<>());
            List<int[]> c = connections.get(flight[0]);
            c.add(new int[] {flight[1], flight[2]});
            n = Math.max(n, flight[0] + 1);
            n = Math.max(n, flight[1] + 1);
        }

        int minCost = Integer.MAX_VALUE;

        // (v, cost, stops)
        Queue<int[]> q = new PriorityQueue<>((a, b) -> a[1] - b[1]);
        q.add(new int[] {src, 0, 0});
        int[] stopCache = new int[n];
        Arrays.fill(stopCache, Integer.MAX_VALUE);
        while (!q.isEmpty()) {
            int[] top = q.poll();
            int v = top[0];
            int cost = top[1];
            int nstops = top[2];

            if (nstops > stops + 1 || nstops > stopCache[v]) {
                continue;
            }
            stopCache[v] = nstops;

            if (v == dst) {
                minCost = Math.min(minCost, cost);
                continue;
            }

            List<int[]> conn = connections.get(v);
            for (int[] c : conn) {
                q.add(new int[] {c[0], cost + c[1], nstops + 1});
            }
        }

        return minCost;
    }

    public static int findCheapest2(int[][] flights, int src, int dst, int stops) {
        // cache connections & prices: start->{(dest, price)*}
        Map<Integer, Map<Integer, Integer>> prices = new HashMap<>();
        for (int[] flight : flights) {
            Map<Integer, Integer> connections = prices.getOrDefault(flight[0], new HashMap<>());
            connections.put(flight[1], flight[2]);
            prices.put(flight[0], connections);
        }

        // p.queue: (port, stops, cost)
        PriorityQueue<int[]> q = new PriorityQueue<>((a, b) -> a[2] - b[2]);
        q.add(new int[] { src, stops, 0 });
        while (!q.isEmpty()) {
            int[] current = q.poll();
            int port = current[0];
            int remainingStops = current[1];
            int cost = current[2];

            if (port == dst) {
                return cost;
            }

            if (remainingStops < 0 || !prices.containsKey(port))  {
                continue;
            }

            // check for connections from current port
            for (Map.Entry<Integer, Integer> next : prices.get(port).entrySet()) {
                q.add(new int[] { next.getKey(), remainingStops - 1, cost + next.getValue() });
            }
        }

        return -1;
    }

    // find shortest path between graph nodes
    // byte-by-byte #16 pg.16
    public static List<Integer> findShortestPath(int[][] G, int src, int dst) {
        LinkedList<Integer> q = new LinkedList<>();
        HashMap<Integer, Integer> parent = new HashMap<>();
        q.add(src);
        while (!q.isEmpty()) {
            int v = q.poll();
            for (int u: G[v]) {
                if (parent.containsKey(u)) {
                    continue;
                }
                parent.put(u, v);
                if (u == dst) {
                    break;
                }
                q.add(u);
            }
        }
        if (!parent.containsKey(dst)) {
            return new ArrayList<Integer>();
        }
        List<Integer> res = new ArrayList<>();
        int v = dst;
        res.add(v);
        while (true) {
            v = parent.get(v);
            res.add(0, v);
            if (v == src) {
                return res;
            }
        }
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

    // Done
    // https://leetcode.com/problems/is-graph-bipartite/submissions/
}