package com.dtchesno.kata.struct;

import java.util.*;

public class TreeNode {
    public TreeNode left = null;
    public TreeNode right = null;
    public int key;

    public static int minDepth(TreeNode root) {
        if (root == null) {
            return 0;
        }
        return 1 + Math.min(minDepth(root.left), minDepth(root.right));
    }

    public static int maxDepth(TreeNode root) {
        if (root == null) {
            return 0;
        }
        return 1 + Math.max(maxDepth(root.left), maxDepth(root.right));
    }

    public static boolean isBalanced(TreeNode root) {
        return maxDepth(root) - minDepth(root) <= 1;
    }

    public TreeNode(int value) {
        key = value;
    }

    public TreeNode(Integer[] values) {
        LinkedList<TreeNode> q = new LinkedList<>();
        this.key = values[0];
        q.add(this);
        int i = 1;
        while (!q.isEmpty() && i < values.length) {
            TreeNode p = q.poll();
            TreeNode l = (i < values.length && values[i] != null) ? new TreeNode(values[i]) : null;
            i++;
            TreeNode r = (i < values.length && values[i] != null) ? new TreeNode(values[i]) : null;
            i++;
            p.left = l;
            p.right = r;
            if (l != null) {
                q.add(l);
            }
            if (r != null) {
                q.add(r);
            }
        }
    }

    public TreeNode insert(int value) {
        if (value <= key) {
            if (left == null) {
                left = new TreeNode(value);
            } else {
                left.insert(value);
            }
        } else {
            if (right == null) {
                right = new TreeNode(value);
            } else {
                right.insert(value);
            }
        }
        return this;
    }

    public static void inOrder(TreeNode root, List<Integer> res) {
        if (root == null) {
            return;
        }
        inOrder(root.left, res);
        res.add(root.key);
        inOrder(root.right, res);
    }

    public static void inOrderI(TreeNode root, List<Integer> res) {
        if (root == null) {
            return;
        }

        Stack<TreeNode> stack = new Stack();
        TreeNode node = root;
        while (!stack.isEmpty() || node != null) {
            if (node != null) {
                stack.push(node);
                node = node.left;
            } else {
                node = stack.pop();
                res.add(node.key);
                node = node.right;
            }
        }
    }

    public static void preOrder(TreeNode root, List<Integer> res) {
        if (root == null) {
            return;
        }
        res.add(root.key);
        preOrder(root.left, res);
        preOrder(root.right, res);
    }

    public static void preOrderI(TreeNode root, List<Integer> res) {
        if (root == null) {
            return;
        }

        Stack<TreeNode> stack = new Stack();
        stack.push(root);
        while (!stack.isEmpty()) {
            TreeNode n = stack.pop();
            res.add(n.key);
            if (n.right != null) {
                stack.push(n.right);
            }
            if (n.left != null) {
                stack.push(n.left);
            }
        }
    }

    public static void postOrder(TreeNode root, List<Integer> res) {
        if (root == null) {
            return;
        }
        postOrder(root.left, res);
        postOrder(root.right, res);
        res.add(root.key);
    }

    public static void postOrderI(TreeNode root, List<Integer> res) {
        if (root == null) {
            return;
        }

        Stack<TreeNode> stack = new Stack();
        TreeNode node = root;
        TreeNode last = null;
        while (!stack.isEmpty() || node != null) {
            if (node != null) {
                stack.push(node);
                node = node.left;
            } else {
                TreeNode peek = stack.peek();
                if (peek.right != null && peek.right != last) {
                    node = peek.right;
                } else {
                    last = stack.pop();
                    res.add(last.key);
                }
            }
        }
    }

    public static TreeNode createBST(int[] arr) {
        return addToTree(arr, 0, arr.length - 1);
    }

    private static TreeNode addToTree(int[] arr, int start, int end) {
        if (end < start) {
            return null;
        }
        int i = (start + end) / 2;
        TreeNode root = new TreeNode(arr[i]);
        root.left = addToTree(arr, start, i - 1);
        root.right = addToTree(arr, i + 1, end);
        return root;
    }

    // is tree is subtree of this
    public boolean isSubtree(TreeNode tree) {
        if (tree == null) {
            return true;
        }
        return isSubtree(this, tree);
    }

    // is t2 subtree of t1
    private static boolean isSubtree(TreeNode t1, TreeNode t2) {
        if (t1 == null) {
            return false;
        }
        if (t1.key == t2.key) {
            return matchTree(t1, t2);
        }
        return isSubtree(t1.left, t2) || isSubtree(t1.right, t2);
    }

    private static boolean matchTree(TreeNode t1, TreeNode t2) {
        if (t1 == null && t2 == null) {
            return true;
        }
        if (t1 == null || t2 == null) {
            return false;
        }
        if (t1.key != t2.key) {
            return false;
        }
        return matchTree(t1.left, t2.left) && matchTree(t1.right, t2.right);
    }

    public Set<ArrayList<Integer>> findSum(int sum) {
        Set<ArrayList<Integer>> acc = new HashSet<>();
        findSum(this, sum, new ArrayList<Integer>(), 0, acc);
        return acc;
    }

    private static void findSum(TreeNode t, int sum, ArrayList<Integer> buffer, int level, Set<ArrayList<Integer>> acc) {
        if (t == null) {
            return;
        }
        buffer.add(t.key);
        int tmpSum = sum;
        for (int i = level; i > -1; i--) {
            tmpSum -= buffer.get(i);
            if (tmpSum == 0) {
                acc.add(new ArrayList<>(buffer.subList(i, level + 1)));
            }
        }
        ArrayList<Integer> left = (ArrayList<Integer>) buffer.clone();
        findSum(t.left, sum, left, level + 1, acc);
        ArrayList<Integer> right = (ArrayList<Integer>) buffer.clone();
        findSum(t.right, sum, right, level + 1, acc);
    }

    public static TreeNode toLinkedList(TreeNode root) {
        TreeNode[] nodes = buildList(root);
        nodes[0].left = nodes[1];
        nodes[1].right = nodes[0];
        return nodes[0];
    }

    private static TreeNode[] buildList(TreeNode root) {
        TreeNode[] l = null, r = null;
        if (root.left != null) {
            l = buildList(root.left);
            l[1].right = root;
            root.left = l[1];
        }
        if (root.right != null) {
            r = buildList(root.right);
            r[0].left = root;
            root.right = r[0];
        }
        return new TreeNode[] {
                (l != null) ? l[0] : root,
                (r != null) ? r[1] : root
        };
    }

    public static int longestConsecutiveBranch(TreeNode root) {
        return longestConsecutiveBranchH(root.key - 1, root);
    }

    private static int longestConsecutiveBranchH(int parent, TreeNode root) {
        if (root == null || root.key != parent + 1) {
            return 0;
        }
        return 1 + Math.max(
                longestConsecutiveBranchH(root.key, root.left),
                longestConsecutiveBranchH(root.key, root.right));
    }
}
