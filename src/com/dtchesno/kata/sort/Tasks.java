package com.dtchesno.kata.sort;

import java.util.PriorityQueue;

public class Tasks {
    public static int findKthLargestSimple(int[] arr, int k) {
        PriorityQueue<Integer> pq = new PriorityQueue<>();
        for (int i = 0; i < arr.length; i++) {
            pq.add(arr[i]);
        }
        int ilargest = arr.length - k;
        for (int i = 0; i < ilargest; i++) {
            pq.remove();
        }
        return pq.peek();
    }

    public static int findKthLargest(int[] arr, int k) {
        int p = -1;
        int i = 0;
        int j = arr.length - 1;
        while (p != k - 1) {
            p = partition(arr, i, j);
            if (p > k - 1) {
                j = p - 1;
            } else {
                i = p + 1;
            }
        }
        return arr[p];
    }

    private static int partition(int[] arr, int i, int j) {
        if (i >= j) {
            return i;
        }
        // pivot is arr[j]
        int p = i;
        for (int n = i; n < j; n++) {
            if (arr[n] >= arr[j]) {
                swap(arr, n, p);
                p++;
            }
        }
        swap(arr, j, p);
        return p;
    }

    private static void swap(int[] arr, int i, int j) {
        int temp = arr[i];
        arr[i] = arr[j];
        arr[j] = temp;
    }
}
