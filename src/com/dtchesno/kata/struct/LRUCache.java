package com.dtchesno.kata.struct;

import java.util.HashMap;

public class LRUCache {

    // TODO: running multiple tests at once will fail due to static context
    private static class Node {
        static Node head = new Node(0, 0);
        static Node tail = new Node(0, 0);
        static int size = 0;

        static {
            head.next = tail;
            tail.prev = head;
        }

        int key;
        int value;
        Node prev;
        Node next;

        Node(int key, int value) {
            this.key = key;
            this.value = value;
        }

        static void add(Node n) {
            Node prev = tail.prev;
            prev.next = n;
            n.prev = prev;
            n.next = tail;
            tail.prev = n;
            size++;
        }

        static void remove(Node n) {
            n.prev.next = n.next;
            n.next.prev = n.prev;
            size--;
        }

        static Node poll() {
            Node n = head.next;
            remove(n);
            return n;
        }

        static int size() {
            return size;
        }
    }

    int capacity;
    HashMap<Integer, Node> cache = new HashMap<>();

    public LRUCache(int capacity) {
        this.capacity = capacity;
    }

    public int get(int key) {
        Node node = cache.get(key);

        if (node == null)
            return -1;

        Node.remove(node);
        Node.add(node);
        return node.value;
    }

    public void put(int key, int value) {
        Node entry = cache.get(key);

        if (entry != null) {
            entry.value = value;
            Node.remove(entry);
            Node.add(entry);
            return;
        }

        entry = new Node(key, value);

        if (cache.size() == capacity) {
            Node expiredEntry = Node.poll();
            cache.remove(expiredEntry.key);
        }

        cache.put(key, entry);
        Node.add(entry);
    }

    public int size() {
        if (Node.size() != cache.size()) {
            throw new RuntimeException("size mismatch");
        }
        return cache.size();
    }
}
