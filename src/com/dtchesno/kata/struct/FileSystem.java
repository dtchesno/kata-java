package com.dtchesno.kata.struct;

import java.util.*;

public class FileSystem {

    private static class Node {
        String name;
        Map<String, Node> childs = new TreeMap<>();
        String content;

        private Node(String name) {
            this.name = name;
        }
    }

    private Node root = new Node("");

    public List<String> ls(String path) {
        List<String> result = new ArrayList<>();
        Node n = find(path, false);
        if (n.content != null) {
            result.add(n.name);
        } else {
            result.addAll(n.childs.keySet());
        }
        return result;
    }

    public void mkdir(String path) {
        find(path, true);
    }

    public void addContentToFile(String filePath, String content) {
        Node n = find(filePath, true);
        n.content = n.content == null ? content : n.content + content;
    }

    public String readContentFromFile(String filePath) {
        Node n = find(filePath, false);
        return n.content;
    }

    private Node find(String path, boolean isCreate) {
        String[] names = path.split("/");
        Node n = root;
        for (int i = 1; i < names.length; i++) {
            String name = names[i];
            Node child = n.childs.get(name);
            if (child == null && isCreate) {
                child = new Node(name);
                n.childs.put(name, child);
            }
            n = child;
        }
        return n;
    }
}