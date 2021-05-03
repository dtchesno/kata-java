package com.dtchesno.kata.struct;

import java.util.HashMap;

public class LRUCache {

    private static class Node {
        static Node head = new Node(0, 0);
        static Node tail = new Node(0, 0);

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
        }

        static void remove(Node n) {
            n.prev.next = n.next;
            n.next.prev = n.prev;
        }

        static Node poll() {
            Node n = head.next;
            remove(n);
            return n;
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
        Node n = new Node(key, value);
        cache.put(key, n);
        if (cache.size() > capacity) {
            cache.remove(Node.poll().key);
        }
        Node.add(n);
    }
}
