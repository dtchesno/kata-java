package com.dtchesno.kata.test;

import static org.junit.Assert.*;

import com.dtchesno.kata.sort.MergeSort;
import java.util.Arrays;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class MergeSortTest {

    @Before
    public void setUp() throws Exception {
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void testSort() {
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
}
