package com.dtchesno.kata.struct;

import javafx.util.Pair;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

public class LRUCacheSimplified {

    private int capacity;
    private LinkedList<Pair<Integer, Integer>> queue = new LinkedList<>();
    private Map<Integer, Pair<Integer, Integer>> cache = new HashMap<>();

    public LRUCacheSimplified(int capacity) {
        this.capacity = capacity;
    }

    public int get(int key) {
        Pair<Integer, Integer> entry = cache.get(key);

        if (entry == null)
            return -1;

        queue.remove(entry);
        queue.add(entry);

        return entry.getValue();
    }

    public void put(int key, int value) {
        Pair<Integer, Integer> entry = cache.get(key);
        if (entry != null) {
            queue.remove(entry);
            if (entry.getValue() != value) {
                entry = new Pair<Integer, Integer>(key, value);
                cache.put(key, entry);
            }
            queue.add(entry);
            return;
        }

        if (queue.size() == capacity) {
            Pair<Integer, Integer> expiredEntry = queue.pop();
            cache.remove(expiredEntry.getKey(), expiredEntry);
        }

        Pair<Integer, Integer> newEntry = new Pair<>(key, value);
        queue.add(newEntry);
        cache.put(key, newEntry);
    }

    public int size() {
        if (queue.size() != cache.size()) {
            throw new RuntimeException("size mismatch");
        }
        return cache.size();
    }
}
