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
    // edges[i] is int[][], array of int pairs - vertex connected to i vertex and weight of the edge
    public static int[] dijkstra(int[][][] g, int source) {
        int n = g.length;
        int[] distance = new int[n];
        HashMap<Integer, int[]> ref = new HashMap<>();

        // [0] - vertex, [1] - distance
        PriorityQueue<int[]> q = new PriorityQueue<>(n, (v1, v2) -> { return v1[1] - v2[1]; });
        for (int i = 0; i < g.length; i++) {
            int[] d = new int[] {i, i == source ? 0 : Integer.MAX_VALUE};
            ref.put(i, d);
            q.add(d);
            distance[i] = d[1];
        }

        while (!q.isEmpty()) {
            int[] u = q.poll();
            for (int[] v: g[u[0]]) {
                int alt = u[1] + v[1];
                if (alt < distance[v[0]]) {
                    distance[v[0]] = alt;
                    // update in heap
                    int[] r = ref.get(v[0]);
                    q.remove(r);
                    r[1] = alt;
                    q.add(r);
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
        state[0] = 1;
        while (!q.isEmpty()) {
            int v = q.poll();
            for (int u: G[v]) {
                if (state[u] == 0) {
                    q.add(u);
                    state[u] = 1;
                }
            }
            state[v] = 2;
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
        HashMap<Integer, HashMap<Integer, Integer>> cost = new HashMap<>();
        for (int[] flight: flights) {
            int from = flight[0];
            int to = flight[1];
            int price = flight[2];
            if (!cost.containsKey(from)) {
                cost.put(from, new HashMap<>());
            }
            cost.get(from).put(to, price);
        }

        // 0 - city (dest), 1 - number of rem.stops, 2 - price
        PriorityQueue<int[]> q = new PriorityQueue<>((a, b)->a[2] - b[2]);
        q.add(new int[] {src, stops, 0});
        while (!q.isEmpty()) {
            int[] top = q.poll();
            int city = top[0];
            int nstops = top[1];
            int price = top[2];
            if (city == dst) {
                return price;
            }
            if (nstops < 0) {
                continue;
            }
            HashMap<Integer, Integer> connections = cost.get(city);
            if (connections != null) {
                for (Map.Entry<Integer, Integer> c: connections.entrySet()) {
                    q.add(new int[] {c.getKey(), nstops - 1, c.getValue() + price});
                }
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
}