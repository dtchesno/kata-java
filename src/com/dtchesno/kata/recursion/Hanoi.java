package com.dtchesno.kata.recursion;

import java.util.Stack;

// [recursion, stack]
public class Hanoi {
    private Stack<Integer> A = new Stack<>();
    private Stack<Integer> B = new Stack<>();
    private Stack<Integer> C = new Stack<>();
    private int size = 0;

    public Hanoi(int size) {
        this.size = size;
        reset();
    }

    public void reset() {
        A.clear();
        B.clear();
        C.clear();
        for (int i = 0; i < size; i++) {
            A.push(size - i);
        }
    }

    public int[] move() {
        move(size, A, C, B);
        int[] res = new int[size];
        for (int i = 0; i < size; i++) {
            res[i] = C.isEmpty() ? 0 : C.pop();
        }
        return res;
    }

    private void move(int n, Stack<Integer> source, Stack<Integer> target, Stack<Integer> aux) {
        if (n == 0) {
            return;
        }
        move(n - 1, source, aux, target);
        target.push(source.pop());
        move(n - 1, aux, target, source);
    }
}
