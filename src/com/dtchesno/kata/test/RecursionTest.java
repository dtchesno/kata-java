package com.dtchesno.kata.test;

import com.dtchesno.kata.misc.MiscSolution;
import com.dtchesno.kata.recursion.RecursionSolution;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.*;

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

    @Test
    public void testReverseStack() {
        Stack<Integer> s = new Stack<>();
        int n = 10;
        for (int i = 1; i <= n; i++) {
            s.push(i);
        }
        RecursionSolution.reverseStack(s);
        for (int i = 1; i <= n; i++) {
            assertEquals(i, (int)s.pop());
        }
    }

    @Test
    public void testGetAllPermutations() {
        List<Integer> input = Arrays.asList(1, 2, 3);
        HashSet<List<Integer>> expected = new HashSet<>();
        expected.add(Arrays.asList(1, 2, 3));
        expected.add(Arrays.asList(1, 3, 2));
        expected.add(Arrays.asList(2, 1, 3));
        expected.add(Arrays.asList(2, 3, 1));
        expected.add(Arrays.asList(3, 1, 2));
        expected.add(Arrays.asList(3, 2, 1));

        List<List<Integer>> result = RecursionSolution.getAllPermutations(input);
        assertEquals(expected.size(), result.size());
        assertTrue(expected.containsAll(result));
    }

    @Test
    public void testSortStack() {
        assertEquals(true, Arrays.equals(
                new int[] { 1, 2, 3, 4, 5, 6, 7 },
                RecursionSolution.sortStack(new int[] { 7, 1, 3, 5, 2, 4, 6})));
    }
}
