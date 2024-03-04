package com.dtchesno.kata.struct;

import java.util.*;

public class AlienDictionary {

    private Map<Character, List<Character>> adjMap = new HashMap<>();
    private Map<Character, Boolean> seen = new HashMap<>();

    public String alienOrder(String[] words) {
        for (String w : words) {
            for (char c : w.toCharArray()) {
                List<Character> adj = adjMap.get(c);
                if (adj == null) {
                    adjMap.put(c, new ArrayList<>());
                }
            }
        }

        for (int i = 0; i < words.length - 1; i++) {
            String w1 = words[i];
            String w2 = words[i + 1];
            for (int j = 0; j < w1.length() && j < w2.length(); j++) {
                if (w1.charAt(j) != w2.charAt(j)) {
                    List<Character> adj = adjMap.get(w2.charAt(j));
                    adj.add(w1.charAt(j));
                    break;
                }
            }
        }

        StringBuilder result = new StringBuilder();
        for (char c : adjMap.keySet()) {
            if (!dfs(c, result)) {
                return "";
            }
        }
        return result.toString();
    }

    private boolean dfs(char c, StringBuilder result) {
        Boolean state = seen.get(c);
        if (state != null) {
            return state;
        }

        seen.put(c, false);

        List<Character> adj = adjMap.get(c);
        for (char d : adj) {
            if (!dfs(d, result)) {
                return false;
            }
        }

        seen.put(c, true);
        result.append(c);
        return true;
    }
}