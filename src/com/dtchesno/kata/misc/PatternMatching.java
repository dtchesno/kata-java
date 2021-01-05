package com.dtchesno.kata.misc;

public class PatternMatching {

    // Given an input string (s) and a pattern (p), implement regular expression matching with support for '.' and '*'.
    // '.' - any single character; '*' - 0 or many previous characters;; '.*' - 0 or many any character
    //
    // https://leetcode.com/problems/regular-expression-matching/, #10
    // Hard - facebook, amazon
    public static boolean isMatch(String s, String p) {
        if (s == null || p == null) return false;
        return isMatch(s, p, 0, 0);
    }

    private static boolean isMatch(String s, String p, int i, int j) {
        if (i == s.length() && j == p.length()) {
            return true;
        }
        if (j == p.length()) {
            return false;
        }
        if (p.charAt(j) == '*') return false;

        if (j < p.length() - 1 && p.charAt(j + 1) == '*') {
            return isMatchStar(s, p, i, j);
        }

        if (p.charAt(j) == '.' || (i < s.length() && p.charAt(j) == s.charAt(i))) {
            return isMatch(s, p, i + 1, j + 1);
        }
        return false;
    }

    private static boolean isMatchStar(String s, String p, int i, int j) {
        char repeated = p.charAt(j);
        j += 2;
        while (i < s.length() && (s.charAt(i) == repeated || repeated == '.')) {
            if (isMatch(s, p, i++, j)) {
                return true;
            }
        }
        return isMatch(s, p, i, j);
    }
}
