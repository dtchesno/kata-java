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
    // Cracking...4.7 pg.130
    public boolean isSubtree(TreeNode tree) {
        if (tree == null) {
            return true;
        }
        return isSubtree(this, tree);
    }

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



    // find all paths which sum up to the given sum, not need to start @root
    // Cracking...4.8 pg.130
    // [selected - 1]
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


    // build doubly-linked list from tree
    // byte-by-byte #21 pg20
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


    // find length of longest branch starting from root and mono-increasing values
    // byte-by-byte #22 pg21
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


    // BST, find k largest elements
    // Aziz 15.3 pg260
    // [selected - 1]
    public static int[] findKthLargestElements(TreeNode root, int k) {
        int[] result = new int[k];
        findKthLargestElements(root, k, result);
        return result;
    }

    private static int findKthLargestElements(TreeNode root, int k, int[] result) {
        if (k == 0) {
            return 0;
        }
        if (root == null) {
            return k;
        }

        if (root.right != null) {
            k = findKthLargestElements(root.right, k, result);
        }
        if (k != 0) {
            result[k - 1] = root.key;
            k--;
        }
        if (k != 0) {
            k = findKthLargestElements(root.left, k, result);
        }
        return k;
    }


    // test whether tree can be cut once, so, two parts will have same sum of node values
    // FACEBOOK: https://www.careercup.com/question?id=4871620274421760
    public static boolean isHalfCuttable(TreeNode tree) {

        // int[] will have size of 3: Tree sum outside + parent; left subtree sum; right subtree sum
        HashMap<TreeNode, int[]> hash = new HashMap<>();

        calculateSum(tree, hash);
        calculateParentSum(0, 0, tree, hash);

        return isHalfCuttable(tree, hash);
    }

    // test recursively
    private static boolean isHalfCuttable(TreeNode node, HashMap<TreeNode, int[]> hash) {
        if (node == null) {
            return false;
        }

        // test we could cut left or right sub-tree
        int[] sums = hash.get(node);
        if (sums[0] + node.key + sums[1] == sums[2] || sums[0] + node.key + sums[2] == sums[1]) {
            return true;
        }

        // otherwise try to do so further for each sub-tree
        return isHalfCuttable(node.left, hash) || isHalfCuttable(node.right, hash);
    }

    // calculate left & right sub-trees sums, store results in hash using node/parent as a key
    private static int calculateSum(TreeNode tree, HashMap<TreeNode, int[]> hash) {
        if (tree == null) {
            return 0;
        }
        int lSum = calculateSum(tree.left, hash);
        int rSum = calculateSum(tree.right, hash);
        hash.put(tree, new int[] { 0, lSum, rSum });
        return tree.key + lSum + rSum;
    }

    // calculate 'upstream' tree sum, [whole tree sum] - [node value] - [this node sub-trees sums]; store in hash
    private static void calculateParentSum(int parent, int sibling, TreeNode node, HashMap<TreeNode, int[]> hash) {
        if (node == null) {
            return;
        }
        (hash.get(node))[0] = parent + sibling;
        calculateParentSum(node.key, (hash.get(node))[2], node.left, hash);
        calculateParentSum(node.key, (hash.get(node))[1], node.right, hash);
    }


    // TODO: compute distance between nodes
    // [selected - 1]


    // find all nodes with distance K in binary tree
    // https://leetcode.com/problems/all-nodes-distance-k-in-binary-tree/
    public static List<Integer> distanseK(TreeNode root, TreeNode target, int K) {
        ArrayList<Integer> result = new ArrayList<>();
        if (root == null || target == null || K < 0) {
            return result;
        }
        HashMap<TreeNode, TreeNode> parent = new HashMap<>();
        LinkedList<TreeNode> q = new LinkedList<>();
        q.push(root);
        while (!q.isEmpty()) {
            TreeNode n = q.pop();
            if (n == target) {
                break;
            }
            if (n.left != null) {
                q.push(n.left);
                parent.put(n.left, n);
            }
            if (n.right != null) {
                q.push(n.right);
                parent.put(n.right, n);
            }
        }
        distanceK(target, null, K, result);
        TreeNode p = parent.get(target);
        TreeNode guard = target;
        int k = K - 1;
        while (p != null && k >= 0) {
            distanceK(p, guard, k--, result);
            guard = p;
            p = parent.get(p);
        }
        return result;
    }

    private static void distanceK(TreeNode parent, TreeNode guard, int K, List<Integer> result) {
        if (parent == null) {
            return;
        }
        if (K == 0) {
            result.add(parent.key);
            return;
        }
        if (parent.left != guard) {
            distanceK(parent.left, null, K - 1, result);
        }
        if (parent.right != guard) {
            distanceK(parent.right, null, K - 1, result);
        }
    }


    // TODO:
    // https://leetcode.com/discuss/interview-question/125084/given-a-binary-search-tree-find-the-distance-between-2-nodes
}
