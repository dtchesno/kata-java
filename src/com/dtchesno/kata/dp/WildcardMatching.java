package com.dtchesno.kata.dp;

import java.util.Arrays;

public class WildcardMatching {

    // Given an input string (s) and a pattern (p), implement wildcard pattern matching with support for '?' and '*' where:
    //  '?' Matches any single character.
    //  '*' Matches any sequence of characters (including the empty sequence).
    // https://leetcode.com/problems/wildcard-matching/, #44
    // hard - amazon, google
    public static boolean isMatch(String s, String p) {
        int[][] mtable = new int[s.length() + 1][p.length() + 1];
        return isMatchDPRecursive(
                removeMultipleWildcardsHelper(s).toCharArray(), 0, p.toCharArray(), 0, mtable) == 1;
//        return isMatchDP(s, p);
    }

    private static String removeMultipleWildcardsHelper(String input) {
        StringBuffer sb = new StringBuffer();
        char lastChar = 'a';
        for (Character c : input.toCharArray()) {
            if (lastChar == '*' && c == '*') {
                continue;
            }
            sb.append(c);
            lastChar = c;
        }
        return sb.toString();
    }

    private static boolean isMatchDP(String s, String p) {
        if (removeMultipleWildcardsHelper(p).equals("*")) {
            return true;
        }
        if (s.equals(p)) {
            return true;
        }
        if (s.isEmpty() || p.isEmpty()) {
            return false;
        }

        boolean[][] mem = new boolean[p.length() + 1][s.length() + 1];
        mem[0][0] = true;

        for (int pIdx = 1; pIdx <= p.length(); pIdx++) {

            if (p.charAt(pIdx - 1) == '*') {
                int sIdx = 0;
                while (sIdx <= s.length() && !mem[pIdx - 1][sIdx]) {
                    sIdx++;
                }
                while (sIdx <= s.length()) {
                    mem[pIdx][sIdx++] = true;
                }
            } else if (p.charAt(pIdx - 1) == '?') {
                for (int sIdx = 1; sIdx <= s.length(); sIdx++) {
                    mem[pIdx][sIdx] = mem[pIdx - 1][sIdx - 1];
                }
            } else {
                for (int sIdx = 1; sIdx <= s.length(); sIdx++) {
                    mem[pIdx][sIdx] = mem[pIdx - 1][sIdx - 1] && p.charAt(pIdx - 1) == s.charAt(sIdx - 1);
                }
            }
        }

        return mem[p.length()][s.length()];
    }

    private static int isMatchDPRecursive(char[] s, int sOffset, char[] p, int pOffset, int[][] mtable) {
        if (mtable[sOffset][pOffset] != 0) {
            return mtable[sOffset][pOffset];
        }

        if (Arrays.equals(s, sOffset, s.length, p, pOffset, p.length)) {
            mtable[sOffset][pOffset] = 1;
        } else if (pOffset < p.length && p[pOffset] == '*') {
            mtable[sOffset][pOffset] = isMatchStarHelper(s, sOffset, p, pOffset, mtable);
        } else if (pOffset == p.length || sOffset == s.length) {
            mtable[sOffset][pOffset] = -1;
        } else if (p[pOffset] == '?' || s[sOffset] == p[pOffset]) {
            mtable[sOffset][pOffset] = isMatchDPRecursive(s, sOffset + 1, p, pOffset + 1, mtable);
        } else {
            mtable[sOffset][pOffset] = -1;
        }

        return mtable[sOffset][pOffset];
    }

    private static int isMatchStarHelper(char[] s, int sOffset, char[] p, int pOffset, int[][] mtable) {
        if (sOffset < s.length) {
            mtable[sOffset][pOffset] = isMatchDPRecursive(s, sOffset + 1, p, pOffset, mtable);
        }
        if (pOffset < p.length && mtable[sOffset][pOffset] != 1) {
            mtable[sOffset][pOffset] = isMatchDPRecursive(s, sOffset, p, pOffset + 1, mtable);
        }
        return mtable[sOffset][pOffset];
    }
}
