package com.dtchesno.kata.test;

import com.dtchesno.kata.dp.PatternMatching;
import com.dtchesno.kata.dp.WildcardMatching;
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
    public void PatterMatching_IsMatch() {
        assertTrue(PatternMatching.isMatch("bbbba", ".*a*a"));
        assertFalse(PatternMatching.isMatch("aa", "a"));
        assertTrue(PatternMatching.isMatch("aa", "a*"));
        assertTrue(PatternMatching.isMatch("ab", ".*"));
        assertTrue(PatternMatching.isMatch("aab", "c*a*b"));
        assertFalse(PatternMatching.isMatch("mississippi", "mis*is*p*."));
        assertTrue(PatternMatching.isMatch("a", "ab*"));
        assertFalse(PatternMatching.isMatch("ab", ".*c"));
        assertFalse(PatternMatching.isMatch("a", ".*..a*"));
    }

    @Test
    public void WildcardMatching_IsMatch() {
        //assertFalse(WildcardMatching.isMatch("zacabz", "*a?b*"));
        assertTrue(WildcardMatching.isMatch("", "*****"));
        assertFalse(WildcardMatching.isMatch("aa", "a"));
        assertTrue(WildcardMatching.isMatch("aa", "*"));
        assertFalse(WildcardMatching.isMatch("cb", "?a"));
        assertTrue(WildcardMatching.isMatch("adceb", "*a*b"));
        assertFalse(WildcardMatching.isMatch("acdcb", "a*c?b"));
    }
}
