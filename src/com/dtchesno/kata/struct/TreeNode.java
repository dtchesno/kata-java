package com.dtchesno.kata.struct;

import com.intellij.execution.wsl.WSLCommandLineOptions;
import com.intellij.ui.treeStructure.Tree;
import javafx.util.Pair;
import org.apache.commons.lang3.tuple.Triple;
import org.jetbrains.org.objectweb.asm.util.TraceRecordComponentVisitor;

import java.util.*;
import java.util.stream.Collectors;

public class TreeNode {
    public TreeNode left = null;
    public TreeNode right = null;
    public int key;

    public static TreeNode buildBinaryTree(Integer[] values) {
        TreeNode root = new TreeNode(values[0]);
        Queue<TreeNode> q = new LinkedList<>();
        q.add(root);
        int i = 1;
        while (!q.isEmpty() && i < values.length) {
            TreeNode top = q.poll();
            if (i < values.length && values[i] != null) {
                top.left = new TreeNode(values[i]);
                q.add(top.left);
            }
            i++;
            if (i < values.length && values[i] != null) {
                top.right = new TreeNode(values[i]);
                q.add(top.right);
            }
            i++;
        }
        return root;
    }

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

    private static int findKthLargestElements(TreeNode root, int k, int[] result) {
        if (root == null || k == 0) return k;
        k = findKthLargestElements(root.right, k, result);
        if (k > 0) {
            result[k - 1] = root.key;
            k--;
        }
        return findKthLargestElements(root.left, k, result);
    }
//    private static int findKthLargestElements(TreeNode root, int k, int[] result) {
//        if (root == null) return 0;
//        k = findKthLargestElements(root.right, k, result);
//        if (k > 0) {
//            result[k - 1] = root.key;
//            k--;
//        }
//        if (k > 0) {
//            k = findKthLargestElements(root.left, k, result);
//        }
//        return k;
//    }


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
    // 1740. Find Distance in a Binary Tree
    // https://leetcode.com/problems/find-distance-in-a-binary-tree/
    // [selected - 1]
    public static int distance(TreeNode root, TreeNode node1, TreeNode node2) {
        if (node1 == node2) {
            return 0;
        }
        int[] d = distanceH(root, node1, node2);
        return d[0] + d[1];
    }

    private static int[] distanceH(TreeNode node, TreeNode p, TreeNode q) {
        if (node == null) return new int[] { -1, -1 };

        int[] left = distanceH(node.left, p, q);
        if (left[0] != -1 && left[1] != -1) return left;

        int[] right = distanceH(node.right, p, q);
        if (right[0] != -1 && right[1] != -1) return right;

        int d1 = Math.max(left[0], right[0]);
        int d2 = Math.max(left[1], right[1]);

        return new int[] {
            node == p ? 0 : d1 != -1 ? d1 + 1 : -1,
            node == q ? 0 : d2 != -1 ? d2 + 1 : -1
        };
    }


    // 863. All Nodes Distance K in Binary Tree
    // https://leetcode.com/problems/all-nodes-distance-k-in-binary-tree/
    // Solution:
    //  - dfs to establish parent path
    //  - use 'seen' HashSet to avoid going same path via parent
    //  - bfs from target to left/right/parent, adding null and increasing distance when 'layer' is done (when peek is null from prev.layer)
    //  - return bfs queue content except top null entry
    public static List<Integer> distanceK(TreeNode root, TreeNode target, int K) {
        Map<TreeNode, TreeNode> parentMap = new HashMap<>();
        distanceKDFSParent(root, null, target, parentMap);

        Queue<TreeNode> q = new LinkedList<>();
        Set<TreeNode> seen = new HashSet<>();
        q.add(target);
        q.add(null);
        seen.add(target);
        seen.add(null);

        while (!q.isEmpty() && K > 0) {
            TreeNode top = q.poll();

            if (top == null) continue;

            TreeNode p = parentMap.get(top);
            if (!seen.contains(p)) q.add(p);

            TreeNode l = top.left;
            if (!seen.contains(l)) q.add(l);

            TreeNode r = top.right;
            if (!seen.contains(r)) q.add(r);

            if (q.peek() == null) {
                K--;
                q.add(null);
            }
        }

        List<Integer> result = new ArrayList<>();
        while (!q.isEmpty()) {
            TreeNode top = q.poll();
            if (top != null) result.add(top.key);
        }
        return result;
    }

