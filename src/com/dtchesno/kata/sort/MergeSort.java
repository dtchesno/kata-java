package com.dtchesno.kata.sort;

public class MergeSort {

    public static void sort(int[] buf) {
        int[] temp = new int[buf.length];
        mergesort(buf, temp, 0, buf.length - 1);
    }

    private static void mergesort(int[] buf, int[] temp, int start, int end) {
        if (start >= end) {
            return;
        }
        int mid = (start + end) / 2;
        mergesort(buf, temp, start, mid);
        mergesort(buf, temp, mid + 1, end);
        merge(buf, temp, start, mid, end);
    }

    private static void merge(int[] buf, int[] temp, int start, int mid, int end) {
        int i = start;
        int j = mid + 1;
        int k = start;
        while (i <= mid && j <= end) {
            if (buf[i] <= buf[j]) {
                temp[k++] = buf[i++];
            } else {
                temp[k++] = buf[j++];
            }
        }
        while (i <= mid) {
            temp[k++] = buf[i++];
        }
        while (j <= end) {
            temp[k++] = buf[j++];
        }
        for (int n = start; n <= end; n++) {
            buf[n] = temp[n];
        }
    }
}
