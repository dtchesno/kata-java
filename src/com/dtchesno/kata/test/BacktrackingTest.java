package com.dtchesno.kata.test;

import com.dtchesno.kata.recursion.Backtracking;
import org.junit.Assert;
import org.junit.Test;

import java.util.*;

public class BacktrackingTest {
    @Test
    public void testRemoveInvalidParentheses() {
        Assert.assertEquals(
                new HashSet<>(Arrays.asList("(())()","()()()")),
                new HashSet<>(Backtracking.removeInvalidParentheses("()())()")));

        Assert.assertEquals(
                new HashSet<>(Arrays.asList("(a())()","(a)()()")),
                new HashSet<>(Backtracking.removeInvalidParentheses("(a)())()")));

        Assert.assertEquals(
                new HashSet<>(Arrays.asList("")),
                new HashSet<>(Backtracking.removeInvalidParentheses(")(")));
    }
}
