package com.dtchesno.kata.struct;

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
        Map<Character, Integer> values = Map.of('I', 1, 'V', 5, 'X', 10, 'L', 50, 'C', 100);
        int result = 0;
        int prev = Integer.MAX_VALUE;
        for (char c : roman.toCharArray()) {
            int value = values.get(c);
            result += (value <= prev) ? value : value - 2 * prev;
            prev = value;
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


    // byte-by-byte merge sorted arrays, pg.10, https://www.byte-by-byte.com/mergearrays/
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

        List<Integer> result = new ArrayList<>();
        int pos = 0;
        while (true) {
            if (Arrays.equals(sHash, pHash)) result.add(pos);

            if (pos + len == s.length()) break;

            sHash[s.charAt(pos)]--;
            sHash[s.charAt(pos + len)]++;
            pos++;
        }

        return result;
    }

    // while not checking arrays every time, it has cost to call update method;
    // leetcode shows slightly lower perf
//    public static List<Integer> findAnagrams(String s, String p) {
//        List<Integer> result = new ArrayList<>();
//        if (p.length() > s.length()) return result;
//        int[] diff = new int[256];
//        int delta = 0;
//        for (int i = 0; i < p.length(); i++) {
//            delta = updateDelta(diff, s.charAt(i), delta, 1);
//            delta = updateDelta(diff, p.charAt(i), delta, -1);
//        }
//        if (delta == 0) {
//            result.add(0);
//        }
//        for (int i = 1; i < s.length() - p.length() + 1; i++) {
//            delta = updateDelta(diff, s.charAt(i - 1), delta, -1);
//            delta = updateDelta(diff, s.charAt(i + p.length() - 1), delta, 1);
//            if (delta == 0) {
//                result.add(i);
//            }
//        }
//        return result;
//    }
//    private static int updateDelta(int[] diff, int i, int delta, int d) {
//        diff[i] += d;
//        if (diff[i] == 0) {
//            delta--;
//        } else if (diff[i] * d > 0) {
//            delta++;
//        } else {
//            delta--;
//        }
//        return delta;
//    }

    // https://leetcode.com/problems/text-justification/
    public static List<String> fullJustify(String[] words, int maxWidth) {
        List<String> result = new ArrayList<>();
        List<String> line = new ArrayList<>();
        int currentLineLength = 0;
        for (int i = 0; i < words.length; i++) {
            if (currentLineLength + line.size() + words[i].length() > maxWidth) {
                result.add(buildLine(line, maxWidth - currentLineLength));
                line.clear();
                currentLineLength = 0;
            }
            line.add(words[i]);
            currentLineLength += words[i].length();
        }

        StringBuilder sb = new StringBuilder();
        sb.append(String.join(" ", line));
        addSpaces(sb, maxWidth - currentLineLength - (line.size() - 1));
        result.add(sb.toString());
        return result;
    }

    private static String buildLine(List<String> words, int spaceCount) {
        StringBuilder sb = new StringBuilder();

        // to handle 'special' case when there is single word in the line (division by zero)
        for (int i = 0; i < words.size() - 1; i++) {
            sb.append(words.get(i));
            int spaces = (int)Math.ceil((double)spaceCount / (words.size() - 1 - i));
            addSpaces(sb, spaces);
            spaceCount -= spaces;
        }
        sb.append(words.get(words.size() - 1));
        addSpaces(sb, spaceCount);
        return sb.toString();
    }

    private static void addSpaces(StringBuilder sb, int count) {
        for (int i = 0; i < count; i++) {
            sb.append(" ");
        }
    }


    // 239. Sliding Window Maximum
    // https://leetcode.com/problems/sliding-window-maximum/description/
    public static int[] maxSlidingWindow(int[] nums, int k) {
        int[] result = new int[nums.length - k + 1];
        Deque<Integer> q = new LinkedList<>();
        for (int i = 0; i < k; i++) {
            while (!q.isEmpty() && nums[q.peekLast()] <= nums[i]) q.pollLast();
            q.add(i);
        }
        result[0] = nums[q.peek()];
        for (int i = k; i < nums.length; i++) {
            while (!q.isEmpty() && nums[q.peekLast()] <= nums[i]) q.pollLast();
            if (!q.isEmpty() && q.peek() <= i - k) q.poll();
            q.add(i);
            result[i - k + 1] = nums[q.peek()];
        }
        return result;
    }


    // 2402. Meeting Rooms III
    // https://leetcode.com/problems/meeting-rooms-iii/
