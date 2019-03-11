package com.dtchesno.kata.test;

import com.dtchesno.kata.struct.ArrayTasks;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import java.util.Arrays;
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
        assertTrue(Arrays.equals(new int[] { 4, 5, 6, 7, 1, 2, 3 }, ArrayTasks.rotateRight(new int[] { 1, 2, 3, 4, 5, 6, 7 }, 4)));
    }

    @Test
    public void testReverseWords() {
        assertEquals("blue is sky the", ArrayTasks.reverseWords("the sky is blue"));
    }
}
