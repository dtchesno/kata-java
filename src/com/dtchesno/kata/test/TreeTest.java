package com.dtchesno.kata.test;

import com.dtchesno.kata.struct.TreeNode;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.*;

import static org.junit.Assert.assertEquals;

//            100
//          /       \
//    50              150
//  /   \           /      \
//20      70      120     180
public class TreeTest {

    @Before
    public void setUp() throws Exception {
    }

    @After
    public void tearDown() throws Exception {
    }

    private TreeNode createTestTree() {
        TreeNode root = new TreeNode(100);

        // left branch
        root.insert(50);
        root.insert(20);
        root.insert(70);

        // right branch
        root.insert(150);
        root.insert(120);
        root.insert(180);

        return root;
    }

    @Test
    public void testInsert() {
        TreeNode root = createTestTree();
        assertEquals(root.left.left.key, 20);
        assertEquals(root.left.right.key, 70);
        assertEquals(root.left.key, 50);
        assertEquals(root.right.left.key,120);
        assertEquals(root.right.right.key, 180);
        assertEquals(root.right.key,150);
    }

    @Test
    public void testMinDepth() {
        TreeNode tree = createTestTree();
        assertEquals(tree.minDepth(tree), 3);
    }

    @Test
    public void testMaxDepth() {
        TreeNode tree = createTestTree();
        assertEquals(tree.maxDepth(tree), 3);
    }

    @Test
    public void testIsBalanced() {
        TreeNode tree = createTestTree();
        assertEquals(tree.isBalanced(tree), true);

        tree.insert(75);
        assertEquals(TreeNode.isBalanced(tree), true);
        tree.insert(77);
        assertEquals(TreeNode.isBalanced(tree), false);
    }

    @Test
    public void inOrder() {
        List<Integer> expected = new ArrayList();
        expected.add(20);
        expected.add(50);
        expected.add(70);
        expected.add(100);
        expected.add(120);
        expected.add(150);
        expected.add(180);

        TreeNode tree = createTestTree();
        ArrayList<Integer> res =  new ArrayList();
        TreeNode.inOrder(tree, res);
        assertEquals(expected, res);

        res.clear();
        TreeNode.inOrderI(tree, res);
        assertEquals(expected, res);
    }

    @Test
    public void preOrder() {
        List<Integer> expected = new ArrayList();
        expected.add(100);
        expected.add(50);
        expected.add(20);
        expected.add(70);
        expected.add(150);
        expected.add(120);
        expected.add(180);

        TreeNode tree = createTestTree();
        ArrayList<Integer> res =  new ArrayList();
        TreeNode.preOrder(tree, res);
        assertEquals(expected, res);
    }

    @Test
    public void testBST() {
        int[] expected = new int[] { 20, 50, 70, 100, 120, 150, 180};
        TreeNode root = TreeNode.createBST(expected);

        assertEquals(root.key, 100);
        assertEquals(TreeNode.isBalanced(root), true);
        assertEquals(TreeNode.minDepth(root), 3);
        assertEquals(TreeNode.maxDepth(root), 3);
    }

    @Test
    public void isSubtree() {
        TreeNode t1 = createTestTree();

        assertEquals(t1.isSubtree(t1), true);
        assertEquals(t1.isSubtree(null), true);
        assertEquals(t1.isSubtree(new TreeNode(100)), false);
        assertEquals(t1.isSubtree(new TreeNode(20)), true);
        assertEquals(t1.isSubtree(new TreeNode(180)), true);

        int[] arr = new int[] { 50, 100, 150 };
        TreeNode t2 = TreeNode.createBST(arr);
        assertEquals(t1.isSubtree(t2), false);
        assertEquals(t1.isSubtree(t2.insert(20)), false);
        assertEquals(t1.isSubtree(t2.insert(70)), false);
        assertEquals(t1.isSubtree(t2.insert(180)), false);
        assertEquals(t1.isSubtree(t2.insert(120)), true);
    }
    @Test
    public void findSum() {
        TreeNode t = createTestTree();
        t.left.left.insert(-100);

        HashSet<List<Integer>> expected = new HashSet<>();
        expected.add(Arrays.asList(70));
        expected.add(Arrays.asList(50, 20));
        expected.add(Arrays.asList(100, 50, 20, -100));
        assertEquals(expected, t.findSum(70));
    }
}
