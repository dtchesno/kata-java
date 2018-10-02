package com.dtchesno.kata.test;

import com.dtchesno.kata.careercup.Tasks;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
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
}
