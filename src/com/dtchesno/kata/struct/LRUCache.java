package com.dtchesno.kata.struct;

import java.util.Iterator;
import java.util.LinkedHashMap;

public class LRUCache {

    int capacity;
    LinkedHashMap<Integer, Integer> cache = new LinkedHashMap<>();

    public LRUCache(int capacity) {
        this.capacity = capacity;
    }

    public int get(int key) {
        Integer value = cache.remove(key);
        if (value == null) {
            return -1;
        }
        cache.put(key, value);
        return value;
    }

    public void put(int key, int value) {
        int oldValue = get(key);
        if (oldValue != value) {
            cache.put(key, value);
        }
        if (cache.size() > capacity) {
            Iterator<Integer> it = cache.keySet().iterator();
            if (it.hasNext()) {
                it.next();
                it.remove();
            }
        }
    }

    public int size() {
        return cache.size();
    }
}
