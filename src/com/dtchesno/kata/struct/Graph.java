package com.dtchesno.kata.struct;

import java.util.ArrayList;

public class Graph {

    private ArrayList<ArrayList<Edge>> nodes;

    public static class Edge {
        int y;
        int weight;
    }

    public Graph(int size) {
        nodes = new ArrayList<>(size);
        for (int i = 0; i < size; i++) {
            nodes.add(new ArrayList<>());
        }
    }

}