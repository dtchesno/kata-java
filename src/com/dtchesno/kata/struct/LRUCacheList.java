package com.dtchesno.kata.struct;

import java.util.HashMap;

public class LRUCacheList {

    private static class Entry {
        int key;
        int value;
        Entry prev;
        Entry next;
        Entry(int key, int value) {
            this.key = key;
            this.value = value;
        }
    }

    private int capacity;
    private HashMap<Integer, Entry> cache = new HashMap<>();
    private Entry root = new Entry(0, 0);
    private Entry tail = new Entry(0, 0);

    public LRUCacheList(int capacity) {
        this.capacity = capacity;
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

    private void add(Entry entry) {
        var last = tail.prev;
        last.next = entry;
        entry.prev = last;
        entry.next = tail;
        tail.prev = entry;
        cache.put(entry.key, entry);
    }

    private void remove(Entry entry) {
        var prev = entry.prev;
        var next = entry.next;
        prev.next = next;
        next.prev = prev;
        entry.prev = entry.next = null;
        cache.remove(entry.key);
    }
}
