package com.dtchesno.kata.test;

import com.dtchesno.kata.sort.HeapSort;
import com.dtchesno.kata.sort.MergeSort;
import com.dtchesno.kata.sort.QuickSort;
import com.dtchesno.kata.sort.Tasks;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;

import static org.junit.Assert.assertEquals;

public class SortTest {

    @Before
    public void setUp() throws Exception {
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void testMergeSort() {
        int size = 10;
        int[] probe = new int[size];
        int[] buf = new int[size];

        for (int i = 0; i < size; i++) {
            int val = (int) (Math.random() * 100);
            buf[i] = val;
            probe[i] = val;
        }

        Arrays.sort(probe);
        MergeSort.sort(buf);
        for (int i = 0; i < size; i++) {
            assertEquals(buf[i], probe[i]);
        }
    }

    @Test
    public void testQuickSort() {
        int size = 10;
        int[] probe = new int[size];
        int[] buf = new int[size];

        for (int i = 0; i < size; i++) {
            int val = (int) (Math.random() * 100);
            buf[i] = val;
            probe[i] = val;
        }

        Arrays.sort(probe);
        QuickSort.sort(buf);
        for (int i = 0; i < size; i++) {
            assertEquals(buf[i], probe[i]);
        }
    }

    @Test
    public void testHeapSort() {
        int size = 10;
        int[] probe = new int[size];
        int[] buf = new int[size];

        for (int i = 0; i < size; i++) {
            int val = (int) (Math.random() * 100);
            buf[i] = val;
            probe[i] = val;
        }

        Arrays.sort(probe);
        HeapSort.sort(buf);
        for (int i = 0; i < size; i++) {
            assertEquals(buf[i], probe[i]);
        }
    }

    @Test
    public void testKthLargest() {
        assertEquals(9, Tasks.findKthLargest(new int[] { 6, 2, 8, 9, 5, 1, 7, 4, 3 }, 1));
        assertEquals(8, Tasks.findKthLargest(new int[] { 6, 2, 8, 9, 5, 1, 7, 4, 3 }, 2));
        assertEquals(7, Tasks.findKthLargest(new int[] { 6, 2, 8, 9, 5, 1, 7, 4, 3 }, 3));
        assertEquals(6, Tasks.findKthLargest(new int[] { 6, 2, 8, 9, 5, 1, 7, 4, 3 }, 4));
        assertEquals(1, Tasks.findKthLargest(new int[] { 6, 2, 8, 9, 5, 1, 7, 4, 3 }, 9));
    }
}
