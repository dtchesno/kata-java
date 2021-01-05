package com.dtchesno.kata.test;

import com.dtchesno.kata.misc.TaskScheduler;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;


public class TaskSchedulerTest {

    @Before
    public void setUp() throws Exception {
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void testTestScheduler() {
        assertEquals(6, TaskScheduler.leastInterval(new char[] {'A', 'A', 'A', 'B', 'B', 'B'}, 0));
        assertEquals(8, TaskScheduler.leastInterval(new char[] {'A', 'A', 'A', 'B', 'B', 'B'}, 2));
        assertEquals(16, TaskScheduler.leastInterval(
                new char[] {'A','A','A','A','A','A','B','C','D','E','F','G'}, 2));
    }

    @Test
    public void testReaarangeString() {
        assertEquals("abcabc", TaskScheduler.rearrangeString("aabbcc", 3));
        assertEquals("", TaskScheduler.rearrangeString("aaabc", 3));
        assertEquals("abcadbca", TaskScheduler.rearrangeString("aaadbbcc", 2));
    }
}
