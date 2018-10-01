package com.dtchesno.kata.test;

import com.dtchesno.kata.careercup.Tasks;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

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
}
