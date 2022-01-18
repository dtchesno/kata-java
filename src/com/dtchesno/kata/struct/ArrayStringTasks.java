package com.dtchesno.kata.struct;

import java.util.Arrays;
import java.util.HashMap;

public class ArrayStringTasks {

    // https://www.programcreek.com/2015/03/rotate-array-in-java/
    // {1, 2, 3, 4, 5, 6, 7}, 4 -> {4, 5, 6, 7, 1, 2, 3}
    // [selected - 2]
    public static int[] rotateRight(int[] arr, int k) {
        reverse(arr, 0, arr.length - k - 1);
        reverse(arr, arr.length - k, arr.length - 1);
        reverse(arr, 0, arr.length - 1);
        return arr;
    }

    private static void reverse(int[] arr, int i, int j) {
        while (i < j) {
            int temp = arr[i];
            arr[i] = arr[j];
            arr[j] = temp;
            i++;
            j--;
        }
    }

    // reverse all words in sentence
    // Aziz 7.6 pg.101
    // [selected - 1]
    public static String reverseWords(String input) {
        char[] buf = input.toCharArray();
        reverse(buf, 0, buf.length - 1);
        int start = 0;
        for (int i = 0; i < buf.length; i++) {
            if (buf[i] == ' ' && start < i - 1) {
                reverse(buf, start, i - 1);
                start = i + 1;
            }
        }
        // last word
        if (start < buf.length) {
            reverse(buf, start, buf.length - 1);
        }
        return String.valueOf(buf);
    }

    private static void reverse(char[] str, int i, int j) {
        while (i < j) {
            char temp = str[i];
            str[i] = str[j];
            str[j] = temp;
            i++;
            j--;
        }
    }


    // convert to/from Roman
    // [selected - 3]
    public static String toRoman(int number) {
        int[] values = new int[] {100, 90, 50, 40, 10, 9, 5, 4, 1};
        String[] digits = new String[] {"C", "XC", "L", "XL", "X", "IX", "V", "IV", "I"};
        StringBuffer result = new StringBuffer();
        for (int i = 0; i < values.length && number > 0; i++) {
            int k = number / values[i];
            number %= values[i];
            while (k-- > 0) {
                result.append(digits[i]);
            }
        }
        return result.toString();
    }

    public static int fromRoman(String roman) {
        int result = 0;
        HashMap<Character, Integer> values = new HashMap<>();
        values.put('I', 1);
        values.put('V', 5);
        values.put('X', 10);
        values.put('L', 50);
        values.put('C', 100);

        int prev = 0;
        for (int i = 0; i < roman.length(); i++) {
            int curr = values.get(roman.charAt(i));
            if (curr > prev && prev != 0) {
                result += (curr - 2 * prev);
            } else {
                result += curr;
            }
            prev = curr;
        }
        return result;
    }


    // Aziz 7.13 pg.109
    // [selected - 1]
    public static int firstOccurrence(String str, String pat) {
        if (pat.length() > str.length()) {
            return -1;
        }
        int sHash = 0;
        int pHash = 0;
        int base = 26;
        int power = 1;
        for (int i = 0; i < pat.length(); i++) {
            sHash = sHash * base + str.charAt(i);
            pHash = pHash * base + pat.charAt(i);
            power = (i == 0) ? 1 : power * base;
        }
        for (int i = pat.length(); i < str.length(); i++) {
            if (sHash == pHash && str.substring(i - pat.length(), i).equals(pat)) {
                return i - pat.length();
            }
            sHash -= str.charAt(i - pat.length()) * power;
            sHash = sHash * base + str.charAt(i);
        }
        // check str remainder
        if (sHash == pHash && str.substring(str.length() - pat.length()).equals(pat)) {
            return str.length() - pat.length();
        }
        return -1;
    }


    // Given two strings s and part, perform the following operation on s until all occurrences of the substring
    // part are removed: Find the leftmost occurrence of the substring part and remove it from s.
    // Return s after removing all occurrences of part.
    // A substring is a contiguous sequence of characters in a string.
    // Input: s = "daabcbaabcbc", part = "abc" => Output: "dab"
    // Input: s = "axxxxyyyyb", part = "xy" => Output: "ab"
    // https://leetcode.com/problems/remove-all-occurrences-of-a-substring/
    public static String removeOccurrences(String s, String part) {
        if (part.length() > s.length()) {
            return s;
        }

        if (part.equals(s)) {
            return "";
        }

        int phash = 0;
        int power = 1;
        for (int i = 0; i < part.length(); i++) {
            phash = phash * 26 + part.charAt(i);
            if (i > 0) {
                power *= 26;
            }
        }

        StringBuilder sb = new StringBuilder(s);
        int[] shash = new int[sb.length()];
        int pos = 0;
        while (pos < sb.length()) {
            // update shash
            shash[pos] = 0;
            if (pos >= part.length()) {
                shash[pos] = shash[pos - 1] - (sb.charAt(pos - part.length())) * power;
            } else {
                shash[pos] = (pos == 0) ? 0 : shash[pos - 1];
            }
            shash[pos] = shash[pos] * 26 + sb.charAt(pos);

            // check hash and part match
            if (shash[pos] == phash && part.equals(sb.substring(pos + 1 - part.length(), pos + 1))) {
                sb.delete(pos + 1 - part.length(), pos + 1);
                pos -= part.length();
            }
            pos++;
        }

        String remainder = sb.toString();
        return remainder.equals(part) ? "" : remainder;
    }


    // find any subarray which sum == 0
    // byte-by-byte #11 pg.11
    // [selected - 2]
    public static int[] findZeroSum(int[] a) {
        HashMap<Integer, Integer> map = new HashMap<>();
        int sum = 0;
        int i = -1;
        int j = 0;
        for (; j < a.length; j++) {
            sum += a[j];
            if (map.containsKey(sum)) {
                i = map.get(sum) + 1;
                break;
            }
            map.put(sum, j);
        }
        if (i == -1) {
            return new int[0];
        }
        return Arrays.copyOfRange(a, i, j + 1);
    }

    // done
    // https://leetcode.com/problems/valid-number/ (hard) [string, dfa]
    // https://leetcode.com/problems/simplify-path/ (medium) [string, stack]
}
