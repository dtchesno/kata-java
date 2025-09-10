package com.dtchesno.kata.struct;

import java.util.*;

// 588. Design In-Memory File System
// https://leetcode.com/problems/design-in-memory-file-system
public class FileSystem {

//    public List<String> ls(String path) {
//        return null;
//    }
//
//    public void mkdir(String path) {
//    }
//
//    public void addContentToFile(String filePath, String content) {
//    }
//
//    public String readContentFromFile(String filePath) {
//        return null;
//    }

    private static class Node {
        TreeMap<String, Node> childs = new TreeMap<>();
        String name;
        String content;

        Node(String name) {
            this.name = name;
        }
    }

    private Node root = new Node("");

    public List<String> ls(String path) {
        Node n = find(path, false);
        if (n.content != null) {
            return List.of(n.name);
        } else {
            return new ArrayList<>(n.childs.keySet());
        }
    }

    public void mkdir(String path) {
        Node n = find(path, true);
    }

    public void addContentToFile(String filePath, String content) {
        Node n = find(filePath, true);
        if (n.content == null) {
            n.content = content;
        } else {
            n.content += content;
        }
    }

    public String readContentFromFile(String filePath) {
        Node n = find(filePath, false);
        return n.content;
    }

    private Node find(String path, boolean doCreate) {
        String[] segments = path.split("/");

        Node n = root;
        for (int i = 1; i < segments.length; i++) {
            Node next = n.childs.get(segments[i]);
            if (next == null && doCreate) {
                next = new Node(segments[i]);
                n.childs.put(segments[i], next);
            }
            n = next;
        }
        return n;
    }
}