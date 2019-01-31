package com.dtchesno.kata.struct;

import java.util.*;


public class Trie {

    public static class Node {
        Character key;
        String value;
        //boolean isWord;
        HashMap<Character, Node> childs = new HashMap<>();

        Node(Character key, String value) {
            this.key = key;
            this.value = value;
        }
    }

    Node root = new Node(null, null);

    public static Trie build(List<String> words) {
        Trie t = new Trie();
        for (String s: words) {
            t.add(s);
        }
        return t;
    }

    public void add(String word) {
        Node node = root;
        for (char c: word.toCharArray()) {
            if (!node.childs.containsKey(c)) {
                Node n = new Node(c, null);
                node.childs.put(c, n);
            }
            node = node.childs.get(c);
        }
        node.value = word;
    }

    public List<String> getValues(String prefix) {
        ArrayList<String> res = new ArrayList<>();
        Node node = root;
        for (char c: prefix.toCharArray()) {
            if (!node.childs.containsKey(c)) {
                return res;
            }
            node = node.childs.get(c);
        }

        LinkedList<Node> q = new LinkedList<>();
        q.add(node);
        while (!q.isEmpty()) {
            Node n = q.poll();
            if (n.value != null) {
                res.add(n.value);
            }
            for (Node child: n.childs.values()) {
                q.add(child);
            }
        }
        return res;
    }
}
