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

    // byte-by-byte, pg.32
    public static boolean hasCycle(ListNode list) {
        ListNode cur = list;
        ListNode runner = list;

        while (cur != null && runner != null) {
            cur = cur.next;
            runner = (runner.next != null) ? runner.next.next : null;
            if (cur == runner) {
                return true;
            }
        }
        return false;
    }

    // split a linked list in halves
    // byte-by-byte #41 pg31
    // [selected - 1]
    public static ListNode split(ListNode node) {
        ListNode runner = node.next;
        while (runner != null && runner.next != null) {
            node = node.next;
            runner = runner.next.next;
        }

        ListNode splitHead = node.next;
        splitHead.prev = null;
        node.next = null;
        return splitHead;
    }

    // find begin of circle
    // cracking the coding interview #2.5 pg.109
    // [selected - 1]
    // s1->(loop node)->d->(meet point), s2 - length of loop
    //      ptr will travel s1+d
    //      runner will travel s1+d+s2; we need to divide by 2 to get equal number of moves
    //      s1 + d = (s1 + d + s2) / 2 => s1 + d = s2 => s1 = s2 - d
    //      so, if ptr starts over and runner from meet point with the same speed, they should meet at start of loop
    //      runner could make multiple loop runs (k * s2), but this doesn't change things, it will do so for final step either
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

    // 0, 1, 2, 3, 4, 5 -> 0, 5, 1, 4, 2, 3 // n0, nlast, n1, nlast-1...
    public static ListNode reorder(ListNode root) {
        ListNode ptr = root.next;
        ListNode runner = root.next.next;
        while (runner != null && runner.next != null) {
            ptr = ptr.next;
            runner = runner.next.next;
        }
        ListNode p1 = root;
        ListNode p2 = ptr.next;
        ptr.next = null;
        p2 = reverse(p2);
        merge(p1, p2);
        return root;
    }

    // [selected - 2]
    public static ListNode reverse(ListNode root) {
        ListNode cur = root;
        ListNode next = root.next;
        cur.next = null;
        while (next != null) {
            ListNode prev = cur;
            cur = next;
            next = cur.next;
            cur.next = prev;
        }
        return cur;
    }

    // add numbers represented by lists; 1's digit @ head
    // e.g. (3->1->5) + (5->9->2) = (8->0->8)
    // cracking #2.4 pg.50
    // [selected - 2]
    public static ListNode add(ListNode l1, ListNode l2, int carry) {
        if (l1 == null && l2 == null && carry == 0) {
            return null;
        }

        int value = carry;

        if (l1 != null) {
            value += l1.value;
            l1 = l1.next;
        }

        if (l2 != null) {
            value += l2.value;
            l2 = l2.next;
        }

        ListNode node = new ListNode(value % 10);
        ListNode next = add(l1, l2, value / 10);
        node.next = next;
        return node;
    }

    public static void merge(ListNode l1, ListNode l2) {
        ListNode p1 = l1;
        ListNode p2 = l2;
        while (p1.next != null && p2 != null) {
            ListNode temp = p1.next;
            p1.next = p2;
            p2 = p2.next;
            p1.next.next = temp;
            p1 = temp;
        }
        if (p1.next == null) {
            p1.next = p2;
        }
    }
}
