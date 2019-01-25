package com.dtchesno.kata.test;

import com.dtchesno.kata.careercup.Tasks;
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
        assertEquals(2, Tasks.getDistinctStrings(new String[] { "abc", "de"}));
        assertEquals(2, Tasks.getDistinctStrings(new String[] { "abc", "def"}));
        assertEquals(2, Tasks.getDistinctStrings(new String[] { "abcd", "cbad", "bacd" }));
        assertEquals(1, Tasks.getDistinctStrings(new String[] { "abcde", "cbade", "cdabe" }));
    }

//    mergeSortedIS
    @Test
    public void testMergeStreams() {
        ByteArrayInputStream arr1 = new ByteArrayInputStream(new byte[] { 1, 2, 5, 10, 18 });
        ByteArrayInputStream arr2 = new ByteArrayInputStream(new byte[] { 1, 3, 4, 20 });
        ByteArrayInputStream arr3 = new ByteArrayInputStream(new byte[] { 100, 110, 120 });
        ByteArrayOutputStream os = new ByteArrayOutputStream();

        Tasks.mergeStreams(os, new InputStream[] { arr1, arr2, arr3 });
        assertTrue(Arrays.equals(os.toByteArray(), new byte[] { 1, 1, 2, 3, 4, 5, 10, 18, 20, 100, 110, 120}));
    }

    @Test
    public void testSpiral() {
        assertTrue(Arrays.deepEquals(
                Tasks.spiral(3),
                new int[][] {
                        {1,2,3},
                        {8,9,4},
                        {7,6,5}
                }
        ));

        assertTrue(Arrays.deepEquals(
                Tasks.spiral(4),
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
        assertTrue(!Tasks.isOneEditAway("cat", "dog"));
        assertTrue(Tasks.isOneEditAway("cat", "cats"));
        assertTrue(Tasks.isOneEditAway("cat", "cut"));
        assertTrue(Tasks.isOneEditAway("cat", "cast"));
        assertTrue(Tasks.isOneEditAway("cat", "at"));
        assertTrue(!Tasks.isOneEditAway("cat", "act"));
    }

    @Test
    public void testLookAndSay() {
        assertEquals("1", Tasks.lookAndSay(-2));
        assertEquals("1", Tasks.lookAndSay(0));
        assertEquals("1", Tasks.lookAndSay(1));
        assertEquals("11", Tasks.lookAndSay(2));
        assertEquals("21", Tasks.lookAndSay(3));
        assertEquals("1211", Tasks.lookAndSay(4));
        assertEquals("111221", Tasks.lookAndSay(5));
        assertEquals("312211", Tasks.lookAndSay(6));
        assertEquals("13112221", Tasks.lookAndSay(7));
        assertEquals("1113213211", Tasks.lookAndSay(8));
        assertEquals("31131211131221", Tasks.lookAndSay(9));
        assertEquals("13211311123113112211", Tasks.lookAndSay(10));
    }

    @Test
    public void testReverseStack() {
        Stack<Integer> s = new Stack<>();
        s.push(1);
        s.push(2);
        s.push(3);
        s.push(4);
        Tasks.reverseStack(s);
        assertEquals(1, (int) s.pop());
        assertEquals(2, (int) s.pop());
        assertEquals(3, (int) s.pop());
        assertEquals(4, (int) s.pop());
    }
}
