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

    // new TreeNode(new Integer[] { 3, 5, 1, 1, 6, 0, 8, null, null, 7, 4 })
    //              3
    //       5              1
    //    1      6        0    8
    //          7 4
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
    // Cracking...4.8 pg.131
    // [selected - 1]
    public Set<ArrayList<Integer>> findSum(int sum) {
        Set<ArrayList<Integer>> acc = new HashSet<>();
        findSum(this, sum, new ArrayList<Integer>(), acc);
        return acc;
    }

    private static void findSum(TreeNode t, int sum, ArrayList<Integer> buffer, Set<ArrayList<Integer>> acc) {
        if (t == null) return;
        buffer.add(t.key);

        int pathSum = 0;
        for (int i = buffer.size() - 1; i >= 0; i--) {
            pathSum += buffer.get(i);
            if (pathSum == sum) {
                acc.add(new ArrayList<>(buffer.subList(i, buffer.size())));
            }
        }
        findSum(t.left, sum, buffer, acc);
        findSum(t.right, sum, buffer, acc);
        buffer.remove(buffer.size() - 1);
    }


    // build doubly-linked list from tree
    // byte-by-byte #21 pg20
    // [selected - 2]
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
        return isHalfCuttable(tree, hash);
    }

    private static boolean isHalfCuttable(TreeNode root, Map<TreeNode, int[]> hash) {
        if (root == null) {
            return false;
        }

        int[] sums = hash.get(root);

        if (sums[0] == 2 * (root.key + sums[1] + sums[2])
                || sums[1] == root.key + sums[2] + sums[0]
                || sums[2] == root.key + sums[1] + sums[0]) {
            return true;
        }

        return isHalfCuttable(root.left, hash) || isHalfCuttable(root.right, hash);
    }

    private static int calculateSum(TreeNode node, Map<TreeNode, int[]> hash) {
        if (node == null) {
            return 0;
        }

        int[] sums = new int[] {0, 0, 0};
        sums[1] = calculateSum(node.left, hash);
        sums[2] = calculateSum(node.right, hash);
        hash.put(node, sums);
        setParentSum(node.left, node.key + sums[2], hash);
        setParentSum(node.right, node.key + sums[1], hash);
        return node.key + sums[1] + sums[2];
    }

    private static void setParentSum(TreeNode node, int parentSum, Map<TreeNode, int[]> hash) {
        if (node == null) {
            return;
        }
        int[] sums = hash.get(node);
        sums[0] = parentSum;
    }


    // compute distance between nodes
    // [selected - 1]
    public static int distance(TreeNode root, TreeNode node1, TreeNode node2) {
        if (node1 == node2) {
            return 0;
        }
        int[] d = distanceH(root, node1, node2);
        return d[0] + d[1];
    }

    private static int[] distanceH(TreeNode root, TreeNode node1, TreeNode node2) {
        if (root == null) {
            return new int[] {-1, -1};
        }

        int[] leftDistance = distanceH(root.left, node1, node2);
        int[] rightDistance = distanceH(root.right, node1, node2);

        // both are in left subtree
        if (leftDistance[0] >= 0 && leftDistance[1] >= 0) {
            return leftDistance;
        }

        // both are in right subtree
        if (rightDistance[0] >= 0 && rightDistance[1] >= 0) {
            return rightDistance;
        }

        // node could be the same as root, in left/right subtree or not found in either
        int distance1 = Math.max(leftDistance[0], rightDistance[0]);
        int distance2 = Math.max(leftDistance[1], rightDistance[1]);
        return new int[] {
                root == node1 ? 0 : distance1 == -1 ? -1 : 1 + distance1,
                root == node2 ? 0 : distance2 == -1 ? -1 : 1 + distance2
        };
    }


    // find all nodes with distance K in binary tree
    // https://leetcode.com/problems/all-nodes-distance-k-in-binary-tree/
    public static List<Integer> distanсeK(TreeNode root, TreeNode target, int K) {
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

    // Aziz 10.15 pg169
    // [selected - 2]
    public static List<Integer> exteriorBinaryTree(TreeNode root) {
        ArrayList<Integer> result = new ArrayList<>();
        if (root != null) {
            result.add(root.key);
            exteriorAddLeftAndLeaves(root.left, true, result);
            exteriorAddRightAndLeaves(root.right, true, result);
        }
        return result;
    }

    private static void exteriorAddLeftAndLeaves(TreeNode node, boolean isBoundary, List<Integer> result) {
        if (node == null) {
            return;
        }
        if (isBoundary || isLeaf(node)) {
            result.add(node.key);
        }
        exteriorAddLeftAndLeaves(node.left, isBoundary, result);
        exteriorAddLeftAndLeaves(node.right, isBoundary && node.left == null, result);
    }

    private static void exteriorAddRightAndLeaves(TreeNode node, boolean isBoundary, List<Integer> result) {
        if (node == null) {
            return;
        }
        exteriorAddRightAndLeaves(node.left, isBoundary && node.right == null, result);
        exteriorAddRightAndLeaves(node.right, isBoundary, result);
        if (isBoundary || isLeaf(node)) {
            result.add(node.key);
        }
    }

    private static boolean isLeaf(TreeNode node) {
        return node.left == null && node.right == null;
    }

    // https://leetcode.com/discuss/interview-question/125084/given-a-binary-search-tree-find-the-distance-between-2-nodes
    public static int distanceBST(TreeNode root, TreeNode node1, TreeNode node2) {
        TreeNode lca = root;
        while (node1.key < lca.key && node2.key < lca.key) {
            lca = lca.left;
        }
        while (node1.key > lca.key && node2.key > lca.key) {
            lca = lca.right;
        }
        return distanceBST(lca, node1) + distanceBST(lca, node2);
    }

    private static int distanceBST(TreeNode root, TreeNode node) {
        if (node.key == root.key) {
            return 0;
        }
        return 1 + distanceBST(node.key < root.key ? root.left : root.right, node);
    }

    // done
    // https://leetcode.com/problems/lowest-common-ancestor-of-a-binary-tree/
    // https://leetcode.com/problems/lowest-common-ancestor-of-a-binary-tree-iv/
    // https://leetcode.com/problems/lowest-common-ancestor-of-deepest-leaves/
    // https://leetcode.com/problems/binary-tree-cameras/
    // https://leetcode.com/problems/binary-tree-vertical-order-traversal/ (medium) [tree, bfs]
    // https://leetcode.com/problems/binary-tree-right-side-view/ (medium) [tree, bfs]
    // https://leetcode.com/problems/vertical-order-traversal-of-a-binary-tree/ (hard) [tree, bfs]
}
