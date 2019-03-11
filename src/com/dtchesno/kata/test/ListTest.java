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
    public void testSplit() {
        assertEquals(3, ListNode.split(new ListNode(new int[] { 1, 2, 3, 4 }, false)).value);
        assertEquals(4, ListNode.split(new ListNode(new int[] { 1, 2, 3, 4, 5 }, false)).value);
        assertEquals(4, ListNode.split(new ListNode(new int[] { 1, 2, 3, 4, 5, 6 }, false)).value);
        assertEquals(5, ListNode.split(new ListNode(new int[] { 1, 2, 3, 4, 5, 6, 7 }, false)).value);
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
}
