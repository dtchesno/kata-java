package com.dtchesno.kata.dp;

public class PatternMatching {

    // Given an input string (s) and a pattern (p), implement regular expression matching with support for '.' and '*'.
    // '.' - any single character; '*' - 0 or many previous characters;; '.*' - 0 or many any character
    //
    // 10. Regular Expression Matching
    // https://leetcode.com/problems/regular-expression-matching/, #10
    // Hard - facebook, amazon
    //
    // Solution
    //  - this is DP problem as it's likely we will repeat match on pattern substring while with change things at the beginning
    //  - within 'main' method there is straightforward logic
    //      - if we exhausted both strings - it's a match
    //      - if we used whole pattern, but not the string - that's mismatch
    //      - if next pattern char is * - we recursively try to match *; we need to look ahead, otherwise we fail early
    //          and prune the match path, e.g. (ab) matches (ac*b) - we might fail at c-vs-b
    //      - lastly check that we either has '.' or chars match, and call main method recursively for remainders
    //      - otherwise that's mismatch
    //  - for star matching we simply loop and try to match by skipping repeated/. chars, if nothing matches,
    //      we do final call from position where we reached to
    public static boolean isMatch(String s, String p) {
        // -1 - no match, 1 - match, 0 - not calculated
        int[][] mem = new int[s.length() + 1][p.length() + 1];
        return isMatch(s, p, 0, 0, mem) == 1;
    }

    private static int isMatch(String s, String p, int i, int j, int[][] mem) {
        if (j == p.length()) {
            return i == s.length() ? 1 : -1;
        }

        if (mem[i][j] != 0) return mem[i][j];

        boolean isMatch = (i < s.length() && (p.charAt(j) == s.charAt(i) || p.charAt(j) == '.'));
        if (j < p.length() - 1 && p.charAt(j + 1) == '*') {
            mem[i][j] = Math.max(
                isMatch(s, p, i, j + 2, mem), // skip '*'
                isMatch ? isMatch(s, p, i + 1, j, mem) : -1 // use '*'
            );
        } else {
            mem[i][j] = isMatch ? isMatch(s, p, i + 1, j + 1, mem) : -1;
        }
        return mem[i][j];
    }
}
