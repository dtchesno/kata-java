package com.dtchesno.kata.test;

import com.dtchesno.kata.misc.PatternMatching;
import com.dtchesno.kata.misc.TaskScheduler;
import com.dtchesno.kata.misc.WildcardMatching;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;


public class PatternMatchingTest {

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

    @Test
    public void PatterMatching_IsMatch() {
        assertFalse(PatternMatching.isMatch("aa", "a"));
        assertTrue(PatternMatching.isMatch("aa", "a*"));
        assertTrue(PatternMatching.isMatch("ab", ".*"));
        assertTrue(PatternMatching.isMatch("aab", "c*a*b"));
        assertFalse(PatternMatching.isMatch("mississippi", "mis*is*p*."));
        assertTrue(PatternMatching.isMatch("a", "ab*"));
        assertFalse(PatternMatching.isMatch("ab", ".*c"));
    }

    @Test
    public void WildcardMatching_IsMatch() {
        assertFalse(WildcardMatching.isMatch("zacabz", "*a?b*"));
        assertTrue(WildcardMatching.isMatch("", "*****"));
        assertFalse(WildcardMatching.isMatch("aa", "a"));
        assertTrue(WildcardMatching.isMatch("aa", "*"));
        assertFalse(WildcardMatching.isMatch("cb", "?a"));
        assertTrue(WildcardMatching.isMatch("adceb", "*a*b"));
        assertFalse(WildcardMatching.isMatch("acdcb", "a*c?b"));
    }
}
