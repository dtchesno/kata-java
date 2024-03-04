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

    @Test
    public void testWordSearchExist() {
        Assert.assertTrue(Backtracking.wordSearchExist(
            new char[][] {{'A','B','C','E'}, {'S','F','C','S'}, {'A','D','E','E'}},
            "ABCCED"));

        Assert.assertTrue(Backtracking.wordSearchExist(
                new char[][] {{'A','B','C','E'},{'S','F','C','S'},{'A','D','E','E'}},
                "SEE"));

        Assert.assertFalse(Backtracking.wordSearchExist(
                new char[][] {{'A','B','C','E'},{'S','F','C','S'},{'A','D','E','E'}},
                "ABCB"));
    }

    @Test
    public void testSolveQueens() {
        Assert.assertEquals(
                Arrays.asList(
                        Arrays.asList(".Q..","...Q","Q...","..Q."),
                        Arrays.asList("..Q.","Q...","...Q",".Q..")),
                Backtracking.solveNQueens(4));
        Assert.assertEquals(
                Arrays.asList(Arrays.asList("Q")),
                Backtracking.solveNQueens(1));
    }

    @Test
    public void testCombinationSum() {
        Assert.assertEquals(
                Arrays.asList(Arrays.asList(2, 2, 3), Arrays.asList(7)),
                Backtracking.combinationSum(new int[] {2,3,6,7}, 7));
        Assert.assertEquals(
                Arrays.asList(Arrays.asList(2,2,2,2), Arrays.asList(2,3,3), Arrays.asList(3,5)),
                Backtracking.combinationSum(new int[] {2,3,5}, 8));
        Assert.assertEquals(
                new ArrayList<List<Integer>>(),
                Backtracking.combinationSum(new int[0], 1));
    }
}
