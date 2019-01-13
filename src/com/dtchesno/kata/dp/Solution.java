package com.dtchesno.kata.dp;

import java.util.Arrays;
import java.util.List;

public class Solution {

    // TODO: add description
    public static int minMessiness(List<String> words, int lineLen) {
        int[] M = new int[words.size()];
        Arrays.fill(M, Integer.MAX_VALUE);

        // calculate M[0] - ends on 0th word
        int space = lineLen - words.get(0).length();
        M[0] = space * space;

        // calculate M for all 'end' words (1-size) based on prev.calculations;
        // if M[i] is optimal then M[i-1] should be optimal too;
        // we try to combine current i-th word with preceding, calculating current line M and check M[i-...]
        for (int i = 1; i < words.size(); i++) {
            space = lineLen - words.get(i).length();
            M[i] = M[i - 1] + space * space;
            for (int j = i - 1; j >= 0; j--) {
                space -= (words.get(j).length() + 1);
                if (space < 0) {
                    break;
                }
                M[i] = Math.min(M[i], space * space + (j - 1 >= 0 ? M[j - 1] : 0));
            }
        }
        return M[words.size() - 1];
    }

    public static int longestNondecreasingSeq(int[] array) {
        int[] length = new int[array.length];
        int max = 1;
        length[0] = 1;
        for (int i = 1; i < array.length; i++) {
            for (int j = 0; j < i; j++) {
                if (array[j] <= array[i]) {
                    length[i] = Math.max(length[i], length[j] + 1);
                }
            }
            max = Math.max(max, length[i]);
        }
        return max;
    }
}
