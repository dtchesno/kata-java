package com.dtchesno.kata.test;

import com.dtchesno.kata.bitops.BitsSolution;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;

import static org.junit.Assert.*;


public class BitTest {

    @Before
    public void setUp() throws Exception {
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void testReplace() {
        assertEquals(0b10000010101, BitsSolution.replace(0b10000000000, 0b10101, 0, 4));
        assertEquals(0b10001010100, BitsSolution.replace(0b10000000000, 0b10101, 2, 6));
        assertEquals(0b10001010100, BitsSolution.replace(0b10001111100, 0b10101, 2, 6));
    }

    @Test
    public void testPrintBinary() {
        assertEquals("0.0", BitsSolution.printBinary("0.0"));
        assertEquals("11111.0", BitsSolution.printBinary("31.0"));
        assertEquals("11.1", BitsSolution.printBinary("3.5"));
        assertEquals("11.01", BitsSolution.printBinary("3.25"));
        assertEquals("11.001", BitsSolution.printBinary("3.125"));
        assertEquals("11.101", BitsSolution.printBinary("3.625"));
        assertEquals("11.111", BitsSolution.printBinary("3.875"));
        assertEquals("ERROR", BitsSolution.printBinary("3.1234567"));
    }

    @Test
    public void testFindNeighbors() {
        assertEquals(true, Arrays.equals(new int[] { 0b000001, 0b000100 }, BitsSolution.findNeighbors(0b000010)));
        assertEquals(true, Arrays.equals(new int[] { 0b001110, 0b010101 }, BitsSolution.findNeighbors(0b010011)));
        assertEquals(true, Arrays.equals(new int[] { 0b010011, 0b010110 }, BitsSolution.findNeighbors(0b010101)));
        assertEquals(true, Arrays.equals(new int[] { 0b011010, 0b100011 }, BitsSolution.findNeighbors(0b011100)));
    }

    @Test
    public void testSwapNeighbors() {
        assertEquals(0b101010, BitsSolution.swapNeighbors(0b010101));
        assertEquals(0b000110, BitsSolution.swapNeighbors(0b001001));
        assertEquals(0b000111, BitsSolution.swapNeighbors(0b001011));
    }

    @Test
    public void testParity() {
        assertEquals(0, BitsSolution.parity(0b000000));
        assertEquals(1, BitsSolution.parity(0b000001));
        assertEquals(1, BitsSolution.parity(0b000010));
        assertEquals(1, BitsSolution.parity(0b100000));
        assertEquals(0, BitsSolution.parity(0b100001));
        assertEquals(1, BitsSolution.parity(0b001011));
    }

    @Test
    public void testFindMissing() {
        assertEquals(0, BitsSolution.findMissingElement(createArrayWithMissingElement(100, 0)));
        assertEquals(1, BitsSolution.findMissingElement(createArrayWithMissingElement(100, 1)));
        assertEquals(11, BitsSolution.findMissingElement(createArrayWithMissingElement(100, 11)));
        assertEquals(32, BitsSolution.findMissingElement(createArrayWithMissingElement(100, 32)));
        assertEquals(99, BitsSolution.findMissingElement(createArrayWithMissingElement(100, 99)));
    }

    @Test
    public void testFindMissAndDup() {
        assertTrue(Arrays.equals(new int[] {0, 99}, BitsSolution.findMissAndDupElements(createArrayWithMissingElement(100, 0, 99))));
        assertTrue(Arrays.equals(new int[] {1, 11}, BitsSolution.findMissAndDupElements(createArrayWithMissingElement(100, 1, 11))));
        assertTrue(Arrays.equals(new int[] {11, 12}, BitsSolution.findMissAndDupElements(createArrayWithMissingElement(100, 11, 12))));
        assertTrue(Arrays.equals(new int[] {32, 75}, BitsSolution.findMissAndDupElements(createArrayWithMissingElement(100, 32, 75))));
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

    private int[] createArrayWithMissingElement(int size, int missing, int dup) {
        int[] array = new int[size];
        for (int i = 0; i < size; i++) {
            if (i == missing) {
                array[i] = dup;
            } else {
                array[i] = i;
            }
        }
        return array;
    }

    @Test
    public void testAdd() {
        assertEquals(12 + 23, BitsSolution.add(12, 23));
        assertEquals(4 + 6, BitsSolution.add(4, 6));
        assertEquals(7 + 3, BitsSolution.add(7, 3));
    }

    @Test
    public void testMultiply() {
        assertEquals(7 * 2, BitsSolution.multiply(7, 2));
        assertEquals(12 * 23, BitsSolution.multiply(12, 23));
        assertEquals(4 * 6, BitsSolution.multiply(4, 6));
        assertEquals(7 * 3, BitsSolution.multiply(7, 3));
    }
}
