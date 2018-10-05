package com.dtchesno.kata.sort;

public class HeapSort {
    public static void sort(int[] buf) {
        int size = buf.length;
        heapify(buf, size);

        while (size > 1) {
            swap(buf, 0, size - 1);
            size--;
            bubbleDown(buf, size, 0);
        }
    }

    private static void heapify(int[] buf, int size) {
        for (int i = getParent(size - 1); i >= 0; i--) {
            bubbleDown(buf, size, i);
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
