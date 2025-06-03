package com.dtchesno.kata.test;

import com.dtchesno.kata.struct.EncodeNTree;
import com.dtchesno.kata.struct.TreeNode;
//import com.dtchesno.kata.careercup.CutTree;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

//import javax.xml.bind.annotation.XmlElementRef;
import java.util.*;

import static org.junit.Assert.*;

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

        res.clear();
        TreeNode.preOrderI(tree, res);
        assertEquals(expected, res);
    }

    @Test
    public void postOrder() {
        List<Integer> expected = new ArrayList();
        expected.add(20);
        expected.add(70);
        expected.add(50);
        expected.add(120);
        expected.add(180);
        expected.add(150);
        expected.add(100);

        TreeNode tree = createTestTree();
        ArrayList<Integer> res =  new ArrayList();
        TreeNode.postOrder(tree, res);
        assertEquals(expected, res);

        res.clear();
        TreeNode.postOrderI(tree, res);
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

        assertTrue(t1.isSubtree(t1));
        assertTrue(t1.isSubtree(null));
        assertFalse(t1.isSubtree(new TreeNode(100)));
        assertTrue(t1.isSubtree(new TreeNode(20)));
        assertTrue(t1.isSubtree(new TreeNode(180)));

        int[] arr = new int[] { 50, 100, 150 };
        TreeNode t2 = TreeNode.createBST(arr);
        assertFalse(t1.isSubtree(t2));
        assertFalse(t1.isSubtree(t2.insert(20)));
        assertFalse(t1.isSubtree(t2.insert(70)));
        assertFalse(t1.isSubtree(t2.insert(180)));
        assertTrue(t1.isSubtree(t2.insert(120)));
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

    @Test
    public void isHalfCuttable() {
        TreeNode root = new TreeNode(2);
        assertFalse(TreeNode.isHalfCuttable(root));

        root.insert(1);
        root.insert(3);
        assertTrue(TreeNode.isHalfCuttable(root));

        root.insert(6);
        assertTrue(TreeNode.isHalfCuttable(root));

        root.insert(1);
        assertFalse(TreeNode.isHalfCuttable(root));
    }

    @Test
    public void testToLinkedList() {
        TreeNode t = createTestTree();

        // 20-50-70-100-120-150-180
        TreeNode r = TreeNode.toLinkedList(t);
        assertEquals(20, r.key);
        r = r.right;
        assertEquals(50, r.key);
        r = r.right;
        assertEquals(70, r.key);
        r = r.right;
        assertEquals(100, r.key);
        r = r.right;
        assertEquals(120, r.key);
        r = r.right;
        assertEquals(150, r.key);
        r = r.right;
        assertEquals(180, r.key);
        r = r.right;
        assertEquals(20, r.key);
        r = r.left;
        assertEquals(180, r.key);
        r = r.left;
        assertEquals(150, r.key);
        r = r.left;
        assertEquals(120, r.key);
        r = r.left;
        assertEquals(100, r.key);
        r = r.left;
        assertEquals(70, r.key);
        r = r.left;
        assertEquals(50, r.key);
        r = r.left;
        assertEquals(20, r.key);
        r = r.left;
        assertEquals(180, r.key);
    }

    @Test
    public void testLongestConsecutiveBranch() {
        assertEquals(3, TreeNode.longestConsecutiveBranch(new TreeNode(new Integer[] { 0, 1, 2, 1, 2, 1, 3 })));
        assertEquals(4, TreeNode.longestConsecutiveBranch(new TreeNode(
                new Integer[] { 0, 1, 2, 2, 2, 1, 3, 4, 4, 3, 4, 4, 4, 4, 4})));
    }

    @Test
    public void testFindKthLargestElements() {
        TreeNode t = createTestTree();
        assertTrue(Arrays.equals(new int[] { 20, 50, 70, 100, 120, 150, 180 }, TreeNode.findKthLargestElements(t, 7)));
        assertTrue(Arrays.equals(new int[] { 50, 70, 100, 120, 150, 180 }, TreeNode.findKthLargestElements(t, 6)));
        assertTrue(Arrays.equals(new int[] { 70, 100, 120, 150, 180 }, TreeNode.findKthLargestElements(t, 5)));
        assertTrue(Arrays.equals(new int[] { 100, 120, 150, 180 }, TreeNode.findKthLargestElements(t, 4)));
        assertTrue(Arrays.equals(new int[] { 120, 150, 180 }, TreeNode.findKthLargestElements(t, 3)));
        assertTrue(Arrays.equals(new int[] { 150, 180 }, TreeNode.findKthLargestElements(t, 2)));
        assertTrue(Arrays.equals(new int[] { 180 }, TreeNode.findKthLargestElements(t, 1)));
    }

    @Test
    public void testDistanceK() {
        TreeNode t = new TreeNode(new Integer[] { 3, 5, 1, 6, 2, 0, 8, null, null, 7, 4 });
        assertEquals(
            new HashSet(Arrays.asList(7, 4, 1)),
            new HashSet(TreeNode.distanceK(t, t.left, 2))
        );
    }

    @Test
    public void testDistance() {
        TreeNode t = new TreeNode(new Integer[] { 3, 5, 1, 6, 2, 0, 8, null, null, 7, 4 });
        assertEquals(3, TreeNode.distance(t, t.left.left, t.left.right.right));
        assertEquals(2, TreeNode.distance(t, t.left.left, t));
        assertEquals(1, TreeNode.distance(t, t.left.left, t.left));
        assertEquals(4, TreeNode.distance(t, t.left.left, t.right.left));
    }

    @Test
    public void testExteriorBinaryTree() {
        TreeNode t = new TreeNode(new Integer[] { 3, 5, 1, 1, 6, 0, 8, null, null, 7, 4 });
        assertEquals(Arrays.asList(3, 5, 1, 7, 4, 0, 8, 1), TreeNode.exteriorBinaryTree(t));

        TreeNode t2 = new TreeNode(new Integer[] {1,null,2,3,4});
        assertEquals(Arrays.asList(1,3,4,2), TreeNode.exteriorBinaryTree(t2));

        TreeNode t3 = new TreeNode(new Integer[] { 1, 2, 3, 4, 5, null, null, null, null, 6, 10, 7, 8});
        assertEquals(Arrays.asList(1, 2, 4, 7, 8, 10, 3), TreeNode.exteriorBinaryTree(t3));
    }

    @Test
    public void testDistanceInBST() {
        TreeNode t = new TreeNode(new Integer[] { 5, 3, 6, 2, 4, null, 7, 1, null, null, null, null, 8 });
        assertEquals(3, TreeNode.distanceBST(t, t.left.left.left, t.left.right));
        assertEquals(6, TreeNode.distanceBST(t, t.left.left.left, t.right.right.right));
    }

    @Test
    public void testDistanceInBST2() {
        TreeNode t = new TreeNode(new Integer[] { 5, 3, 6, 2, 4, null, 7, 1, null, null, null, null, 8 });
        assertEquals(3, TreeNode.distanceBST(t, 1, 4));
        assertEquals(6, TreeNode.distanceBST(t, 1, 8));
    }

    @Test
    public void testFindLCA() {
        TreeNode root = new TreeNode(new Integer[] { 3,5,1,6,2,0,8,null,null,7,4 });
        assertEquals(3, TreeNode.findLCA(root, root.left, root.right).key);
        assertEquals(5, TreeNode.findLCA(root.left, root.left, root.left.right.right).key);
        TreeNode root2 = new TreeNode(new Integer[] { 1, 2 });
        assertEquals(1, TreeNode.findLCA(root2, root2, root2.left).key);
    }

    @Test
    public void testLcaDeepestLeaves() {
        TreeNode root = new TreeNode(new Integer[] { 3,5,1,6,2,0,8,null,null,7,4 });
        assertEquals(2, root.lcaDeepestLeaves(root).key);

        TreeNode root2 = new TreeNode(new Integer[] { 0,1,3,null,2 });
        assertEquals(2, root.lcaDeepestLeaves(root2).key);

        TreeNode root3 = new TreeNode(new Integer[] { 1 });
        assertEquals(1, root.lcaDeepestLeaves(root3).key);

        TreeNode root4 = new TreeNode(new Integer[] { 1, 2, null });
        assertEquals(2, root.lcaDeepestLeaves(root4).key);

        TreeNode root5 = new TreeNode(new Integer[] { 1, 2, 3, 4, 5, 6, null });
        assertEquals(1, root.lcaDeepestLeaves(root5).key);
    }

    @Test
    public void testMinCameraCover() {
        // TODO: problem with tree construction below
        assertEquals(1, TreeNode.minCameraCover(new TreeNode(new Integer[] { 8,4,null,2,6 })));
        assertEquals(2, TreeNode.minCameraCover(new TreeNode(new Integer[] { 8,4,null,3,null,1,null,null,2 })));
        assertEquals(2, TreeNode.minCameraCover(new TreeNode(new Integer[] { 0,0,null,null,0,0,null,null,0,0 })));
        assertEquals(2, TreeNode.minCameraCover(new TreeNode(new Integer[] { 0,null,0,null,0,null,0 })));
        assertEquals(2, TreeNode.minCameraCover(new TreeNode(new Integer[] { 0,0,0,null,null,null,0 })));
        assertEquals(1, TreeNode.minCameraCover(new TreeNode(new Integer[] { 0,0,null,0,0 })));
    }

    @Test
    public void testVerticalTraversal() {
    }

    @Test
    public void testNTreeSerialize() {
        EncodeNTree.Node root =
            new EncodeNTree.Node(1, Arrays.asList(
                    new EncodeNTree.Node(3, Arrays.asList(
                            new EncodeNTree.Node(5),
                            new EncodeNTree.Node(6)
                    )),
                    new EncodeNTree.Node(2),
                    new EncodeNTree.Node(4)
            ));
        Assert.assertEquals(
                Arrays.asList(1,null,3,2,4,null,5,6),
                root.serialize(root));
    }

    @Test
    public void testVerticalOrderMed() {
        TreeNode root = TreeNode.buildBinaryTree(new Integer[] {1,2,3,4,6,5,7});
        Assert.assertEquals(
                Arrays.asList(
                        Arrays.asList(4),
                        Arrays.asList(2),
                        Arrays.asList(1,6,5),
                        Arrays.asList(3),
                        Arrays.asList(7)
                ),
                TreeNode.verticalOrderMed(root));
    }

    @Test
    public void testVerticalOrderHard() {
        TreeNode root = TreeNode.buildBinaryTree(new Integer[] {1,2,3,4,6,5,7});
        Assert.assertEquals(
                Arrays.asList(
                        Arrays.asList(4),
                        Arrays.asList(2),
                        Arrays.asList(1,5,6),
                        Arrays.asList(3),
                        Arrays.asList(7)
                ),
                TreeNode.verticalOrderHard(root));
    }

    @Test
    public void testRightSideView() {
        Assert.assertEquals(
            List.of(1, 3, 4),
            TreeNode.rightSideView(TreeNode.buildBinaryTree(new Integer[] {1,2,3,null,5,null,4})));
        Assert.assertEquals(
                List.of(1, 3),
                TreeNode.rightSideView(TreeNode.buildBinaryTree(new Integer[] {1,null,3})));
        Assert.assertEquals(List.of(), TreeNode.rightSideView(null));
    }

    @Test
    public void testSumNumbers() {
        Assert.assertEquals(10, TreeNode.sumNumbers(TreeNode.buildBinaryTree(new Integer[] {1,0})));
        Assert.assertEquals(25, TreeNode.sumNumbers(TreeNode.buildBinaryTree(new Integer[] {1,2,3})));
        Assert.assertEquals(1026, TreeNode.sumNumbers(TreeNode.buildBinaryTree(new Integer[] {4,9,0,5,1})));
    }

    @Test
    public void testSerialize() {
        Assert.assertEquals(null, TreeNode.deserialize(TreeNode.serialize(null)));
        var tree = TreeNode.buildBinaryTree(new Integer[] {1,2,3,null,null,4,5});
        Assert.assertTrue(TreeNode.equal(tree, TreeNode.deserialize(TreeNode.serialize(tree))));
    }
}
