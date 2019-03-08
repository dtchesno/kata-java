package com.dtchesno.kata.test;

import com.dtchesno.kata.bitops.Tasks;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
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
        assertEquals(true, Arrays.equals(new int[] { 0b000001, 0b000100 }, Tasks.findNeighbors(0b000010)));
        assertEquals(true, Arrays.equals(new int[] { 0b001110, 0b010101 }, Tasks.findNeighbors(0b010011)));
        assertEquals(true, Arrays.equals(new int[] { 0b010011, 0b010110 }, Tasks.findNeighbors(0b010101)));
        assertEquals(true, Arrays.equals(new int[] { 0b011010, 0b100011 }, Tasks.findNeighbors(0b011100)));
    }

    @Test
    public void testSwapNeighbors() {
        assertEquals(0b101010, Tasks.swapNeighbors(0b010101));
        assertEquals(0b000110, Tasks.swapNeighbors(0b001001));
        assertEquals(0b000111, Tasks.swapNeighbors(0b001011));
    }

    @Test
    public void testFindMissing() {
        assertEquals(0, Tasks.findMissingElement(createArrayWithMissingElement(100, 0)));
        assertEquals(1, Tasks.findMissingElement(createArrayWithMissingElement(100, 1)));
        assertEquals(11, Tasks.findMissingElement(createArrayWithMissingElement(100, 11)));
        assertEquals(32, Tasks.findMissingElement(createArrayWithMissingElement(100, 32)));
        assertEquals(99, Tasks.findMissingElement(createArrayWithMissingElement(100, 99)));
    }

    private int[] createArrayWithMissingElement(int size, int missing) {
        int[] array = new int[size];
        for (int i = 0, j = 0; i < size; i++, j++) {
            if (i == missing) {
                j++;
            }
            array[i] = j;
        }
        return array;
    }

    @Test
    public void testAdd() {
        assertEquals(12 + 23, Tasks.add(12, 23));
        assertEquals(4 + 6, Tasks.add(4, 6));
        assertEquals(7 + 3, Tasks.add(7, 3));
    }

    @Test
    public void testMultiply() {
        assertEquals(7 * 2, Tasks.multiply(7, 2));
        assertEquals(12 * 23, Tasks.multiply(12, 23));
        assertEquals(4 * 6, Tasks.multiply(4, 6));
        assertEquals(7 * 3, Tasks.multiply(7, 3));
    }
}
