package com.dtchesno.kata.sort;

public class HeapSort {
    public static void sort(int[] buf) {
        int size = buf.length;
        heapify(buf);

        // at the beginning entire buf is max-heap;
        // we iteratively reduce size of heap at the beginning of buf and building sorted array at the end;
        //  - swap top of heap (heap-max) with last element of heap buffer
        //  - this prev heap-end cell  becomes head of sorted array at the end of buf[]
        //  - now restore heap property by sifting down new heap head
        //  - iterate
        while (size > 1) {
            swap(buf, 0, size - 1);
            size--;
            bubbleDown(buf, size, 0);
        }
    }

    // build max-heap
    private static void heapify(int[] buf) {
        for (int i = getParent(buf.length - 1); i >= 0; i--) {
            bubbleDown(buf, buf.length, i);
        }
    }

    private static void bubbleDown(int[] buf, int size, int i) {
        while (true) {
            int ileft = getChild(i);
            int iright = ileft + 1;

            if (ileft >= size) {
                return;
            }
            int iswap = (buf[i] >= buf[ileft]) ? i : ileft;
            if (iright < size && buf[iright] > buf[iswap]) {
                iswap = iright;
            }
            if (iswap == i) {
                return;
            }

            swap(buf, i, iswap);

            i = iswap;
        }
    }

    private static void swap(int[] buf, int i, int j) {
        int temp = buf[i];
        buf[i] = buf[j];
        buf[j] = temp;
    }

    private static int getParent(int n) {
        return n == 0 ? -1 : (n - 1) / 2;
    }

    private static int getChild(int n) { return 2 * n + 1; }
}
