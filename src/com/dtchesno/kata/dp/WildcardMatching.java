package com.dtchesno.kata.dp;

public class WildcardMatching {

    // Given an input string (s) and a pattern (p), implement wildcard pattern matching with support for '?' and '*' where:
    //  '?' Matches any single character.
    //  '*' Matches any sequence of characters (including the empty sequence).
    // https://leetcode.com/problems/wildcard-matching/, #44
    // hard - amazon, google
    public static boolean isMatch(String s, String p) {
        int[][] mem = new int[s.length() + 1][p.length() + 1];
        return isMatchDP(s, p, 0, 0, mem) == 1;
    }

    private static int isMatchDP(String s, String p, int i, int j, int[][] mem) {
        if (i > s.length() || j > p.length()) {
            return -1;
        }

        if (mem[i][j] != 0) return mem[i][j];

        if (i == s.length() && j == p.length()) {
            mem[i][j] = 1;
        } else if (j == p.length()) {
            mem[i][j] = -1;
        } else if (p.charAt(j) == '*') {
            mem[i][j] = Math.max(isMatchDP(s, p, i + 1, j, mem), isMatchDP(s, p, i, j + 1, mem));
        } else if (i < s.length() && (s.charAt(i) == p.charAt(j) || p.charAt(j) == '?')) {
            mem[i][j] = isMatchDP(s, p, i + 1, j + 1, mem);
        } else {
            mem[i][j] = -1;
        }
        return mem[i][j];
    }

//    public static boolean isMatch(String s, String p) {
//        int[][] mem = new int[s.length() + 1][p.length() + 1];
//        return isMatchDP(s, p, 0, 0, mem) == 1;
//    }
//
//    private static int isMatchDP(String s, String p, int i, int j, int[][] mem) {
//        if (i > s.length() || j > p.length()) {
//            return -1;
//        }
//
//        if (mem[i][j] != 0) {
//            return mem[i][j];
//        }
//
//        if (i == s.length() && j == p.length()) {
//            mem[i][j] = 1;
//        } else if (j == p.length()) {
//            mem[i][j] = -1;
//        } else if (p.charAt(j) == '*') {
//            mem[i][j] = Math.max(isMatchDP(s, p, i + 1, j, mem), isMatchDP(s, p, i, j + 1, mem));
//        } else if (i < s.length() && (p.charAt(j) == s.charAt(i) || p.charAt(j) == '?')) {
//            mem[i][j] = isMatchDP(s, p, i + 1, j + 1, mem);
//        } else {
//            mem[i][j] = -1;
//        }
//        return mem[i][j];
//    }
}
