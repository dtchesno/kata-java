package com.dtchesno.kata.dp;

public class WildcardMatching {

    // Given an input string (s) and a pattern (p), implement wildcard pattern matching with support for '?' and '*' where:
    //  '?' Matches any single character.
    //  '*' Matches any sequence of characters (including the empty sequence).
    //
    // 44. Wildcard Matching
    // https://leetcode.com/problems/wildcard-matching/, #44
    // hard - amazon, google
    public static boolean isMatch(String s, String p) {
        int[][] mem = new int[s.length() + 1][p.length() + 1];
        return isMatchDP(s, p, 0, 0, mem) == 1;
    }

    private static int isMatchDP(String s, String p, int i, int j, int[][] mem) {
        if (j == p.length()) {
            return i == s.length() ? 1 : -1;
        }

        if (mem[i][j] != 0) return mem[i][j];

        if (p.charAt(j) == '*') {
            mem[i][j] = Math.max(
                isMatchDP(s, p, i, j + 1, mem), // skip
                i < s.length() ? isMatchDP(s, p, i + 1, j, mem) : -1 // take
            );
        } else {
            mem[i][j] = i < s.length() && (p.charAt(j) == s.charAt(i) || p.charAt(j) == '?')
                ? isMatchDP(s, p, i + 1, j + 1, mem)
                : -1;
        }
        return mem[i][j];
    }
}










