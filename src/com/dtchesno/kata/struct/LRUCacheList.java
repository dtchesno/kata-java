package com.dtchesno.kata.struct;

import java.util.HashMap;

public class LRUCacheList {

    private static class Node {
        int key;
        int value;
        Node prev;
        Node next;

        Node(int key, int value) {
            this.key = key;
            this.value = value;
        }
    }

    private static class Queue {
        private Node head = new Node(0, 0);
        private Node tail = new Node(0, 0);
        private int size = 0;

        public Queue() {
            head.next = tail;
            tail.prev = head;
        }

        public void add(Node n) {
            Node prev = tail.prev;
            prev.next = n;
            n.prev = prev;
            n.next = tail;
            tail.prev = n;
            size++;
        }

        public void remove(Node n) {
            n.prev.next = n.next;
            n.next.prev = n.prev;
            size--;
        }

        public Node poll() {
            Node n = head.next;
            remove(n);
            return n;
        }

        public int size() {
            return size;
        }
    }

    int capacity;
    HashMap<Integer, Node> cache = new HashMap<>();
    Queue queue = new Queue();

    public LRUCacheList(int capacity) {
        this.capacity = capacity;
    }

    public int get(int key) {
        Node node = cache.get(key);

        if (node == null)
            return -1;

        queue.remove(node);
        queue.add(node);
        return node.value;
    }

    public void put(int key, int value) {
        Node entry = cache.get(key);

        if (entry != null) {
            entry.value = value;
            queue.remove(entry);
            queue.add(entry);
            return;
        }

        entry = new Node(key, value);

        if (cache.size() == capacity) {
            Node expiredEntry = queue.poll();
            cache.remove(expiredEntry.key);
        }

        cache.put(key, entry);
        queue.add(entry);
    }

    public int size() {
        if (queue.size() != cache.size()) {
            throw new RuntimeException("size mismatch");
        }
        return cache.size();
    }
}
