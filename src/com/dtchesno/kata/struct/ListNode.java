package com.dtchesno.kata.struct;

public class ListNode {
    public ListNode next = null;
    public ListNode prev = null;
    public int value;

    public ListNode(int value) {
        this.value = value;
    }

    public ListNode(int[] values, boolean isDoubleLinked) {
        value = values[0];
        ListNode prev = this;
        for (int i = 1; i < values.length; i++) {
            ListNode n = new ListNode(values[i]);
            prev.next = n;
            if (isDoubleLinked) {
                n.prev = prev;
            }
            prev = n;
        }
    }

    public ListNode get(int n) {
        if (n == 0) {
            return this;
        }
        return this.next == null ? null : this.next.get(n - 1);
    }

    // 50, byte-by-byte, #41 split a linked list
    public static ListNode split(ListNode node) {
        ListNode runner = node.next;
        while (runner != null && runner.next != null) {
            node = node.next;
            runner = runner.next.next;
        }
        ListNode root2 = node.next;
        root2.prev = null;
        node.next = null;
        return root2;
    }

    // cracking the coding interview 2.5 find begin of circle
    public static ListNode findStartLoop(ListNode root) {
        ListNode ptr = root.next;
        ListNode runner = root.next.next;

        while (ptr != runner) {
            ptr = ptr.next;
            runner = runner.next.next;
        }
        ptr = root;
        while (ptr != runner) {
            ptr = ptr.next;
            runner = runner.next;
        }
        return runner;
    }
}
