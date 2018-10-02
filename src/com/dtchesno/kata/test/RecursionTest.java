package com.dtchesno.kata.test;

import com.dtchesno.kata.recursion.Tasks;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.HashSet;

import static org.junit.Assert.assertEquals;


public class RecursionTest {

    @Before
    public void setUp() throws Exception {
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void testBraces() {
        HashSet<String> expected = new HashSet<>();
        expected.add("()()()");
        expected.add("()(())");
        expected.add("(())()");
        expected.add("((()))");
        expected.add("(()())");
        assertEquals(expected, Tasks.braces(3));
    }

}
