package com.dtchesno.kata.struct;

import javafx.util.Pair;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

public class LRUCacheSimplified implements ILRUCache {

    private static class Entry extends Pair<Integer, Integer> {
        Entry(int key, int value) {
            super(key, value);
        }
    }

    private int capacity;

    private Map<Integer, Entry> cache = new HashMap<>();
    private LinkedList<Entry> queue  = new LinkedList<>();

    public LRUCacheSimplified(int capacity) {
        this.capacity = capacity;
    }

    public int get(int key) {
        Entry entry = cache.get(key);

        if (entry == null) {
            return -1;
        }

        // this is simplification and not fully efficient
        // ideally we want to have pointer to element in LRU queue to have O(1) access cost
        queue.remove(entry);
        queue.add(entry);

        return entry.getValue();
    }

    public void put(int key, int value) {
        Entry current = cache.get(key);
        Entry newEntry = new Entry(key, value);

        // key is in cache
        if (current != null) {
            queue.remove(current);
        }
        queue.add(newEntry);
        cache.put(key, newEntry);

        if (queue.size() > capacity) {
            Entry expired = queue.remove();
            cache.remove(expired.getKey());
        }
    }

    public int size() {
        return queue.size();
    }
}
