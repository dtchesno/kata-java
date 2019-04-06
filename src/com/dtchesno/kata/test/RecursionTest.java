package com.dtchesno.kata.test;

import com.dtchesno.kata.misc.MiscSolution;
import com.dtchesno.kata.recursion.RecursionSolution;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.HashSet;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;


public class RecursionTest {

    @Before
    public void setUp() throws Exception {
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void testBracePermutations() {
        HashSet<String> expected = new HashSet<>();
        expected.add("()()()");
        expected.add("()(())");
        expected.add("(())()");
        expected.add("((()))");
        expected.add("(()())");
        assertEquals(expected, RecursionSolution.permuteBraces(3));
    }

    @Test
    public void testBracesBalance() {
        assertTrue(MiscSolution.isBracesBalanced("()"));
        assertTrue(MiscSolution.isBracesBalanced("()()"));
        assertTrue(MiscSolution.isBracesBalanced("()(())"));

        assertTrue(!MiscSolution.isBracesBalanced("(("));
        assertTrue(!MiscSolution.isBracesBalanced("("));
        assertTrue(!MiscSolution.isBracesBalanced(")"));
        assertTrue(!MiscSolution.isBracesBalanced("())("));
        assertTrue(!MiscSolution.isBracesBalanced("()(()"));
    }
}
