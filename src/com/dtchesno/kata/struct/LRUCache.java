package com.dtchesno.kata.struct;

import java.util.LinkedHashMap;

public class LRUCache {

    private int capacity;
    private LinkedHashMap<Integer, Integer> cache = new LinkedHashMap<>();

    public LRUCache(int capacity) {
        this.capacity = capacity;
    }

    public int get(int key) {
        Integer value = cache.get(key);
        if (value == null) return -1;
        cache.remove(key);
        cache.put(key, value);
        return value;
    }

    public void put(int key, int value) {
        Integer oldValue = cache.get(key);
        if (oldValue != null) {
            cache.remove(key);
        }
        cache.put(key, value);
        if (cache.size() > capacity) {
            var it = cache.keySet().iterator();
            it.next();
            it.remove();
        }
    }

    public int size() {
        return cache.size();
    }
}
