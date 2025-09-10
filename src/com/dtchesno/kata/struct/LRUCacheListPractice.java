package com.dtchesno.kata.struct;

import com.intellij.ide.util.newProjectWizard.modes.CreateModuleFromSourcesMode;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

// 146. LRU Cache
// https://leetcode.com/problems/lru-cache/
public class LRUCacheListPractice implements ILRUCache {

    // public LRUCacheListPractice(int capacity)
    // public int get(int key)
    // public void put(int key, int value)
    // public int size()

    private static class Node {
        Node prev;
        Node next;
        int key;
        int value;

        Node(int key, int value) {
            this.key = key;
            this.value = value;
        }
    }

    private int capacity;
    private Map<Integer, Node> cache = new HashMap<>();
    private Node root;
    private Node tail;

    public LRUCacheListPractice(int capacity) {
        this.capacity = capacity;
        this.root = new Node(0, 0);
        this.tail = new Node(0, 0);
        root.next = tail;
        tail.prev = root;
    }

    public int get(int key) {
        Node n = cache.get(key);
        if (n == null) return -1;
        remove(n);
        add(n);
        return n.value;
    }

    public void put(int key, int value) {
        Node n = cache.get(key);
        if (n != null) {
            remove(n);
            n.value = value;
        } else {
            n = new Node(key, value);
        }
        if (cache.size() == capacity) {
            remove(root.next);
        }
        add(n);
    }

    public int size() {
        return cache.size();
    }

    private void add(Node n) {
        Node last = tail.prev;
        last.next = n;
        n.prev = last;
        n.next = tail;
        tail.prev = n;
        cache.put(n.key, n);
    }

    private void remove(Node n) {
        cache.remove(n.key);
        Node prev = n.prev;
        Node next = n.next;
        prev.next = next;
        next.prev = prev;
        n.prev = null;
        n.next = null;
    }
}
