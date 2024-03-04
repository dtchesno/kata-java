package com.dtchesno.kata.struct;

import com.intellij.ui.treeStructure.Tree;
import javafx.util.Pair;
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
    // Cracking...4.7 pg.130 (2010)
    public boolean isSubtree(TreeNode t2) {
        if (t2 == null) {
            return true;
        }
        TreeNode t1 = this;
        while (t1 != null) {
            if (t1.key == t2.key) {
                return isSubtree(t1, t2);
            }
            t1 = t2.key < t1.key ? t1.left : t1.right;
        }
        return false;
    }

    private boolean isSubtree(TreeNode t1, TreeNode t2) {
        if (t1 == null && t2 == null) {
            return true;
        }
        if (t1 == null || t2 == null || t1.key != t2.key) {
            return false;
        }
        return isSubtree(t1.left, t2.left) && isSubtree(t1.right, t2.right);
    }

    // find all paths which sum up to the given sum, not need to start @root
    // Cracking...4.8 pg.131 (2010)
    // [selected - 1]
    public Set<List<Integer>> findSum(int sum) {
        Set<List<Integer>> result = new HashSet<>();
        List<Integer> acc = new ArrayList<>();
        findSumDfs(this, sum, acc, result);
        return result;
    }

    private void findSumDfs(TreeNode root, int sum, List<Integer> acc, Set<List<Integer>> result) {
        if (root == null) {
            return;
        }

        acc.add(root.key);
        int total = sum;
        for (int i = acc.size() - 1; i >= 0; i--) {
            total -= acc.get(i);
            if (total == 0) {
                result.add(new ArrayList<>(acc.subList(i, acc.size())));
            }
        }

        findSumDfs(root.left, sum, acc, result);
        findSumDfs(root.right, sum, acc, result);
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

    // returns how many added
    private static int findKthLargestElements(TreeNode root, int k, int[] result) {
        if (k == 0) {
            return 0;
        }
        if (root == null) {
            return 0;
        }

        int addedCount = 0;

        // start from right
        addedCount += findKthLargestElements(root.right, k, result);
        k -= addedCount;

        // add current
        if (k > 0) {
            result[k - 1] = root.key;
            addedCount++;
            k--;
        }

        // continue with left
        if (k > 0) {
            addedCount += findKthLargestElements(root.left, k, result);
        }

        return addedCount;
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
    // https://leetcode.com/problems/find-distance-in-a-binary-tree/
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

        int[] left = distanceH(root.left, node1, node2);
        if (left[0] != -1 && left[1] != -1) {
            return left;
        }

        int[] right = distanceH(root.right, node1, node2);
        if (right[0] != -1 && right[1] != -1) {
            return right;
        }

        int d1 = Math.max(left[0], right[0]);
        int d2 = Math.max(left[1], right[1]);
        return new int[] {
                root == node1 ? 0 : d1 != -1 ? d1 + 1 : -1,
                root == node2 ? 0 : d2 != -1 ? d2 + 1 : -1
        };
    }


    // find all nodes with distance K in binary tree
    // https://leetcode.com/problems/all-nodes-distance-k-in-binary-tree/
    // Solution:
    //  - dfs to establish parent path
    //  - use 'seen' HashSet to avoid going same path via parent
    //  - bfs from target to left/right/parent, adding null and increasing distance when 'layer' is done (when peek is null from prev.layer)
    //  - return bfs queue content except top null entry
    //  - return bfs queue content except top null entry
//    public static List<Integer> distanсeK(TreeNode root, TreeNode target, int K) {
//        Map<TreeNode, TreeNode> parent = new HashMap<>();
//        distanсeKDFS(root, null, parent);
//
//        Queue<TreeNode> q = new LinkedList<>();
//        Set<TreeNode> seen = new HashSet<>();
//        seen.add(null); // to avoid check for null left/right/parent
//        q.add(target);
//        q.add(null);
//        int distance = 0;
//        while (!q.isEmpty() && distance < K) {
//            TreeNode top = q.poll();
//            if (top == null) {
//                continue;
//            }
//            seen.add(top);
//
//            if (!seen.contains(top.left)) {
//                q.add(top.left);
//            }
//            if (!seen.contains(top.right)) {
//                q.add(top.right);
//            }
//            if (!seen.contains(parent.get(top))) {
//                q.add(parent.get(top));
//            }
//            if (!q.isEmpty() && q.peek() == null) {
//                distance++;
//                q.add(null);
//            }
//        }
//
//        List<Integer> res = new ArrayList<>();
//        while (!q.isEmpty()) {
//            TreeNode entry = q.poll();
//            if (entry == null) {
//                continue;
//            }
//            res.add(entry.key);
//        }
//        return res;
//    }
//
//    private static void distanсeKDFS(TreeNode node, TreeNode parent, Map<TreeNode, TreeNode> map) {
//        if (node == null) {
//            return;
//        }
//        map.put(node, parent);
//        distanсeKDFS(node.left, node, map);
//        distanсeKDFS(node.right, node, map);
//    }

    // another approach for above - dfs to find parent, then bfs to find K-distance
    public static List<Integer> distanсeK(TreeNode root, TreeNode target, int K) {
        Map<TreeNode, TreeNode> parentMap = new HashMap<>();
        dfs(null, root, target, parentMap);
        List<Integer> result = new ArrayList<>();
        dfs(target, target, K, parentMap, result);
        return result;
    }

    private static void dfs(TreeNode parent, TreeNode root, TreeNode target, Map<TreeNode, TreeNode> parentMap) {
        if (root == null) {
            return;
        }
        parentMap.put(root, parent);
        if (root == target) {
            return;
        }
        dfs(root, root.left, target, parentMap);
        dfs(root, root.right, target, parentMap);
    }

    private static void dfs(TreeNode root, TreeNode guard, int K, Map<TreeNode, TreeNode> parentMap, List<Integer> result) {
        if (root == null) {
            return;
        }
        if (K == 0) {
            result.add(root.key);
            return;
        }

        // go down
        if (root.left != guard) {
            dfs(root.left, root, K - 1, parentMap, result);
        }
        if (root.right != guard) {
            dfs(root.right, root, K - 1, parentMap, result);
        }

        // go up
        dfs(parentMap.get(root), root, K - 1, parentMap, result);
    }

    // Aziz 10.15 pg169
    // https://leetcode.com/problems/boundary-of-binary-tree/description/
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
        return node != null && node.left == null && node.right == null;
    }

    // https://leetcode.com/discuss/interview-question/125084/given-a-binary-search-tree-find-the-distance-between-2-nodes
    public static int distanceBST(TreeNode root, TreeNode node1, TreeNode node2) {
        TreeNode p = root;
        while (p.key > node1.key && p.key > node2.key) p = p.left;
        while (p.key < node1.key && p.key < node2.key) p = p.right;
        return distanceBST(p, node1) + distanceBST(p, node2);
    }

    private static int distanceBST(TreeNode p, TreeNode n) {
        int d = 0;
        while (p != n) {
            p = p.key > n.key ? p.left : p.right;
            d++;
        }
        return d;
    }


    // leetcode 236
    // https://leetcode.com/problems/lowest-common-ancestor-of-a-binary-tree/description/sss
    public static TreeNode findLCA(TreeNode root, TreeNode p, TreeNode q) {
        sLca = null;
        traverse(root, p, q);
        return sLca;
    }

    private static TreeNode sLca;

    private static int traverse(TreeNode root, TreeNode p, TreeNode q) {
        if (root == null || sLca != null) {
            return 0;
        }

        int left = traverse(root.left, p, q);
        if (sLca != null) {
            return left;
        }

        int right = traverse(root.right, p, q);
        if (sLca != null) {
            return right;
        }

        int mid = root == p || root == q ? 1 : 0;
        if (left + right + mid == 2) {
            sLca = root;
        }
        return left + right + mid;
    }


    // https://leetcode.com/problems/lowest-common-ancestor-of-deepest-leaves/
    public TreeNode lcaDeepestLeaves(TreeNode root) {
        return traverse(root, 0).getKey();
    }

    private static Pair<TreeNode, Integer> traverse(TreeNode root, int depth) {
        if (root == null) {
            return null;
        }

        Pair<TreeNode, Integer> left = traverse(root.left, depth + 1);
        Pair<TreeNode, Integer> right = traverse(root.right, depth + 1);

        if (root.left == null && root.right == null) {
            return new Pair(root, depth);
        } else if (left == null) {
            return right;
        }
        if (right == null) {
            return left;
        }

        if (left.getValue() > right.getValue()) {
            return left;
        } else if (right.getValue() > left.getValue()) {
            return right;
        }
        return new Pair<>(root, left.getValue());
    }


    // leetcode 987: // https://leetcode.com/problems/vertical-order-traversal-of-a-binary-tree/ (hard) [tree, bfs]
    public List<List<Integer>> verticalOrder(TreeNode root) {
        TreeMap<Integer, List<Integer>> columns = new TreeMap<>();

        // (column index, node); left to right; 0 is root
        Queue<Pair<Integer, TreeNode>> q = new LinkedList<>();
        q.add(new Pair(0, root));
        while (!q.isEmpty()) {
            var top = q.poll();
            if (top.getValue() == null) {
                continue;
            }
            var column = columns.getOrDefault(top.getKey(), new ArrayList<>());
            columns.put(top.getKey(), column);
            column.add(top.getValue().key);
            q.add(new Pair(top.getKey() - 1, top.getValue().left));
            q.add(new Pair(top.getKey() + 1, top.getValue().right));
        }

        List<List<Integer>> result = new ArrayList<>();
        for (var column : columns.values()) {
            result.add(column);
        }
        return result;
    }

    private static Map<Integer, ArrayList<Pair<Integer, Integer>>> map = new HashMap<>();
    private static int minColumn = 0;
    private static int maxColumn = 0;

    private static void dfs(TreeNode root, int row, int col) {
        if (root == null) {
            return;
        }

        if (!map.containsKey(col)) {
            map.put(col, new ArrayList<Pair<Integer, Integer>>());
        }

        map.get(col).add(new Pair<Integer, Integer>(row, root.key));
        minColumn = Math.min(minColumn, col);
        maxColumn = Math.max(maxColumn, col);

        dfs(root.left, row + 1, col - 1);
        dfs(root.right, row + 1, col + 1);
    }

    // leetcode 96): https://leetcode.com/problems/binary-tree-cameras/
    public static int minCameraCover(TreeNode root) {
        Set<TreeNode> cameras = new HashSet<>();
        Set<TreeNode> covered = new HashSet<>();
        covered.add(null);
        minCameraCoverDfs(root, null, covered, cameras);
        return cameras.size() + (!covered.contains(root) ? 1 : 0);
    }

    private static void minCameraCoverDfs(TreeNode node, TreeNode parent, Set<TreeNode> covered, Set<TreeNode> cameras) {
        if (node == null) {
            return;
        }

        minCameraCoverDfs(node.left, node, covered, cameras);
        minCameraCoverDfs(node.right, node, covered, cameras);

        if (!covered.contains(node.left) || !covered.contains(node.right)) {
            cameras.add(node);
            covered.add(node);
            covered.add(node.left);
            covered.add(node.right);
            covered.add(parent);
        }
    }

    // done

    // https://leetcode.com/problems/binary-tree-vertical-order-traversal/ (medium) [tree, bfs]
    // https://leetcode.com/problems/binary-tree-right-side-view/ (medium) [tree, bfs]
}
