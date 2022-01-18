package com.dtchesno.kata.struct;

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

    public Traversal bfs(int start) {
        int size = edges.size();
        ArrayList<Integer> vertices = new ArrayList<>(size);
        int[] parent = new int[size];
        //boolean[] processed = new boolean[size];
        boolean[] discovered = new boolean[size];

        LinkedList<Integer> q = new LinkedList<>();
        q.add(start);
        discovered[start] = true;
        while (!q.isEmpty()) {
            int v = q.poll();
            vertices.add(v);
            for (Edge e: edges.get(v)) {
                if (!discovered[e.y]) {
                    discovered[e.y] = true;
                    parent[e.y] = v;
                    q.add(e.y);
                }
            }
        }

        int[] vArray = new int[vertices.size()];
        for (int i = 0; i < vArray.length; i++) {
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
        ctx.discovered[v] = ctx.processed[v] = true;
        ctx.vertices.add(v);
        for (Edge e: edges.get(v)) {
            if (!ctx.discovered[e.y]) {
                ctx.parent[e.y] = v;
                dfs(e.y, ctx);
            }
        }
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

    public static List<Integer> bfs(int[][] G) {
        ArrayList<Integer> res = new ArrayList<>();
        int[] state = new int[G.length];
        LinkedList<Integer> q = new LinkedList<>();
        q.add(0);
        state[0] = 1; // discovered
        while (!q.isEmpty()) {
            int v = q.poll();
            for (int u: G[v]) {
                if (state[u] == 0) {
                    q.add(u);
                    state[u] = 1; // discovered
                }
            }
            state[v] = 2; // exhausted edges - processed
            res.add(v);
        }
        return res;
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
        for (int i = 0; i < color.length; i++) {
            color[i] = 0;
        }

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
        for (int child: g[node]) {
            if (color[child] == 2) {
                continue;
            }
            if (color[child] == 1) {
                return false;
            }
            if (!listEventualSafeNodesDFS(child, g, color)) {
                return false;
            }
        }
        color[node] = 2;
        return true;
    }

    public static List<Integer> buildOrder(int[][] steps) {
        ArrayList<Integer> order = new ArrayList<>();
        int[] state = new int[steps.length];
        Arrays.fill(state, 0);
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
        Map<Integer, List<int[]>> prices = new HashMap<>();
        for (int i = 0; i < flights.length; i++) {
            List<int[]> connections = prices.getOrDefault(flights[i][0], new ArrayList<>());
            connections.add(new int[] { flights[i][1], flights[i][2] });
            prices.put(flights[i][0], connections);
        }

        int minCost = Integer.MAX_VALUE;

        // [port, cost, nstop]
        PriorityQueue<int[]> q = new PriorityQueue<>((a, b) -> a[1] - b[1]);
        q.add(new int[] { src, 0, stops });
        while (!q.isEmpty()) {
            int[] stop = q.poll();
            int port = stop[0];
            int cost = stop[1];
            int nstops = stop[2];

            if (port == dst) {
                return cost;
            }

            if (nstops < 0 || !prices.containsKey(port)) {
                continue;
            }

            for (int[] connection : prices.get(port)) {
                q.add(new int[] { connection[0], cost + connection[1], nstops - 1 });
            }
        }
        return -1;
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