//    public static int mostBooked(int n, int[][] meetings) {
//        // sort meetings by start date
//        List<int[]> _meetings = new ArrayList<>();
//        for (int[] m : meetings) {
//            _meetings.add(m);
//        }
//        Collections.sort(_meetings, (a, b) -> a[0] - b[0]);
//
//        // room queues: (id, available time)
//        Queue<int[]> available = new PriorityQueue<>((a, b) -> a[0] - b[0]);
//        Queue<int[]> inUse = new PriorityQueue<>((a, b) -> a[1] != b[1] ? a[1] - b[1] : a[0] - b[0]);
//        for (int i = 0; i < n; i++) {
//            available.add(new int[] { i, 0 });
//        }
//
//        int[] count = new int[n];
//        for (int[] m : _meetings) {
//            while (!inUse.isEmpty() && inUse.peek()[1] <= m[0]) {
//                available.add(inUse.poll());
//            }
//            if (available.isEmpty()) available.add(inUse.poll());
//
//            int[] room = available.poll();
//            count[room[0]]++;
//            //room[1] = m[1];
//            room[1] = room[1] <= m[0] ? m[1] : m[1] + (room[1] - m[0]);
//            inUse.add(room);
//        }
//
//        int iMax = 0;
//        int max = count[iMax];
//        for (int i = 1; i < n; i++) {
//            if (count[i] > max) {
//                iMax = i;
//                max = count[iMax];
//            }
//        }
//        return iMax;
//    }

    public static int mostBooked(int n, int[][] meetings) {
        List<int[]> _meetings = new ArrayList<>();
        for (var m : meetings) {
            _meetings.add(m);
        }
        Collections.sort(_meetings, (a, b) -> a[0] - b[0]);

        // room queues: (id, available time)
        Queue<int[]> available = new PriorityQueue<>((a, b) -> a[0] - b[0]);
        Queue<int[]> inUse = new PriorityQueue<>((a, b) -> a[1] != b[1] ? a[1] - b[1] : a[0] - b[0]);
        for (int i = 0; i < n; i++) {
            available.add(new int[] { i, 0 });
        }

        int[] count = new int[n];
        for (int[] m : _meetings) {
            while (!inUse.isEmpty() && inUse.peek()[1] <= m[0]) {
                available.add(inUse.poll());
            }
            if (available.isEmpty()) available.add(inUse.poll());

            int[] room = available.poll();
            count[room[0]]++;
            //room[1] = m[1];
            room[1] = room[1] <= m[0] ? m[1] : m[1] + (room[1] - m[0]);
            inUse.add(room);
        }

        int iMax = 0;
        int max = count[iMax];
        for (int i = 1; i < n; i++) {
            if (count[i] > max) {
                iMax = i;
                max = count[iMax];
            }
        }
        return iMax;
    }

    // https://leetcode.com/problems/longest-increasing-subsequence/
    public static int longestIncreasingSubseq(int[] seq) {
        List<Integer> result = new ArrayList<>();
        result.add(seq[0]);
        for (int i = 1; i < seq.length; i++) {
            if (seq[i] > result.get(result.size() - 1)) {
                result.add(seq[i]);
            } else {
                int pos = longestIncreasingSubseqFindInsertPos(seq[i], result);
                result.set(pos, seq[i]);
            }
        }
        return result.size();
    }

    private static int longestIncreasingSubseqFindInsertPos(int val, List<Integer> result) {
        int left = 0;
        int right = result.size() - 1;
        while (left < right) {
            int mid = (left + right) / 2;
            if (val == result.get(mid)) {
                return mid;
            } else if (val > result.get(mid)) {
                left = mid + 1;
            } else {
                right = mid;
            }
        }
        return left;
    }


    // 76 https://leetcode.com/problems/minimum-window-substring
    // debt: a->0, b->-1, total = 0
    // "acbbaca"
    // "aba"
    // queue: 0->a, 2->b, 3->b, 4->a
    // result: acbba
    public static String minWindow(String s, String t) {
        if (s.length() == 0 || t.length() == 0) return "";

        Map<Character, Integer> dictT = new HashMap<>();
        for (char c : t.toCharArray()) {
            int count = dictT.getOrDefault(c, 0);
            dictT.put(c, count + 1);
        }

        String minWindow = null;
        int left = 0;
        int right = 0;
        int found = 0;
        Map<Character, Integer> dictW = new HashMap<>();
        while (right < s.length()) {
            char c = s.charAt(right);
            int count = dictW.getOrDefault(c, 0);
            dictW.put(c, count + 1);

            if (dictT.get(c) == dictW.get(c)) found++;

            while (left <= right && found == dictT.size()) {
                if (minWindow == null || minWindow.length() > right - left + 1) {
                    minWindow = s.substring(left, right + 1);
                }
                char c2 = s.charAt(left);
                dictW.put(c2, dictW.get(c2) - 1);
                if (dictT.containsKey(c2) && dictW.get(c2) < dictT.get(c2)) found--;
                left++;
            }
            right++;
        }
        return minWindow != null ? minWindow : "";
    }