    private static void distanceKDFSParent(TreeNode node, TreeNode parent, TreeNode target, Map<TreeNode, TreeNode> map) {
        if (node == null) {
            return;
        }
        map.put(node, parent);
        if (node == target) return;
        distanceKDFSParent(node.left, node, target, map);
        distanceKDFSParent(node.right, node, target, map);
    }


    // another approach for above - dfs to find parent, then dfs from target down, and same for each predecessor
//    public static List<Integer> distanсeK(TreeNode root, TreeNode target, int K) {
//        Map<TreeNode, TreeNode> parentMap = new HashMap<>();
//        dfsParent(root, target, parentMap);
//        List<Integer> result = new ArrayList<>();
//        dfsK(target, null, K, result);
//        TreeNode parent = parentMap.get(target);
//        TreeNode guard = target;
//        while (parent != null && K > 0) {
//            dfsK(parent, guard, --K, result);
//            guard = parent;
//            parent = parentMap.get(parent);
//        }
//        return result;
//    }
//    private static void dfsParent(TreeNode root, TreeNode target, Map<TreeNode, TreeNode> parentMap) {
//        if (root == target) return;
//        if (root.left != null) {
//            parentMap.put(root.left, root);
//            dfsParent(root.left, target, parentMap);
//        }
//        if (root.right != null) {
//            parentMap.put(root.right, root);
//            dfsParent(root.right, target, parentMap);
//        }
//    }
//
//    private static void dfsK(TreeNode root, TreeNode guard, int distance, List<Integer> result) {
//        if (distance < 0 || root == null) return;
//        if (distance == 0) {
//            result.add(root.key);
//            return;
//        }
//        if (root.left != null && root.left != guard) dfsK(root.left, null, distance - 1, result);
//        if (root.right != null && root.right != guard) dfsK(root.right, null, distance - 1, result);
//    }

    // Aziz 10.15 pg169
    // 545. Boundary of Binary Tree
    // https://leetcode.com/problems/boundary-of-binary-tree/description/
    // [selected - 2]
    public static List<Integer> exteriorBinaryTree(TreeNode root) {
        List<Integer> result = new ArrayList<>();
        if (root == null) return result;
        result.add(root.key);
        exteriorBinaryTreeLeft(root.left, true, result);
        exteriorBinaryTreeRight(root.right, true, result);
        return result;
    }

    private static void exteriorBinaryTreeLeft(TreeNode node, boolean isBoundary, List<Integer> result) {
        if (node == null) return;

        if (isBoundary || node.left == null && node.right == null) result.add(node.key);
        exteriorBinaryTreeLeft(node.left, isBoundary, result);
        exteriorBinaryTreeLeft(node.right, isBoundary && node.left == null, result);
    }

    private static void exteriorBinaryTreeRight(TreeNode node, boolean isBoundary, List<Integer> result) {
        if (node == null) return;

        exteriorBinaryTreeRight(node.left, isBoundary && node.right == null, result);
        exteriorBinaryTreeRight(node.right, isBoundary, result);
        if (isBoundary || node.left == null && node.right == null) result.add(node.key);
    }


    private static boolean isLeaf(TreeNode node) {
        return node != null && node.left == null && node.right == null;
    }

    // nodes distance (search tree)
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


    // https://leetcode.com/problems/find-distance-in-a-binary-tree/description/
    public static int distanceBST(TreeNode root, int p, int q) {
        if (p == q) return 0;
        int left = Math.min(p, q);
        int right = Math.max(p, q);
        while (left < root.key && right < root.key) {
            root = root.left;
        }
        while (left > root.key && right > root.key) {
            root = root.right;
        }
        return distanceBST(root, left) + distanceBST(root, right);
    }

    private static int distanceBST(TreeNode root, int v) {
        if (root.key == v) return 0;
        if (v < root.key) return distanceBST(root.left, v) + 1;
        return distanceBST(root.right, v) + 1;
    }

