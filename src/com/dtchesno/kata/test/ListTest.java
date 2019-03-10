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
}
