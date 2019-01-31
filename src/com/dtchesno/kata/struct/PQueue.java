package com.dtchesno.kata.struct;

import java.util.ArrayList;

public class PQueue {
    ArrayList<Integer> a = new ArrayList<>();

    public int poll() {
        int res = a.get(0);
        swap(0, a.size() - 1);
        a.remove(a.size() - 1);
        siftDown( 0);
        return res;
    }

    public void add(int element) {
        a.add(element);
        siftUp(a.size() - 1);
    }

    public int size() {
        return a.size();
    }

    private int parent(int i) {
        return (i - 1) / 2;
    }

    private int left(int i) {
        return 2 * i + 1;
    }

    private int right(int i) {
        return 2 * i + 2;
    }

    private void siftDown(int start) {
        int curr = start;
        while (curr < a.size()) {
            int swap = curr;
            int left = left(curr);
            int right = right(curr);
            if (left < a.size() && a.get(left) < a.get(swap)) {
                swap = left;
            }
            if (right < a.size() && a.get(right) < a.get(swap)) {
                swap = right;
            }
            if (curr == swap) {
                return;
            }
            swap(curr, swap);
            curr = swap;
        }
    }

    private void siftUp(int start) {
        int curr = start;
        while (curr != 0) {
            int parent = parent(curr);
            if (a.get(parent) <= a.get(curr)) {
                return;
            }
            swap(curr, parent);
            curr = parent;
        }
    }

    private void swap(int i, int j) {
        int temp = a.get(i);
        a.set(i, a.get(j));
        a.set(j, temp);
    }
}