    // 236. Lowest Common Ancestor of a Binary Tree
    // https://leetcode.com/problems/lowest-common-ancestor-of-a-binary-tree/description
    public static TreeNode findLCA(TreeNode root, TreeNode p, TreeNode q) {
        sLca = null;
        findLcaDfs(root, p, q);
        return sLca;
    }

    private static TreeNode sLca;

    private static int findLcaDfs(TreeNode root, TreeNode p, TreeNode q) {
        if (root == null) return 0;

        int lCount = findLcaDfs(root.left, p, q);
        if (lCount == 2) return 2;

        int rCount = findLcaDfs(root.right, p, q);
        if (rCount == 2) return 2;

        int count = (root == p || root == q ? 1 : 0) + lCount + rCount;
        if (count == 2) sLca = root;
        return count;
    }


    // not clean in terms of true/false returns as left or right traverse could find two childs
//    private static boolean traverse(TreeNode root, TreeNode p, TreeNode q) {
//        if (root == null || sLca != null) {
//            return false;
//        }
//
//        int left = traverse(root.left, p, q) ? 1 : 0;
//        int right = traverse(root.right, p, q) ? 1 : 0;
//        int mid = root == p || root == q ? 1 : 0;
//
//        if (left + right + mid == 2) {
//            sLca = root;
//        }
//
//        return left + right + mid > 0;
//    }

    // 1123. Lowest Common Ancestor of Deepest Leaves
    // https://leetcode.com/problems/lowest-common-ancestor-of-deepest-leaves/
    public static TreeNode lcaDeepestLeaves(TreeNode root) {
        return lcaDeepestLeavesDFS(root, 0).getValue();
    }

    private static Pair<Integer, TreeNode> lcaDeepestLeavesDFS(TreeNode root, int level) {
        if (root == null) return new Pair<>(level, root);

        var left = lcaDeepestLeavesDFS(root.left, level + 1);
        var right = lcaDeepestLeavesDFS(root.right, level + 1);
        if (left.getKey() > right.getKey()) return left;
        if (right.getKey() > left.getKey()) return right;
        return new Pair<>(left.getKey(), root);
    }


    // 987. Vertical Order Traversal of a Binary Tree
    // https://leetcode.com/problems/vertical-order-traversal-of-a-binary-tree/ (hard) [tree, bfs]
    public static List<List<Integer>> verticalOrderHard(TreeNode root) {
        // column -> (row, value)
        TreeMap<Integer, List<Pair<Integer, Integer>>> columns = new TreeMap<>();

        // column, node
        Queue<Pair<Integer, TreeNode>> q = new LinkedList<>();
        q.add(new Pair(0, root));
        q.add(null);
        int row = 0;

        while (!q.isEmpty()) {
            Pair<Integer, TreeNode> top = q.poll();

            if (top == null) continue;

            int column = top.getKey();
            TreeNode node = top.getValue();

            List<Pair<Integer, Integer>> col = columns.computeIfAbsent(top.getKey(), k -> new ArrayList<>());

            col.add(new Pair<>(row, node.key));
            if (node.left != null) q.add(new Pair(column - 1, node.left));
            if (node.right != null) q.add(new Pair(column + 1, node.right));

            if (q.peek() == null) {
                row++;
                q.add(null);
            }
        }

        List<List<Integer>> result = new ArrayList<>();
        for (var col : columns.values()) {
            Collections.sort(col, (a, b) -> a.getKey() != b.getKey()
                ? a.getKey() - b.getKey()
                : a.getValue() - b.getValue());
            result.add(col.stream().map(p -> p.getValue()).collect(Collectors.toList()));
        }
        return result;
    }

