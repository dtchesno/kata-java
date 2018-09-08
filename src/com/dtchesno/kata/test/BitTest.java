package com.dtchesno.kata.test;

import com.dtchesno.kata.bitops.Tasks;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;

import static org.junit.Assert.assertEquals;


public class BitTest {

    @Before
    public void setUp() throws Exception {
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void testReplace() {
        assertEquals(0b10000010101, Tasks.replace(0b10000000000, 0b10101, 0, 4));
        assertEquals(0b10001010100, Tasks.replace(0b10000000000, 0b10101, 2, 6));
        assertEquals(0b10001010100, Tasks.replace(0b10001111100, 0b10101, 2, 6));
    }

    @Test
    public void testPrintBinary() {
        assertEquals("0.0", Tasks.printBinary("0.0"));
        assertEquals("11111.0", Tasks.printBinary("31.0"));
        assertEquals("11.1", Tasks.printBinary("3.5"));
        assertEquals("11.01", Tasks.printBinary("3.25"));
        assertEquals("11.001", Tasks.printBinary("3.125"));
        assertEquals("11.101", Tasks.printBinary("3.625"));
        assertEquals("11.111", Tasks.printBinary("3.875"));
        assertEquals("ERROR", Tasks.printBinary("3.1234567"));
    }

    @Test
    public void testFindNeighbors() {
        assertEquals(true, Arrays.equals(new int[] { 0b0001, 0b0100 }, Tasks.findNeighbors(0b0010)));
    }
}
