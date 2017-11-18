package com.dtchesno.kata.sort;

public class QuickSort {
    public static void sort(int[] buf) {
        sortInt(buf, 0, buf.length - 1);
    }

    private static void sortInt(int[] buf, int start, int end) {
        if (start >= end) {
            return;
        }
        int p = partition(buf, start, end);
        sortInt(buf, start, p - 1);
        sortInt(buf, p + 1, end);
    }

    private static void swap(int[] data, int i, int j) {
        int temp = data[i];
        data[i] = data[j];
        data[j] = temp;
    }

    private static int partition(int[] data, int start, int end) {
        int pivot = data[end];
        int index = start;
        for (int i = start; i < end; i++) {
            if (data[i] <= pivot) {
                swap(data, index, i);
                index++;
            }
        }
        swap(data, index, end);
        return index;
    }
}
