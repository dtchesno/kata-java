package com.dtchesno.kata.careercup;

import com.dtchesno.kata.struct.TreeNode;
import java.util.HashMap;

/**
 * FACEBOOK: https://www.careercup.com/question?id=4871620274421760
 *
 * Give a binary tree, find if it's possible to cut the tree into two halves of equal sum. You can only cut one edge.
 */
public class CutTree {

    /**
     * Test whether tree can be cut once, so, two part will have thesame sum of node values.
     *
     * @param tree input tree
     * @return true/false
     */
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
}
