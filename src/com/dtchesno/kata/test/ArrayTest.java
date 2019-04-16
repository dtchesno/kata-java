package com.dtchesno.kata.test;

import com.dtchesno.kata.struct.ArrayStringTasks;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import java.util.Arrays;
import static org.junit.Assert.*;

public class ArrayTest {

    @Before
    public void setUp() throws Exception {
    }

    @After
    public void tearDown() throws Exception {
    }


    @Test
    public void testRotate() {
        assertTrue(Arrays.equals(new int[] { 4, 5, 6, 7, 1, 2, 3 }, ArrayStringTasks.rotateRight(new int[] { 1, 2, 3, 4, 5, 6, 7 }, 4)));
    }

    @Test
    public void testReverseWords() {
        assertEquals("blue is sky the", ArrayStringTasks.reverseWords("the sky is blue"));
    }

    @Test
    public void testFirstOccurrence() {
        assertEquals(0, ArrayStringTasks.firstOccurrence("abc", "abc"));
        assertEquals(-1, ArrayStringTasks.firstOccurrence("bc", "abc"));
        assertEquals(-1, ArrayStringTasks.firstOccurrence("abc", "abd"));

        assertEquals(6, ArrayStringTasks.firstOccurrence("abcabcabcdbcdfg", "abcd"));
        assertEquals(7, ArrayStringTasks.firstOccurrence("abcabcabcdbcdfg", "bcd"));
        assertEquals(13, ArrayStringTasks.firstOccurrence("abcabcabcdbcdfg", "fg"));
    }

    @Test
    public void testToRoman() {
        assertEquals("I", ArrayStringTasks.toRoman(1));
        assertEquals("II", ArrayStringTasks.toRoman(2));
        assertEquals("III", ArrayStringTasks.toRoman(3));
        assertEquals("IV", ArrayStringTasks.toRoman(4));
        assertEquals("V", ArrayStringTasks.toRoman(5));
        assertEquals("VI", ArrayStringTasks.toRoman(6));
        assertEquals("IX", ArrayStringTasks.toRoman(9));
        assertEquals("X", ArrayStringTasks.toRoman(10));
        assertEquals("XII", ArrayStringTasks.toRoman(12));
        assertEquals("XV", ArrayStringTasks.toRoman(15));
        assertEquals("XXIX", ArrayStringTasks.toRoman(29));
        assertEquals("XLVIII", ArrayStringTasks.toRoman(48));
        assertEquals("XLIX", ArrayStringTasks.toRoman(49));
    }

    @Test
    public void testfromRoman() {
        assertEquals(1, ArrayStringTasks.fromRoman("I"));
        assertEquals(2, ArrayStringTasks.fromRoman("II"));
        assertEquals(3, ArrayStringTasks.fromRoman("III"));
        assertEquals(4, ArrayStringTasks.fromRoman("IV"));
        assertEquals(5, ArrayStringTasks.fromRoman("V"));
        assertEquals(6, ArrayStringTasks.fromRoman("VI"));
        assertEquals(9, ArrayStringTasks.fromRoman("IX"));
        assertEquals(10, ArrayStringTasks.fromRoman("X"));
        assertEquals(12, ArrayStringTasks.fromRoman("XII"));
        assertEquals(15, ArrayStringTasks.fromRoman("XV"));
        assertEquals(29, ArrayStringTasks.fromRoman("XXIX"));
        assertEquals(48, ArrayStringTasks.fromRoman("XLVIII"));
        assertEquals(49, ArrayStringTasks.fromRoman("XLIX"));
    }

    @Test
    public void testFindZeroSum() {
        assertTrue(Arrays.equals(new int[] {-1, 3, -2}, ArrayStringTasks.findZeroSum(new int[] {2, -1, 3, -2, 5})));
        assertTrue(Arrays.equals(new int[0], ArrayStringTasks.findZeroSum(new int[] {2, 3, -2, 5})));
    }
}
