package com.dtchesno.kata.struct;

import java.util.*;

// 269. Alien Dictionary
// https://leetcode.com/problems/alien-dictionary
public class AlienDictionary {

    private Map<Character, List<Character>> orderMap;
    private char[] state;

    public String alienOrder(String[] words) {
        orderMap = new HashMap<>();
        state = new char[256];

        for (String word : words) {
            for (char c : word.toCharArray()) {
                orderMap.computeIfAbsent(c, k -> new ArrayList<>());
            }
        }

        for (int i = 1; i < words.length; i++) {
            String w1 = words[i - 1];
            String w2 = words[i];
            if (w1.indexOf(w2) == 0 && w1.length() > w2.length()) return "";
            for (int j = 0; j < w1.length() && j < w2.length(); j++) {
                if (w1.charAt(j) != w2.charAt(j)) {
                    orderMap.get(w2.charAt(j)).add(w1.charAt(j));
                    break;
                }
            }
        }

        var result = new StringBuilder();
        for (char c : orderMap.keySet()) {
            if (!dfs(c, result)) return "";
        }
        return result.toString();
    }

    private boolean dfs(char c, StringBuilder result) {
        if (state[c] == 1) return false;
        if (state[c] == 2) return true;

        state[c] = 1;
        for (var v : orderMap.get(c)) {
            if (!dfs(v, result)) return false;
        }
        result.append(c);
        state[c] = 2;
        return true;
    }
}