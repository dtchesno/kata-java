package com.dtchesno.kata.dp;

public class PatternMatching {

    // Given an input string (s) and a pattern (p), implement regular expression matching with support for '.' and '*'.
    // '.' - any single character; '*' - 0 or many previous characters;; '.*' - 0 or many any character
    //
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
        if (s == null || p == null) return false;

        // -1 - no match, 1 - match, 0 - not calculated
        int[][] mem = new int[s.length() + 1][p.length() + 1];
        return isMatch(s, p, 0, 0, mem) == 1;
    }

    private static int isMatch(String s, String p, int i, int j, int[][] mem) {
        if (i > s.length() || j > p.length()) {
            return -1;
        }

        if (mem[i][j] != 0) {
            return mem[i][j];
        }

        if (i == s.length() && j == p.length()) {
            mem[i][j] = 1;
        } else if (j == p.length()) {   // it's fine if s is over, we still could match empty string with e.g. 'a*'
            mem[i][j] = -1;
        } else if (j < p.length() - 1 && p.charAt(j + 1) == '*') {
            mem[i][j] = isMatchStar(s, p, i, j, mem);
        } else if (p.charAt(j) == '.' || (i < s.length() && p.charAt(j) == s.charAt(i))) {
            mem[i][j] = isMatch(s, p, i + 1, j + 1, mem);
        } else {
            mem[i][j] = -1;
        }

        return mem[i][j];
    }

    private static int isMatchStar(String s, String p, int i, int j, int[][] mem) {
        char repeated = p.charAt(j);
        j += 2;
        while (i < s.length() && (s.charAt(i) == repeated || repeated == '.')) {
            if (isMatch(s, p, i++, j, mem) == 1) {
                return 1;
            }
        }
        return isMatch(s, p, i, j, mem);
    }
}
