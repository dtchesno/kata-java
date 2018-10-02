package com.dtchesno.kata.recursion;

import java.util.HashSet;
import java.util.Set;

public class Tasks {
    public static Set<String> braces(int n) {
        return braces(n, n, "", new HashSet<String>());
    }

    private static Set<String> braces(int l, int r, String str, Set<String> result) {
        if (l == 0 && r == 0) {
            result.add(str);
            return result;
        }
        Set<String> s1 = null;
        Set<String> s2 = null;
        if (l > 0) {
            s1 =  braces(l - 1, r, str + '(', result);
        }
        if (r > l) {
            s2 =  braces(l, r - 1, str + ')', result);
        }
        if (s1 != null) {
            result.addAll(s1);
        }
        if (s2 != null) {
            result.addAll(s2);
        }
        return result;
    }
}
