package com.dtchesno.kata.struct;

import java.util.*;


public class Trie {
    private static class Node {
        Map<Character, Node> childs = new HashMap<>();
        String word;
    }

    private Node root = new Node();

    public static Trie build(List<String> words) {
        Trie t = new Trie();
        for (String word : words) {
            t.add(word);
        }
        return t;
    }

    public List<String> getValues(String prefix) {
        List<String> result = new ArrayList<>();
        Node n = root;
        for (char c : prefix.toCharArray()) {
            n = n.childs.get(c);
            if (n == null) {
                return result;
            }
        }
        Queue<Node> q = new LinkedList<>();
        q.add(n);
        while (!q.isEmpty()) {
            n = q.poll();
            if (n.word != null) {
                result.add(n.word);
            }
            for (Node child : n.childs.values()) {
                q.add(child);
            }
        }
        return result;
    }

    private void add(String word) {
        Node n = root;
        for (int i = 0; i < word.length(); i++) {
            Node next = n.childs.get(word.charAt(i));
            if (next == null) {
                next = new Node();
                n.childs.put(word.charAt(i), next);
            }
            n = next;
        }
        n.word = word;
    }
}
