package com.dtchesno.kata.test;

import com.dtchesno.kata.struct.ListNode;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class ListTest {

    @Before
    public void setUp() throws Exception {
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void testHasCycle() {
        ListNode l = new ListNode(new int[] { 1, 2, 3, 4, 5 }, false);
        assertFalse(ListNode.hasCycle(l));

        // 5->2
        l.next.next.next.next.next = l.next;
        assertTrue(ListNode.hasCycle(l));
    }

    @Test
    public void testSplit() {
        ListNode l = new ListNode(new int[] { 1, 2, 3, 4 }, false);
        assertEquals(3, ListNode.split(l).value);
        assertEquals(null, l.get(2));

        l = new ListNode(new int[] { 1, 2, 3, 4, 5 }, false);
        assertEquals(4, ListNode.split(l).value);
        assertEquals(null, l.get(3));

        l = new ListNode(new int[] { 1, 2, 3, 4, 5, 6 }, false);
        assertEquals(4, ListNode.split(l).value);
        assertEquals(null, l.get(3));

        l = new ListNode(new int[] { 1, 2, 3, 4, 5, 6, 7 }, false);
        assertEquals(5, ListNode.split(l).value);
        assertEquals(null, l.get(4));
    }

    @Test
    public void testFindStartLoop() {
        ListNode root = new ListNode(new int[] {1, 2, 3, 4, 5, 6, 7}, false);
        ListNode last = root.get(6);

        last.next = root.get(0); // 1
        assertEquals(last.next, ListNode.findStartLoop(root));

        last.next = root.get(1); // 2
        assertEquals(last.next, ListNode.findStartLoop(root));

        last.next = root.get(2); // 3
        assertEquals(last.next, ListNode.findStartLoop(root));

        last.next = root.get(3); // 4
        assertEquals(last.next, ListNode.findStartLoop(root));

        last.next = last;
        assertEquals(last.next, ListNode.findStartLoop(root));
    }

    @Test
    public void testMerge() {
        ListNode root = new ListNode(new int[] {1, 3, 5, 7}, false);
        ListNode merge = new ListNode(new int[] {2, 4, 6}, false);
        ListNode.merge(root, merge);
        assertEquals(1, root.get(0).value);
        assertEquals(2, root.get(1).value);
        assertEquals(3, root.get(2).value);
        assertEquals(4, root.get(3).value);
        assertEquals(5, root.get(4).value);
        assertEquals(6, root.get(5).value);
        assertEquals(7, root.get(6).value);

        root = new ListNode(new int[] {1, 3}, false);
        merge = new ListNode(new int[] {2, 4, 5, 6, 7}, false);
        ListNode.merge(root, merge);
        assertEquals(1, root.get(0).value);
        assertEquals(2, root.get(1).value);
        assertEquals(3, root.get(2).value);
        assertEquals(4, root.get(3).value);
        assertEquals(5, root.get(4).value);
        assertEquals(6, root.get(5).value);
        assertEquals(7, root.get(6).value);
    }

    @Test
    public void testReverse() {
        ListNode root = new ListNode(new int[] {1, 2, 3, 4, 5, 6, 7}, false);
        root = ListNode.reverse(root);
        assertEquals(7, root.get(0).value);
        assertEquals(6, root.get(1).value);
        assertEquals(5, root.get(2).value);
        assertEquals(4, root.get(3).value);
        assertEquals(3, root.get(4).value);
        assertEquals(2, root.get(5).value);
        assertEquals(1, root.get(6).value);
    }

    @Test
    public void testReorder() {
        ListNode root = new ListNode(new int[] {1, 2, 3, 4, 5, 6, 7}, false);
        ListNode.reorder(root);
        assertEquals(1, root.get(0).value);
        assertEquals(7, root.get(1).value);
        assertEquals(2, root.get(2).value);
        assertEquals(6, root.get(3).value);
        assertEquals(3, root.get(4).value);
        assertEquals(5, root.get(5).value);
        assertEquals(4, root.get(6).value);
    }

    @Test
    public void testAdd() {
        ListNode l1 = new ListNode(new int[] {3, 1, 5}, false);
        ListNode l2 = new ListNode(new int[] {5, 9, 2, 1}, false);
        ListNode sum = ListNode.add(l1, l2, 0);
        assertEquals(8, sum.get(0).value);
        assertEquals(0, sum.get(1).value);
        assertEquals(8, sum.get(2).value);
        assertEquals(1, sum.get(3).value);
        assertEquals(null, sum.get(4));

        ListNode l21 = new ListNode(new int[] {9,9,9,9,9,9,9}, false);
        ListNode l22 = new ListNode(new int[] {9,9,9,9}, false);
        ListNode sum2 = ListNode.add(l21, l22, 0);
        assertEquals(8, sum2.get(0).value);
        assertEquals(9, sum2.get(1).value);
        assertEquals(9, sum2.get(2).value);
        assertEquals(9, sum2.get(3).value);
        assertEquals(0, sum2.get(4).value);
        assertEquals(0, sum2.get(5).value);
        assertEquals(0, sum2.get(6).value);
        assertEquals(1, sum2.get(7).value);
        assertEquals(null, sum2.get(8));

        ListNode l31 = new ListNode(new int[] {0}, false);
        ListNode l32 = new ListNode(new int[] {0}, false);
        ListNode sum3 = ListNode.add(l31, l32, 0);
        assertEquals(0, sum3.get(0).value);
        assertEquals(null, sum3.get(1));
    }
}