//    public static String minWindow(String s, String t) {
//        Map<Character, Integer> debt = new HashMap<>();
//        for (char c : t.toCharArray()) {
//            debt.put(c, debt.getOrDefault(c, 0) + 1);
//        }
//
//        String minWindow = "";
//        int remainedDebt = t.length();
//        LinkedList<int[]> match = new LinkedList<>();
//        for (int i = 0; i < s.length(); i++) {
//            char c = s.charAt(i);
//            if (!debt.containsKey(c)) {
//                continue;
//            }
//            match.add(new int[] { i, s.charAt(i) });
//
//            int currentCharDebt = debt.get(c) - 1;
//            debt.put(c, currentCharDebt);
//
//            // keep going, we already done with char
//            if (currentCharDebt < 0) {
//                continue;
//            }
//
//            remainedDebt--;
//
//            if (remainedDebt == 0) {
//                minWindow = getMinWindowAndDrain(minWindow, s, match, debt);
//
//                // we removed some chars from the beginning and owe again
//                remainedDebt = 1;
//            }
//        }
//
//        return minWindow;
//    }
//
//    private static String getMinWindowAndDrain(
//            String currentMinWindow,
//            String s,
//            LinkedList<int[]> match,
//            Map<Character, Integer> debt) {
//
//        // drain match list while debt is negative, as no impact on match window
//        while (!match.isEmpty()) {
//            char ch = (char)match.getFirst()[1];
//            int d = debt.get(ch);
//            if (d == 0) {
//                break;
//            }
//            debt.put(ch, d + 1);
//            match.remove();
//        }
//
//        // now, get current match window
//        String window = s.substring(match.getFirst()[0], match.getLast()[0] + 1);
//
//        // lastly remove first char in match list which brings debt to 1 to slide further
//        char firstChar = (char)match.getFirst()[1];
//        debt.put(firstChar, 1);
//        match.remove();
//
//        return window.length() < currentMinWindow.length() || currentMinWindow.equals("") ? window : currentMinWindow;
//    }

    // done
    // https://leetcode.com/problems/valid-number/ (hard) [string, dfa]
    // https://leetcode.com/problems/simplify-path/ (medium) [string, stack]
}
