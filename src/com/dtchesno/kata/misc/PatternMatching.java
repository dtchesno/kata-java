package com.dtchesno.kata.misc;

public class PatternMatching {

    // Given an input string (s) and a pattern (p), implement regular expression matching with support for '.' and '*'.
    // '.' - any single character; '*' - 0 or many previous characters;; '.*' - 0 or many any character
    //
    // https://leetcode.com/problems/regular-expression-matching/, #10
    // Hard - facebook, amazon
    public static boolean isMatch(String s, String p) {
        if (s == null || p == null) return false;

        // -1 - no match, 1 - match, 0 - not calculated
        int[][] mem = new int[s.length() + 1][p.length() + 1];
        return isMatch(s, p, 0, 0, mem);
    }

    private static boolean isMatch(String s, String p, int i, int j, int[][] mem) {
        if (i > s.length() || j > p.length()) {
            return false;
        }

        if (mem[i][j] != 0) {
            return mem[i][j] == 1;
        }

        if (i == s.length() && j == p.length()) {
            mem[i][j] = 1;
        } else if (j == p.length()) {
            mem[i][j] = -1;
        } else if (p.charAt(j) == '*') {
            mem[i][j] = -1;
        } else if (j < p.length() - 1 && p.charAt(j + 1) == '*') {
            mem[i][j] = isMatchStar(s, p, i, j, mem) ? 1 : -1;
        } else if (p.charAt(j) == '.' || (i < s.length() && p.charAt(j) == s.charAt(i))) {
            mem[i][j] = isMatch(s, p, i + 1, j + 1, mem) ? 1 : -1;
        } else {
            mem[i][j] = -1;
        }

        return mem[i][j] == 1;
    }

    private static boolean isMatchStar(String s, String p, int i, int j, int[][] mem) {
        char repeated = p.charAt(j);
        j += 2;
        while (i < s.length() && (s.charAt(i) == repeated || repeated == '.')) {
            if (isMatch(s, p, i++, j, mem)) {
                return true;
            }
        }
        return isMatch(s, p, i, j, mem);
    }
}
