package com.dtchesno.kata.test;

import com.dtchesno.kata.misc.MiscSolution;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.util.Arrays;
import java.util.Stack;

import static org.junit.Assert.*;

public class CareerCupTest {

    @Before
    public void setUp() throws Exception {
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void testDistinctStrings() {
        assertEquals(2, MiscSolution.getDistinctStrings(new String[] { "abc", "de"}));
        assertEquals(2, MiscSolution.getDistinctStrings(new String[] { "abc", "def"}));
        assertEquals(2, MiscSolution.getDistinctStrings(new String[] { "abcd", "cbad", "bacd" }));
        assertEquals(1, MiscSolution.getDistinctStrings(new String[] { "abcde", "cbade", "cdabe" }));
    }

//    mergeSortedIS
    @Test
    public void testMergeStreams() {
        ByteArrayInputStream arr1 = new ByteArrayInputStream(new byte[] { 1, 2, 5, 10, 18 });
        ByteArrayInputStream arr2 = new ByteArrayInputStream(new byte[] { 1, 3, 4, 20 });
        ByteArrayInputStream arr3 = new ByteArrayInputStream(new byte[] { 100, 110, 120 });
        ByteArrayOutputStream os = new ByteArrayOutputStream();

        MiscSolution.mergeStreams(os, new InputStream[] { arr1, arr2, arr3 });
        assertTrue(Arrays.equals(os.toByteArray(), new byte[] { 1, 1, 2, 3, 4, 5, 10, 18, 20, 100, 110, 120}));
    }

    @Test
    public void testSpiral() {
        assertTrue(Arrays.deepEquals(
                MiscSolution.spiral(3),
                new int[][] {
                        {1,2,3},
                        {8,9,4},
                        {7,6,5}
                }
        ));

        assertTrue(Arrays.deepEquals(
                MiscSolution.spiral(4),
                new int[][] {
                        {1,2,3,4},
                        {12,13,14,5},
                        {11,16,15,6},
                        {10,9,8,7}
                }
        ));
    }

    //isOneEditAway
    @Test
    public void testIsOneEditAway() {
        assertFalse(MiscSolution.isOneEditAway("cat", "cat"));
        assertFalse(MiscSolution.isOneEditAway("cat", "dog"));
        assertTrue(MiscSolution.isOneEditAway("cat", "cats"));
        assertTrue(MiscSolution.isOneEditAway("cat", "cut"));
        assertTrue(MiscSolution.isOneEditAway("cat", "cast"));
        assertTrue(MiscSolution.isOneEditAway("cat", "at"));
        assertFalse(MiscSolution.isOneEditAway("cat", "act"));
    }

    @Test
    public void testLookAndSay() {
        assertEquals("1", MiscSolution.lookAndSay(-2));
        assertEquals("1", MiscSolution.lookAndSay(0));
        assertEquals("1", MiscSolution.lookAndSay(1));
        assertEquals("11", MiscSolution.lookAndSay(2));
        assertEquals("21", MiscSolution.lookAndSay(3));
        assertEquals("1211", MiscSolution.lookAndSay(4));
        assertEquals("111221", MiscSolution.lookAndSay(5));
        assertEquals("312211", MiscSolution.lookAndSay(6));
        assertEquals("13112221", MiscSolution.lookAndSay(7));
        assertEquals("1113213211", MiscSolution.lookAndSay(8));
        assertEquals("31131211131221", MiscSolution.lookAndSay(9));
        assertEquals("13211311123113112211", MiscSolution.lookAndSay(10));
    }

    @Test
    public void testSquareRoot() {
        double[] values = new double[] { 0, 0.1, 0.55, 1.0, 2.0, 3.0, 1111.123 };
        double epsilon = 0.001;
        for (double val: values) {
            double root = MiscSolution.squareRoot(val, epsilon);
            double tested = root * root;
            assertTrue((tested < val + epsilon) && (tested > val - epsilon));
        }
    }
}