    // 314. Binary Tree Vertical Order
    // https://leetcode.com/problems/binary-tree-vertical-order-traversal/ (medium) [tree, bfs]
    public static List<List<Integer>> verticalOrderMed(TreeNode root) {
        TreeMap<Integer, List<Integer>> columns = new TreeMap<>();
        Queue<Pair<Integer, TreeNode>> q = new LinkedList<>();
        q.add(new Pair(0, root));
        while (!q.isEmpty()) {
            Pair<Integer, TreeNode> top = q.poll();
            int i = top.getKey();
            TreeNode node = top.getValue();
            List<Integer> column = columns.computeIfAbsent(i, key -> new ArrayList<>());
            column.add(node.key);
            if (node.left != null) q.add(new Pair(i - 1, node.left));
            if (node.right != null) q.add(new Pair(i + 1, node.right));
        }
        List<List<Integer>> result = new ArrayList<>();
        for (List<Integer> c : columns.values()) {
            result.add(c);
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

    // 968. Binary Tree Cameras
    // https://leetcode.com/problems/binary-tree-cameras/
    public static int minCameraCover(TreeNode root) {
//        Set<TreeNode> cameras = new HashSet<>();
        cameraCount = 0;
        Set<TreeNode> covered = new HashSet<>();
        covered.add(null);
        minCameraCoverDFS(null, root, covered);
        return covered.contains(root) ? cameraCount : cameraCount + 1;
    }
    private static int cameraCount = 0;

    private static void minCameraCoverDFS(TreeNode parent, TreeNode node, Set<TreeNode> covered) {
        if (node == null) return;

        minCameraCoverDFS(node, node.left, covered);
        minCameraCoverDFS(node, node.right, covered);

        if (!covered.contains(node.left) || !covered.contains(node.right)) {
            cameraCount++;
            covered.add(node);
            covered.add(node.left);
            covered.add(node.right);
            covered.add(parent);
        }
    }

    // 199. Binary Tree Right Side View
    // https://leetcode.com/problems/binary-tree-right-side-view/description/
    public static List<Integer> rightSideView(TreeNode root) {
        if (root == null) return List.of();
        List<Integer> result = new ArrayList<>();
        Queue<TreeNode> q = new LinkedList<>();
        q.add(root);
        q.add(null);
        while (!q.isEmpty()) {
            TreeNode top = q.poll();
            if (top == null) continue;
            if (top.left != null) q.offer(top.left);
            if (top.right != null) q.offer(top.right);
            if (q.peek() == null) {
                result.add(top.key);
                q.offer(null);
            }
        }
        return result;
    }

    // 129. Sum Root to Leaf Numbers
    // https://leetcode.com/problems/sum-root-to-leaf-numbers
    public static int sumNumbers(TreeNode root) {
        return sumNumbers(root, 0);
    }

    private static int sumNumbers(TreeNode root, int current) {
        int value = 10 * current + root.key;

        if (root.left == null && root.right == null) return value;

        int left = root.left == null ? 0 : sumNumbers(root.left, value);
        int right = root.right == null ? 0 : sumNumbers(root.right, value);
        return left + right;
    }

    // 297. Serialize and Deserialize Binary Tree
    // https://leetcode.com/problems/serialize-and-deserialize-binary-tree
    public static String serialize(TreeNode root) {
        if (root == null) return "";

        List<String> tree = new ArrayList<>();
        Queue<TreeNode> q = new LinkedList<>();
        q.add(root);

        while (!q.isEmpty()) {
            TreeNode top = q.poll();

            if (top == null) {
                tree.add("");
                continue;
            }
            tree.add(String.valueOf(top.key));

            q.add(top.left);
            q.add(top.right);
        }

        return String.join(",", tree);
    }

    // Decodes your encoded data to tree.
    public static TreeNode deserialize(String data) {
        if (data.length() == 0) return null;

        List<String> tree = Arrays.asList(data.split(","));

        TreeNode root = new TreeNode(Integer.valueOf(tree.get(0)));
        Queue<TreeNode> q = new LinkedList<>();
        q.add(root);
        int offset = 1;

        while (!q.isEmpty() && offset < tree.size()) {
            TreeNode top = q.poll();

            String leftString = tree.get(offset++);
            String rightString = tree.get(offset++);

            if (!leftString.isEmpty()) {
                top.left = new TreeNode(Integer.valueOf(leftString));
                q.add(top.left);
            }
            if (!rightString.isEmpty()) {
                top.right = new TreeNode(Integer.valueOf(rightString));
                q.add(top.right);
            }
        }

        return root;
    }

    public static boolean equal(TreeNode t1, TreeNode t2) {
        if (t1 == null && t2 == null) return true;
        if (t1 == null || t2 == null) return false;

        if (t1.key != t2.key) return false;

        return equal(t1.left, t2. left) && equal(t1.right, t2.right);
    }
}
