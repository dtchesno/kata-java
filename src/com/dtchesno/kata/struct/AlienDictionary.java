package com.dtchesno.kata.struct;

import java.util.*;

// https://leetcode.com/problems/alien-dictionary
public class AlienDictionary {

    private Map<Character, List<Character>> orderMap;
    private char[] state;

    public String alienOrder(String[] words) {
        orderMap = new HashMap<>();
        state = new char[256];

        for (String w : words) {
            for (char c : w.toCharArray()) {
                orderMap.computeIfAbsent(c, k -> new ArrayList<Character>());
            }
        }

        for (int i = 1; i < words.length; i++) {
            String w1 = words[i - 1];
            String w2 = words[i];
            if (w1.indexOf(w2) == 0 && w2.length() < w1.length()) return "";
            for (int j = 0; j < w1.length() && j < w2.length(); j++) {
                if (w1.charAt(j) != w2.charAt(j)) {
                    orderMap.get(w2.charAt(j)).add(w1.charAt(j));
                    break;
                }
            }
        }

        StringBuilder result = new StringBuilder();
        for (Character c : orderMap.keySet()) {
            if (!dfs(c, result)) return "";
        }
        return result.toString();
    }

    private boolean dfs(Character c, StringBuilder result) {
        if (state[c] == 1) return false;
        if (state[c] == 2) return true;

        state[c] = 1;
        for (Character pred : orderMap.get(c)) {
            if (!dfs(pred, result)) return false;
        }
        result.append(c);
        state[c] = 2;
        return true;
    }
}