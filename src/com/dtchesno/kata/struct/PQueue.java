package com.dtchesno.kata.struct;

import java.util.ArrayList;

public class PQueue<T> {
    public static interface Comparator<T> {
        Integer compare(T t1, T t2);
    }

    private Comparator<T> mComparator;
    private ArrayList<T> a = new ArrayList<>();

    public PQueue(Comparator<T> c) {
        mComparator = c;
    }

    public T poll() {
        T res = a.get(0);
        swap(0, a.size() - 1);
        a.remove(a.size() - 1);
        siftDown( 0);
        return res;
    }

    public void add(T element) {
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
            //if (left < a.size() && a.get(left) < a.get(swap)) {
            if (left < a.size() && mComparator.compare(a.get(left), a.get(swap)) < 0) {
                swap = left;
            }
            //if (right < a.size() && a.get(right) < a.get(swap)) {
            if (right < a.size() && mComparator.compare(a.get(right), a.get(swap)) < 0) {
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
            //if (a.get(parent) <= a.get(curr)) {
            if (mComparator.compare(a.get(parent), a.get(curr)) <= 0) {
                return;
            }
            swap(curr, parent);
            curr = parent;
        }
    }

    private void swap(int i, int j) {
        T temp = a.get(i);
        a.set(i, a.get(j));
        a.set(j, temp);
    }
}
