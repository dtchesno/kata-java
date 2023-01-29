package com.dtchesno.kata.test;

import com.dtchesno.kata.struct.ArrayStringTasks;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import java.util.Arrays;

import static org.hamcrest.CoreMatchers.is;
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
        assertEquals("blue is sky  the", ArrayStringTasks.reverseWords("the  sky is blue"));
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
    public void testRemoveOccurrences() {
        assertEquals("dab", ArrayStringTasks.removeOccurrences("daabcbaabcbc", "abc"));
        assertEquals("", ArrayStringTasks.removeOccurrences("eemckxmckx", "emckx"));
        assertEquals("b", ArrayStringTasks.removeOccurrences("ccctltctlltlb", "ctl"));
        assertEquals("", ArrayStringTasks.removeOccurrences("aaaaaaaaaaaaa", "a"));
        assertEquals("b", ArrayStringTasks.removeOccurrences("baaaaaaaaaaaaa", "a"));
        assertEquals("b", ArrayStringTasks.removeOccurrences("aaaaaaaaaaaaab", "a"));
        assertEquals("b", ArrayStringTasks.removeOccurrences("aaaaaabaaaaaaa", "a"));
        assertEquals("bb", ArrayStringTasks.removeOccurrences("baaaaaaaaaaaab", "a"));
        assertEquals("aaaaaaaaaaaa", ArrayStringTasks.removeOccurrences("baaaaaaaaaaaab", "b"));
        assertEquals("aaaaaaaaaaaa", ArrayStringTasks.removeOccurrences("baaaaaaaaaaaab", "b"));
        assertEquals("", ArrayStringTasks.removeOccurrences("aaa", "aaa"));
        assertEquals(
                "",
                ArrayStringTasks.removeOccurrences(
                        "aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa",
                        "aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa"));
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
    public void testMergedArrays() {
        assertTrue(Arrays.equals(new int[] { 1, 2, 3, 4, 5, 6 }, ArrayStringTasks.mergeSortedArrays(
                new int[] {1, 3, 5, 0, 0, 0}, new int[] { 2, 4, 6 }, 3, 3 )));
        assertTrue(Arrays.equals(new int[] { 1, 2, 4, 4, 5, 6, 7, 8 }, ArrayStringTasks.mergeSortedArrays(
                new int[] { 4, 5, 7, 8, 0, 0, 0, 0}, new int[] { 1, 2, 4, 6 }, 4, 4 )));
    }

    @Test
    public void testFindZeroSum() {
        assertTrue(Arrays.equals(new int[] {-1, 3, -2}, ArrayStringTasks.findZeroSum(new int[] {2, -1, 3, -2, 5})));
        assertTrue(Arrays.equals(new int[0], ArrayStringTasks.findZeroSum(new int[] {2, 3, -2, 5})));
    }

    @Test
    public void testFindMedianSortedArrays() {
        assertEquals(2.5, ArrayStringTasks.findMedianSortedArrays(new int[] { 1, 2 }, new int[] { 3, 4 }), 0.0001);
        assertEquals(2, ArrayStringTasks.findMedianSortedArrays(new int[] { 1, 3 }, new int[] { 2 }), 0.0001);
        assertEquals(11, ArrayStringTasks.findMedianSortedArrays(new int[] { 1, 3, 8, 9, 15 }, new int[] { 7, 11, 18, 19, 21, 25 }), 0.0001);
        assertEquals(13.5, ArrayStringTasks.findMedianSortedArrays(new int[] { 3, 5, 7, 9, 11, 16 }, new int[] { 23, 26, 31, 35 }), 0.0001);
    }

    @Test
    public void testLongestConsecutive() {
        assertEquals(0, ArrayStringTasks.longestConsecutive(new int[0]));
        assertEquals(1, ArrayStringTasks.longestConsecutive(new int[] { 100, 40, 200, 10, 30, 20 }));
        assertEquals(4, ArrayStringTasks.longestConsecutive(new int[] { 100, 4, 200, 1, 3, 2 }));
        assertEquals(9, ArrayStringTasks.longestConsecutive(new int[] { 0, 3, 7, 2, 5, 8, 4, 6, 0, 1 }));
    }

    @Test
    public void testMergeKSortedArrays() {
        assertTrue(Arrays.equals(new int[] { 1, 2, 3, 4, 5, 6, 7, 8, 9 }, ArrayStringTasks.mergeKSortedArrays(
                new int[][] {
                        new int[] { 1, 4, 7 },
                        new int[] { 2, 5, 8 },
                        new int[] { 3, 6, 9 }
                }
        )));
        assertTrue(Arrays.equals(new int[] { 2, 3, 5, 6, 8, 9 }, ArrayStringTasks.mergeKSortedArrays(
                new int[][] {
                        new int[0],
                        new int[] { 2, 5, 8 },
                        new int[] { 3, 6, 9 }
                }
        )));
    }

    @Test
    public void testFindAnagrams() {
        Assert.assertEquals(
                Arrays.asList(new Integer[] { 0, 6 }),
                ArrayStringTasks.findAnagrams("cbaebabacd", "abc"));
        Assert.assertEquals(
                Arrays.asList(new Integer[] { 0, 1, 2 }),
                ArrayStringTasks.findAnagrams("abab", "ab"));
    }
}
