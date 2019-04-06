package com.dtchesno.kata.recursion;

import java.util.HashSet;
import java.util.Set;

public class RecursionSolution {
    public static Set<String> permuteBraces(int n) {
        return permuteBraces(n, n, "", new HashSet<String>());
    }

    private static Set<String> permuteBraces(int l, int r, String str, Set<String> result) {
        if (l == 0 && r == 0) {
            result.add(str);
            return result;
        }
        Set<String> s1 = null;
        Set<String> s2 = null;
        if (l > 0) {
            s1 =  permuteBraces(l - 1, r, str + '(', result);
        }
        if (r > l && r > l) {
            s2 =  permuteBraces(l, r - 1, str + ')', result);
        }
        if (s1 != null) {
            result.addAll(s1);
        }
        if (s2 != null) {
            result.addAll(s2);
        }
        return result;
    }

    // reverse stack: byte-by-byte #20
    // sort stacks: byte-by-byte #28
    // [selected - 1]

    // generate all permutations of given list: byte-by-byte #12 pg.12
    // [selected - 2]
}
