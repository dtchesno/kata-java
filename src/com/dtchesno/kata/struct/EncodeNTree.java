package com.dtchesno.kata.struct;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class EncodeNTree {

    public static class Node {
        public int val;
        public List<Node> children;

        public Node() {}

        public Node(int _val) {
            val = _val;
        }

        public Node(int _val, List<Node> _children) {
            val = _val;
            children = _children;
        }

        public static List<Integer> serialize(Node root) {
            List<Integer> result = new ArrayList<>();
            Queue<Node> q = new LinkedList<>();
            q.add(root);
            q.add(null);
            while (!q.isEmpty()) {
                Node top = q.poll();
                result.add(top != null ? top.val : null);
                if (top == null || top.children == null) continue;
                for (Node child : top.children) {
                    q.add(child);
                }
                if (!q.isEmpty()) q.add(null);
            }
            return result.subList(0, result.size() - 1);
        }
    };

    public static class TreeNode {
        int val;
        TreeNode left;
        TreeNode right;
        TreeNode(int x) { val = x; }
    }

    public static class Codec {
        public TreeNode encode(Node root) {
            return null;
        }

        public Node decode(TreeNode root) {
            return null;
        }
    }
}
