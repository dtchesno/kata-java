package com.dtchesno.kata.test;

import com.dtchesno.kata.misc.MiscSolution;
import com.dtchesno.kata.struct.ArrayStringTasks;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import java.util.Arrays;
import java.util.Set;

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
//        assertEquals("dab", ArrayStringTasks.removeOccurrences("daabcbaabcbc", "abc"));
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
        //assertEquals(1, ArrayStringTasks.fromRoman("I"));
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
//        assertEquals(0, ArrayStringTasks.longestConsecutive(new int[0]));
//        assertEquals(1, ArrayStringTasks.longestConsecutive(new int[] { 100, 40, 200, 10, 30, 20 }));
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

    @Test
    public void testLargestRectangleAreaStack() {
        Assert.assertEquals(10, MiscSolution.largestRectangleAreaStack(new int[] {2,1,5,6,2,3}));
        Assert.assertEquals(4, MiscSolution.largestRectangleAreaStack(new int[] {2,4}));
    }

    @Test
    public void testTrapWater() {
        Assert.assertEquals(6, MiscSolution.trap(new int[] {0,1,0,2,1,0,1,3,2,1,2,1}));
        Assert.assertEquals(9, MiscSolution.trap(new int[] {4,2,0,3,2,5}));
    }

    @Test
    public void testTextJustification() {
        Assert.assertEquals(
                Arrays.asList("This    is    an","example  of text","justification.  "),
                ArrayStringTasks.fullJustify(new String[] {"This", "is", "an", "example", "of", "text", "justification."}, 16));
        Assert.assertEquals(
                Arrays.asList("What   must   be","acknowledgment  ","shall be        "),
                ArrayStringTasks.fullJustify(new String[] {"What","must","be","acknowledgment","shall","be"}, 16));
        Assert.assertEquals(
                Arrays.asList("Science  is  what we","understand      well","enough to explain to","a  computer.  Art is","everything  else  we","do                  "),
                ArrayStringTasks.fullJustify(new String[] {"Science","is","what","we","understand","well","enough","to","explain","to","a","computer.","Art","is","everything","else","we","do"}, 20));
    }

    @Test
    public void testMaxSlidingWindow() {
        Assert.assertArrayEquals(
                new int[] {3,3,5,5,6,7},
                ArrayStringTasks.maxSlidingWindow(new int[] {1,3,-1,-3,5,3,6,7}, 3));
        Assert.assertArrayEquals(
                new int[] {1},
                ArrayStringTasks.maxSlidingWindow(new int[] {1}, 1));
        Assert.assertArrayEquals(
                new int[] {3,3,2,5},
                ArrayStringTasks.maxSlidingWindow(new int[] {1,3,1,2,0,5}, 3));
    }

    @Test
    public void testMostBooked() {
        Assert.assertEquals(0, ArrayStringTasks.mostBooked(2, new int[][] {
            {0,10},{1,5},{2,7},{3,4}
        }));
        Assert.assertEquals(1, ArrayStringTasks.mostBooked(3, new int[][] {
            {1,20},{2,10},{3,5},{4,9},{6,8}
        }));
    }

    @Test
    public void testLongestIncreasingSubseq() {
        Assert.assertEquals(4, ArrayStringTasks.longestIncreasingSubseq(new int[] {10,9,2,5,3,7,101,18}));
        Assert.assertEquals(3, ArrayStringTasks.longestIncreasingSubseq(new int[] {10,9,2,5,3,4}));
        Assert.assertEquals(4, ArrayStringTasks.longestIncreasingSubseq(new int[] {0,1,0,3,2,3}));
        Assert.assertEquals(1, ArrayStringTasks.longestIncreasingSubseq(new int[] {7,7,7,7,7,7,7}));
        Assert.assertEquals(6, ArrayStringTasks.longestIncreasingSubseq(new int[] {3,5,6,2,5,4,19,5,6,7,12}));
    }

    @Test
    public void testMinWindow() {
        Assert.assertEquals("BANC", ArrayStringTasks.minWindow("ADOBECODEBANC", "ABC"));
        Assert.assertEquals("a", ArrayStringTasks.minWindow("a", "a"));
        Assert.assertEquals("", ArrayStringTasks.minWindow("a", "aa"));
        Assert.assertEquals("baca", ArrayStringTasks.minWindow("acbbaca", "aba"));
    }

    @Test
    public void testNextLargestNumber() {
        Assert.assertEquals(1243, MiscSolution.nextLargestNumber(1234));
        Assert.assertEquals(12434, MiscSolution.nextLargestNumber(12344));
        Assert.assertEquals(-1, MiscSolution.nextLargestNumber(4321));
        Assert.assertEquals(-1, MiscSolution.nextLargestNumber(1));
        Assert.assertEquals(-1, MiscSolution.nextLargestNumber(111));
        Assert.assertEquals(2341, MiscSolution.nextLargestNumber(2314));
        Assert.assertEquals(2134, MiscSolution.nextLargestNumber(1432));
        Assert.assertEquals(435126, MiscSolution.nextLargestNumber(432651));
        Assert.assertEquals(-1, MiscSolution.nextLargestNumber(2147483486));
        Assert.assertEquals(-1, MiscSolution.nextLargestNumber(1999999999));
    }

    @Test
    public void testNextPalindrome() {
//        Assert.assertEquals("2112", MiscSolution.nextPalindrome("1221"));
        Assert.assertEquals("", MiscSolution.nextPalindrome("32123"));
        Assert.assertEquals("54455445", MiscSolution.nextPalindrome("45544554"));
    }

    @Test
    public void testCompressString() {
        // TODO
    }

    @Test
    public void testCountAndSay() {
        Assert.assertEquals("1211", ArrayStringTasks.countAndSay(4));
        Assert.assertEquals("312211", ArrayStringTasks.countAndSay(6));
    }

    @Test
    public void testSimplifyPath() {
        Assert.assertEquals("/home", ArrayStringTasks.simplifyPath("/home/"));
        Assert.assertEquals("/home/foo", ArrayStringTasks.simplifyPath("/home//foo/"));
        Assert.assertEquals("/home/user/Pictures", ArrayStringTasks.simplifyPath("/home/user/Documents/../Pictures"));
        Assert.assertEquals("/", ArrayStringTasks.simplifyPath("/../"));
        Assert.assertEquals("/.../b/d", ArrayStringTasks.simplifyPath("/.../a/../b/c/../d/./"));
    }

    @Test
    public void testMinRemoveToMakeValid() {
        Assert.assertEquals("lee(t(c)o)de", ArrayStringTasks.minRemoveToMakeValid("lee(t(c)o)de)"));
        Assert.assertEquals("ab(c)d", ArrayStringTasks.minRemoveToMakeValid("a)b(c)d"));
        Assert.assertEquals("", ArrayStringTasks.minRemoveToMakeValid("))(("));
    }

    @Test
    public void testFindKthLargest() {
        Assert.assertEquals(5, ArrayStringTasks.findKthLargest(new int [] {3,2,1,5,6,4}, 2));
        Assert.assertEquals(4, ArrayStringTasks.findKthLargest(new int [] {3,2,3,1,2,4,5,5,6}, 4));
    }

    @Test
    public void testSubarraySum() {
        Assert.assertEquals(2, ArrayStringTasks.subarraySum(new int [] {1,1,1}, 2));
        Assert.assertEquals(2, ArrayStringTasks.subarraySum(new int [] {1,2,3}, 3));
    }

    @Test
    public void testMergeIntervals() {
        Assert.assertEquals(new int[][] { {1,6},{8,10},{15,18} }, ArrayStringTasks.mergeIntervals(new int[][] {{1,3},{2,6},{8,10},{15,18}}));
        Assert.assertEquals(new int[][] { {1,5} }, ArrayStringTasks.mergeIntervals(new int[][] {{1,4},{4,5}}));
    }

    @Test
    public void testlengthOfLongestSubstring() {
        assertEquals(3, ArrayStringTasks.lengthOfLongestSubstring("abcabcbb"));
        assertEquals(1, ArrayStringTasks.lengthOfLongestSubstring("bbbbb"));
        assertEquals(3, ArrayStringTasks.lengthOfLongestSubstring("pwwkew"));
        assertEquals(1, ArrayStringTasks.lengthOfLongestSubstring(" "));
        assertEquals(5, ArrayStringTasks.lengthOfLongestSubstring("tmmzuxt"));
        assertEquals(3, ArrayStringTasks.lengthOfLongestSubstring("dvdf"));
    }
}
