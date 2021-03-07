package com.dtchesno.kata.test;

import com.dtchesno.kata.struct.ArrayStringTasks;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;

public class IvanTests {

    @Before
    public void setUp() throws Exception {
    }

    @After
    public void tearDown() throws Exception {
    }


    @Test
    public void testTest2() {
        int[] x = new int[] { 1, 2, 3, 4, 5, 6, 7};
        int n = x.length;
        int[] r = new int[n];
        for (int i= 0; i < n/2; i++) {
            r[2* i] = x[i];
            r[2*i + 1] = x[i + n / 2];
        }
        return;
    }

    @Test
    public void testTest() {
        int[][] input = new int[][] {
                { 255, 255, 255, 255 },
                { 255, 0, 128, 255 },
                { 255, 255, 255, 255 }
        };

        int h = input.length;
        int w = input[0].length;

        for (int i = 0; i<h; i++) {
            for (int j = 0; j < w; j++) {
                input[i][j] = 255 - input[i][j];
            }
        }

        System.out.println(input[1][2]);
    }
}
