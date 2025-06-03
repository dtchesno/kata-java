package com.dtchesno.kata.struct;

import java.util.HashMap;
import java.util.Map;

public class LRUCacheListPractice implements ILRUCache {

    private static class Entry {
        int key;
        int value;
        Entry next;
        Entry prev;

        Entry(int key, int value) {
            this.key = key;
            this.value = value;
        }
    }

    private int capacity;

    private Map<Integer, Entry> cache = new HashMap();
    private Entry root;
    private Entry tail;

    public LRUCacheListPractice(int capacity) {
        this.capacity = capacity;
        root = new Entry(0, 0);
        tail = new Entry(0, 0);
        root.next = tail;
        tail.prev = root;
    }

    public int get(int key) {
        Entry entry = cache.get(key);
        if (entry == null) return -1;
        remove(entry);
        add(entry);
        return entry.value;
    }

    public void put(int key, int value) {
        Entry entry = cache.get(key);
        if (entry != null) {
            remove(entry);
            entry.value = value;
        } else {
            entry = new Entry(key, value);
        }
        if (cache.size() == capacity) {
            remove(root.next);
        }
        add(entry);
    }

    public int size() {
        return cache.size();
    }

    private void remove(Entry entry) {
        entry.prev.next = entry.next;
        entry.next.prev = entry.prev;
        cache.remove(entry.key);
    }

    private void add(Entry entry) {
        entry.prev = tail.prev;
        entry.next = tail;

        tail.prev.next = entry;
        tail.prev = entry;

        cache.put(entry.key, entry);
    }
}
