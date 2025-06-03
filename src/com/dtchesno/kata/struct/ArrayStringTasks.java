package com.dtchesno.kata.struct;

import java.util.*;

public class ArrayStringTasks {

    // https://www.programcreek.com/2015/03/rotate-array-in-java/
    // {1, 2, 3, 4, 5, 6, 7}, 4 -> {4, 5, 6, 7, 1, 2, 3}
    // [selected - 2]
    public static int[] rotateRight(int[] arr, int k) {
        reverse(arr, 0, arr.length - 1 - k);
        reverse(arr, arr.length - k, arr.length - 1);
        reverse(arr, 0, arr.length - 1);
        return arr;
    }

    private static void reverse(int[] arr, int left, int right) {
        while (left < right) {
            int temp = arr[left];
            arr[left] = arr[right];
            arr[right] = temp;
            left++;
            right--;
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
            while (k-- > 0) {
                result.append(digits[i]);
            }
            number %= values[i];
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
        if (part.length() > s.length()) return s;

        StringBuilder sb = new StringBuilder(s);
        int i = part.length();
        do {
            while (i - part.length() >= 0 && sb.substring(i - part.length(), i).equals(part)) {
                sb.delete(i - part.length(), i);
                i -= part.length();
            }
            i++;
        } while (i <= sb.length());
        return sb.toString();
    }
//    public static String removeOccurrences(String s, String part) {
//        if (part.length() > s.length()) {
//            return s;
//        }
//
//        if (part.equals(s)) {
//            return "";
//        }
//
//        int phash = 0;
//        int power = 1;
//        for (int i = 0; i < part.length(); i++) {
//            phash = phash * 26 + part.charAt(i);
//            if (i > 0) {
//                power *= 26;
//            }
//        }
//
//        StringBuilder sb = new StringBuilder(s);
//        int[] shash = new int[sb.length()];
//        int pos = 0;
//        while (pos < sb.length()) {
//            // update shash
//            shash[pos] = 0;
//            if (pos >= part.length()) {
//                shash[pos] = shash[pos - 1] - (sb.charAt(pos - part.length())) * power;
//            } else {
//                shash[pos] = (pos == 0) ? 0 : shash[pos - 1];
//            }
//            shash[pos] = shash[pos] * 26 + sb.charAt(pos);
//
//            // check hash and part match
//            if (shash[pos] == phash && part.equals(sb.substring(pos + 1 - part.length(), pos + 1))) {
//                sb.delete(pos + 1 - part.length(), pos + 1);
//                pos -= part.length();
//            }
//            pos++;
//        }
//
//        String remainder = sb.toString();
//        return remainder.equals(part) ? "" : remainder;
//    }


    // find any subarray which sum == 0
    // byte-by-byte #11 pg.11
    // https://www.byte-by-byte.com/zerosum/
    // [selected - 2]
    public static int[] findZeroSum(int[] a) {
        Map<Integer, Integer> map = new HashMap<>();

        int sum = 0;
        for (int i = 0; i < a.length; i++) {
            sum += a[i];
            Integer j = map.get(sum);
            if (j != null) {
                return Arrays.copyOfRange(a, j + 1, i + 1);
            }
            map.put(sum, i);
        }
        return new int[0];
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
    // 4. Median of Two Sorted Arrays
    // leetcode: https://leetcode.com/problems/median-of-two-sorted-arrays/description/
    // move along arrays - find left and right (could be the same - single element); take med of elements
    public static double findMedianSortedArrays(int[] nums1, int[] nums2) {
        int iLeft = (nums1.length + nums2.length) / 2 - 1;
        int left = -1;
        int i = 0;
        int j = 0;

        while (i + j <= iLeft) {
            if (i == nums1.length) {
                left = nums2[j++];
            } else if (j == nums2.length) {
                left = nums1[i++];
            } else if (nums1[i] < nums2[j]) {
                left = nums1[i++];
            } else {
                left = nums2[j++];
            }
        }

        int right = -1;

        if (i == nums1.length) {
            right = nums2[j++];
        } else if (j == nums2.length) {
            right = nums1[i++];
        } else if (nums1[i] < nums2[j]) {
            right = nums1[i++];
        } else {
            right = nums2[j++];
        }

        return ((nums1.length + nums2.length) % 2 == 0) ? ((double) left + right) / 2 : right;
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

    // 128. Longest Consecutive Sequence
    // leetcode: https://leetcode.com/problems/longest-consecutive-sequence/description/
    public static int longestConsecutive(int[] nums) {
        Set<Integer> cache = new HashSet<>();
        for (int n : nums) {
            cache.add(n);
        }

        int maxLen = 0;
        for (int n : nums) {
            if (!cache.contains(n)) continue;

            // look left & right
            int len = 1;
            int prev = n;
            while (cache.contains(prev - 1)) {
                len++;
                prev--;
                cache.remove(prev);
            }
            prev = n;
            while (cache.contains(prev + 1)) {
                len++;
                prev++;
                cache.remove(prev);
            }
            cache.remove(n);
            maxLen = Math.max(maxLen, len);
        }
        return maxLen;
    }


    // 438. Find All Anagrams in a String
    // https://leetcode.com/problems/find-all-anagrams-in-a-string/description/
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

//    public static List<Integer> findAnagrams(String s, String p) {
//        int bufLength = 'z' - 'a' + 1;
//        int[] sBuf = new int[bufLength];
//        int[] pBuf = new int[bufLength];
//        for (int i = 0; i < p.length(); i++) {
//            sBuf[s.charAt(i) - 'a']++;
//            pBuf[s.charAt(i) - 'a']++;
//        }
//
//        List<Integer> result = new ArrayList<>();
//        for (int i = 0; i <= s.length() - p.length(); i++) { // i < 7
//            if (Arrays.equals(sBuf, pBuf)) {
//                result.add(i);
//            }
//            sBuf[s.charAt(i) - 'a']--;
//            if (i + p.length() < s.length()) {
//                sBuf[s.charAt(i + p.length()) - 'a']++;
//            }
//        }
//        return result;
//    }

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
    public static int mostBooked(int n, int[][] meetings) {
        Arrays.sort(meetings, (a, b) -> a[0] - b[0]);

        // rooms - id, available time
        PriorityQueue<int[]> available = new PriorityQueue<>((a, b) -> a[0] - b[0]);
        PriorityQueue<int[]> inUse = new PriorityQueue<>((a, b) -> a[1] != b[1] ? a[1] - b[1] : a[0] - b[0]);

        for (int i = 0; i < n; i++) {
            available.add(new int[] { i, 0 });
        }

        // keep track on number of meetings in the room
        int[] count = new int[n];

        for (int[] m : meetings) {

            // release room which are free before next meeting starts
            while (!inUse.isEmpty() && inUse.peek()[1] <= m[1]) {
                available.add(inUse.poll());
            }

            // if no rooms available, pull 'best room, meeting will be delayed
            if (available.isEmpty()) available.add(inUse.poll());

            int[] room = available.poll();
            int start = Math.max(m[0], room[1]);
            room[1] = start + m[1] - m[0];
            inUse.add(room);
            count[room[0]]++;
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

    // 300. Longest Increasing Subsequence
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


    // 76. Minimum Window Substring
    // https://leetcode.com/problems/minimum-window-substring
    // move left & right pointers; keep found count & dictionaries on S & T counts up-to-data
    public static String minWindow(String s, String t) {
        Map<Character, Integer> dictT = new HashMap<>();
        for (char c : t.toCharArray()) {
            dictT.put(c, dictT.getOrDefault(c, 0) + 1);
        }

        String minWindow = null;
        int left = 0;
        int right = 0;
        int found = 0;
        Map<Character, Integer> dictS = new HashMap<>();

        while (right < s.length()) {
            char c = s.charAt(right);

            if (!dictT.containsKey(c)) {
                right++;
                continue;
            }

            int count = dictS.getOrDefault(c, 0) + 1;
            dictS.put(c, count);

            if (count == dictT.get(c)) found++;

            while (left <= right && found == dictT.size()) {
                char c2 = s.charAt(left);

                if (!dictT.containsKey(c2)) {
                    left++;
                    continue;
                }

                if (minWindow == null || minWindow.length() > right - left + 1) {
                    minWindow = s.substring(left, right + 1);
                }

                int count2 = dictS.getOrDefault(c2, 0) - 1;
                dictS.put(c2, count2);

                if (count2 < dictT.get(c2)) found--;

                left++;
            }

            right++;
        }

        return minWindow != null ? minWindow : "";
    }


    // 443. String Compression
    // https://leetcode.com/problems/string-compression
    public static int compressString(char[] chars) {
        var sb = new StringBuilder();
        int count = 1;
        char lastChar = chars[0];
        for (int i = 1; i < chars.length; i++) {
            if (chars[i] == lastChar) {
                count++;
            } else {
                sb.append(lastChar).append(count);
                lastChar = chars[i];
                count = 1;
            }
        }
        sb.append(lastChar).append(count);
        for (int i = 0; i < sb.length(); i++) {
            chars[i] = sb.charAt(i);
        }
        return sb.length();
    }


    // 38. Count and Say
    // https://leetcode.com/problems/count-and-say
    public static String countAndSay(int n) {
        if (n == 1) return "1";

        String base = countAndSay(n - 1);
        var result = new StringBuilder();
        char current = base.charAt(0);
        int count = 1;

        for (int i = 1; i < base.length(); i++) {
            char c = base.charAt(i);
            if (c == current) {
                count++;
            } else {
                result.append(count).append(current);
                current = c;
                count = 1;
            }
        }
        result.append(count).append(current);
        return result.toString();
    }

    // simplifyPath
    // 71. Simplify Path
    // https://leetcode.com/problems/simplify-path/ (medium) [string, stack]
    public static String simplifyPath(String path) {
        final String CURRENT_DIR = ".";
        final String PARENT_DIR = "..";

        String[] originalElements = path.split("/");
        List<String> elements = new ArrayList<>();

        for (String e : originalElements) {
            switch (e) {
                case "":
                    // skip multiple '/'
                    break;
                case CURRENT_DIR:
                    // skip multiple '.'
                    break;
                case PARENT_DIR:
                    if (!elements.isEmpty()) elements.remove(elements.size() - 1);
                    break;
                default:
                    elements.add(e);
            }
        }

        return "/" + String.join("/", elements);
    }

    // 1249. Minimum Remove to Make Valid Parentheses
    // https://leetcode.com/problems/minimum-remove-to-make-valid-parentheses
    public static String minRemoveToMakeValid(String s) {
        StringBuilder sb = minRemoveToMakeValidRemoveInvalidClosing(s, '(', ')');
        sb = minRemoveToMakeValidRemoveInvalidClosing(sb.reverse().toString(), ')', '(');
        return sb.reverse().toString();
    }

    private static StringBuilder minRemoveToMakeValidRemoveInvalidClosing(String s, char open, char close) {
        StringBuilder sb = new StringBuilder();
        int balance = 0;
        for (char c : s.toCharArray()) {
            if (c == open) {
                balance++;
            } else if (c == close) {
                if (balance == 0) continue;
                balance--;
            }
            sb.append(c);
        }
        return sb;
    }

    public static int findKthLargest(int[] nums, int k) {
        PriorityQueue<Integer> q = new PriorityQueue<>();
        for (int i = 0; i < nums.length; i++) {
            q.add(nums[i]);
            if (q.size() > k) q.poll();
        }
        return q.peek();
    }

    // 560. Subarray Sum Equals K
    // https://leetcode.com/problems/subarray-sum-equals-k
    public static int subarraySum(int[] nums, int k) {
        int count = 0;
        int sum = 0;

        // sum -> count
        Map<Integer, Integer> map = new HashMap<>();
        map.put(0, 1);

        for (int i = 0; i < nums.length; i++) {
            sum += nums[i];
            count += map.getOrDefault(sum - k, 0);
            map.put(sum, map.getOrDefault(sum, 0) + 1);
        }
        return count;
    }

    // 56. Merge Intervals
    // https://leetcode.com/problems/merge-intervals
    public static int[][] mergeIntervals(int[][] intervals) {
        Arrays.sort(intervals, (a, b) -> a[0] - b[0]);
        List<int[]> result = new ArrayList<>();
        int[] current = intervals[0];
        result.add(current);
        for (int i = 1; i < intervals.length; i++) {
            if (current[1] >= intervals[i][0]) {
                current[1] = Math.max(current[1], intervals[i][1]);
            } else {
                current = intervals[i];
                result.add(current);
            }
        }
        return result.toArray(new int[result.size()][]);
    }

    private static void swap(int[] arr, int i, int j) {
        int temp = arr[i];
        arr[i] = arr[j];
        arr[j] = temp;
    }

    // dvdf
    // longestSubstring = 3
    // start = 1
    // map = {v,1} {d,2}
    // i = 3
    public static int lengthOfLongestSubstring(String s) {
        if (s.length() == 0) return 0;

        int longestSubstring = 1;
        Map<Character, Integer> map = new HashMap<>();
        int start = 0;
        map.put(s.charAt(0), 0);

        for (int i = 1; i < s.length(); i++) {
            char c = s.charAt(i);
            if (map.containsKey(c)) {
                int j = map.remove(c);
                if (j >= start) start = j + 1;
            }
            longestSubstring = Math.max(longestSubstring, i - start + 1);
            map.put(c, i);
        }
        return longestSubstring;
    }

    // done
    // https://leetcode.com/problems/valid-number/ (hard) [string, dfa]
    // https://leetcode.com/problems/nested-list-weight-sum/ (medium)
}
