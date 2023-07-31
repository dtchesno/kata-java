package com.dtchesno.kata.struct;

import gnu.trove.TDoubleArrayList;
import javafx.util.Pair;
import org.jvnet.staxex.BinaryText;

import java.nio.charset.StandardCharsets;
import java.util.*;

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
    // reverse entire buffer, then go over buffer and reverse word when found whitespace
    public static String reverseWords(String input) {
        char[] result = input.toCharArray();
        reverse(result, 0, result.length - 1);

        int start = 0;
        for (int i = 0; i < result.length; i++) {
            if (result[i] == ' ') {
                reverse(result, start, i - 1);
                start = i + 1;
            }
        }

        if (start < result.length) {
            reverse(result, start, result.length - 1);
        }

        return String.valueOf(result);
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


    // byte-by-byte merge sorted arrays, pg.10
    // going backwards and copy into result
    // then copy remainder of smaller array if any
    public static int[] mergeSortedArrays(int[] a, int[] b, int aLen, int bLen) {
        int i = aLen - 1;
        int j = bLen - 1;
        int pos = a.length - 1;

        // going backwards, copy at the end of result array
        while (i >= 0 && j >= 0) {
            if (a[i] > b[j]) {
                a[pos--] = a[i--];
            } else {
                a[pos--] = b[j--];
            }
        }

        // copy remainder of smaller array
        while (j >= 0) {
            a[pos--] = b[j--];
        }

        return a;
    }

    // Byte-by-byte pg.5
//    public static double findMedianSortedArrays(int[] nums1, int[] nums2) {
//        return nums1.length <= nums2.length
//                ? findMedianSortedArrays(nums1, nums2, (nums1.length + 1 ) / 2 - 1, (nums1.length + 1) / 2 - 1)
//                : findMedianSortedArrays(nums2, nums1, (nums2.length + 1) / 2 - 1, (nums2.length + 1) / 2 - 1);
//    }
//
//    private static double findMedianSortedArrays(int[] nums1, int[] nums2, int i, int iLast) {
//        // calculate partition
//        int shortLeft = i < 0 ? Integer.MIN_VALUE : nums1[i];
//        int shortRight = i + 1 >= nums1.length ? Integer.MAX_VALUE : nums1[i + 1];
//        int shortLeftCount = i + 1;
//        int longLeftCount = (nums1.length + nums2.length + 1) / 2 - shortLeftCount;
//        int longLeft = longLeftCount <= 0 ? Integer.MIN_VALUE : nums2[longLeftCount - 1];
//        int longRight = longLeftCount >= nums2.length ? Integer.MAX_VALUE : nums2[longLeftCount];
//
//        // exit condition
//        if (shortLeft <= longRight && longLeft <= shortRight) {
//            return (nums1.length + nums2.length) % 2 == 1
//                    ? Math.max(shortLeft, longLeft)
//                    : ((double)Math.max(shortLeft, longLeft) + Math.min(shortRight, longRight)) / 2;
//        }
//
//        // TODO: iLast will be neighbor - we need to use interval instead of and
//        // go to the middle of left or right subintervals partitioned by i
//        // move pointer left or right
//        return (longLeft > shortRight)
//            ? findMedianSortedArrays(nums1, nums2, (i + iLast) / 2 + 1, i)
//            : findMedianSortedArrays(nums1, nums2, (i + iLast) / 2 - 1, i);
//    }

    // leetcode: https://leetcode.com/problems/median-of-two-sorted-arrays/description/
    // move along arrays - find left and right (could be the same - single element); take med of elements
    public static double findMedianSortedArrays(int[] nums1, int[] nums2) {
        int iLeft = (nums1.length + nums2.length - 1) / 2;
        int i = 0;
        int j = 0;
        int left = 0;
        while (i + j <= iLeft) {
            if (i == nums1.length) {
                left = nums2[j++];
            } else if (j == nums2.length) {
                left = nums1[i++];
            } else if (nums1[i] <= nums2[j]) {
                left = nums1[i++];
            } else {
                left = nums2[j++];
            }
        }

        if ((nums1.length + nums2.length) % 2 == 1) return left;

        int right = i == nums1.length ? nums2[j] : j == nums2.length ? nums1[i] : Math.min(nums1[i], nums2[j]);
        return ((double) left + right) / 2;
    }


    // byte-by-byte: Merge K Arrays, pg.9, https://www.byte-by-byte.com/mergekarrays/
    // create priority queue with (arraysId, arrPos, value)
    // initialize with heads and iterate by pulling top and adding next into queue from the given array if any
    public static int[] mergeKSortedArrays(int[][] arrays) {
        List<Integer> resultList = new ArrayList<>();

        // arrayId, arrayOffset, value
        PriorityQueue<int[]> q = new PriorityQueue<>((a, b) -> a[2] - b[2]);

        for (int i = 0; i < arrays.length; i++) {
            if (arrays[i].length == 0) continue;
            q.add(new int[] {i, 0, arrays[i][0]});
        }

        while (!q.isEmpty()) {
            int[] top = q.poll();
            resultList.add(top[2]);
            top[1]++;
            if (top[1] < arrays[top[0]].length) {
                top[2] = arrays[top[0]][top[1]];
                q.add(top);
            }
        }
        return resultList.stream().mapToInt(i -> i).toArray();
    }

    // leetcode: https://leetcode.com/problems/longest-consecutive-sequence/description/
    public static int longestConsecutive(int[] nums) {
        HashSet<Integer> map = new HashSet<>();

        for (int val : nums) {
            map.add(val);
        }

        int maxLen = 0;
        for (int val : nums) {
            // value already part of some sequence
            if (!map.contains(val)) {
                continue;
            }

            // will try to expand from here, so, start with len 1
            int len = 1;

            // go forward
            int next = val + 1;
            while (map.contains(next)) {
                map.remove(next);
                next++;
                len++;
            }

            // go backward
            int prev = val - 1;
            while (map.contains(prev)) {
                map.remove(prev);
                prev--;
                len++;
            }

            maxLen = Math.max(maxLen, len);
        }

        return maxLen;
    }

//    public static int longestConsecutive(int[] nums) {
//        Set<Integer> cache = new TreeSet<>();
//        for (int n : nums) {
//            cache.add(n);
//        }
//
//        int maxLen = 0;
//        int len = 1;
//        int prev = Integer.MIN_VALUE;
//        for (int n : cache) {
//            if (n == prev + 1) {
//                len++;
//                prev = n;
//                maxLen = Math.max(maxLen, len);
//            } else {
//                maxLen = Math.max(maxLen, len);
//                len = 1;
//                prev = n;
//            }
//        }
//        return maxLen;
//    }

    // leetcode: https://leetcode.com/problems/find-all-anagrams-in-a-string/description/
    // build byte array for ASCII (256); set counts per char index; iterate and compare arrays; one in - one out
    public static List<Integer> findAnagrams(String s, String p) {
        if (p.isEmpty() || p.length() > s.length()) {
            return new ArrayList<>();
        }

        byte[] sHash = new byte[256];
        byte[] pHash = new byte[256];
        int len = p.length();

        for (int i = 0; i < p.length(); i++) {
            sHash[s.charAt(i)]++;
            pHash[p.charAt(i)]++;
        }

        ArrayList<Integer> result = new ArrayList<>();

        int pos = 0;
        while (true) {
            if (Arrays.equals(sHash, pHash)) {
                result.add(pos);
            }

            if (pos + len == s.length())
                break;

            sHash[s.charAt(pos)]--;
            sHash[s.charAt(pos + len)]++;
            pos++;
        }

        return result;
    }

    // done
    // https://leetcode.com/problems/valid-number/ (hard) [string, dfa]
    // https://leetcode.com/problems/simplify-path/ (medium) [string, stack]
}
