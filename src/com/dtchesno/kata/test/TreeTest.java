package com.dtchesno.kata.test;

import com.dtchesno.kata.struct.TreeNode;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;

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

    }

    @Test
    public void testMaxDepth() {

    }

    @Test
    public void testIsBalanced() {

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
}
