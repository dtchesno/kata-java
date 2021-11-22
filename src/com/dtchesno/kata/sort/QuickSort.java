package com.dtchesno.kata.sort;

public class QuickSort {
    public static void sort(int[] buf) {
        sort(buf, 0, buf.length - 1);
    }

    private static void sort(int[] buf, int start, int end) {
        if (start >= end) {
            return;
        }
        int p = partition(buf, start, end);
        sort(buf, start, p - 1);
        sort(buf, p + 1, end);
    }

    private static void swap(int[] data, int i, int j) {
        int temp = data[i];
        data[i] = data[j];
        data[j] = temp;
    }

    // partition data[] and return index, so that, all in [start, index) are less than [index, end]
    private static int partition(int[] data, int start, int end) {
        int pivot = data[end];
        int p = start;
        for (int i = start; i < end; i++) {
            if (data[i] <= pivot) {
                swap(data, p, i);
                p++;
            }
        }
        swap(data, p, end);
        return p;
    }
}
