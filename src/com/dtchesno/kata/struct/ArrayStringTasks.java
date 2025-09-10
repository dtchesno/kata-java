package com.dtchesno.kata.struct;

import org.apache.commons.lang3.tuple.Pair;
import org.apache.commons.lang3.tuple.Triple;

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


    // zero sum subarray
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
//    public static double findMedianSortedArrays(int[] nums1, int[] nums2) {
//        int iLeft = (nums1.length + nums2.length) / 2 - 1;
//        int left = -1;
//        int i = 0;
//        int j = 0;
//
//        while (i + j <= iLeft) {
//            if (i == nums1.length) {
//                left = nums2[j++];
//            } else if (j == nums2.length) {
//                left = nums1[i++];
//            } else if (nums1[i] < nums2[j]) {
//                left = nums1[i++];
//            } else {
//                left = nums2[j++];
//            }
//        }
//
//        int right = -1;
//
//        if (i == nums1.length) {
//            right = nums2[j++];
//        } else if (j == nums2.length) {
//            right = nums1[i++];
//        } else if (nums1[i] < nums2[j]) {
//            right = nums1[i++];
//        } else {
//            right = nums2[j++];
//        }
//
//        return ((nums1.length + nums2.length) % 2 == 0) ? ((double) left + right) / 2 : right;
//    }

    public static double findMedianSortedArrays(int[] nums1, int[] nums2) {
        if (nums1.length > nums2.length) return findMedianSortedArrays(nums2, nums1);

        int leftPartitionSize = (nums1.length + nums2.length + 1) / 2;
        int low = 0;
        int high = nums1.length;

        while (low <= high) {
            // par1 & part2 is where right parts start
            // since part1 & part2 are left partitions size for nums1 & nums2
            int part1 = (low + high) / 2;
            int part2 = leftPartitionSize - part1;

            int maxLeft1 = (part1 == 0) ? Integer.MIN_VALUE : nums1[part1 - 1];
            int minRight1 = (part1 == nums1.length) ? Integer.MAX_VALUE : nums1[part1];

            int maxLeft2 = (part2 == 0) ? Integer.MIN_VALUE : nums2[part2 - 1];
            int minRight2 = (part2 == nums2.length) ? Integer.MAX_VALUE : nums2[part2];

            if (maxLeft1 <= minRight2 && maxLeft2 <= minRight1) {
                if ((nums1.length + nums2.length) % 2 == 0) {
                    int left = Math.max(maxLeft1, maxLeft2);
                    int right = Math.min(minRight1, minRight2);
                    return (double)(left + right) / 2;
                } else {
                    return Math.max(maxLeft1, maxLeft2);
                }
            }

            if (maxLeft1 > minRight2) {
                high = part1 - 1;
            } else {
                // we do +1, since nums1[part1] is already evaluated in exit condition
                // here maxLeft1 <= minRight2
                // which means minRight1 > maxLeft2, so, part1 is not good for minRight1
                low = part1 + 1;
            }
        }
        return -1;
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
    // Input: nums = [1,3,-1,-3,5,3,6,7], k = 3
    //Output: [3,3,5,5,6,7]
    //Explanation:
    //Window position                Max
    //---------------               -----
    //[1  3  -1] -3  5  3  6  7       3
    // 1 [3  -1  -3] 5  3  6  7       3
    // 1  3 [-1  -3  5] 3  6  7       5
    // 1  3  -1 [-3  5  3] 6  7       5
    // 1  3  -1  -3 [5  3  6] 7       6
    // 1  3  -1  -3  5 [3  6  7]      7



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
                longestIncreasingSubseqInsert(seq[i], result);
            }
        }
        return result.size();
    }

    private static void longestIncreasingSubseqInsert(int val, List<Integer> result) {
        int left = 0;
        int right = result.size();
        while (left <= right) {
            int mid = (left + right) / 2;
            if (val == result.get(mid)) {
                return;
            } else if (val > result.get(mid)) {
                left = mid + 1;
            } else {
                right = mid - 1;
            }
        }
        result.set(left, val);
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
        int[] current = new int[] {intervals[0][0], intervals[0][1]};
        result.add(current);
        for (int i = 1; i < intervals.length; i++) {
            if (current[1] >= intervals[i][0]) {
                current[1] = Math.max(current[1], intervals[i][1]);
            } else {
                current = new int[] {intervals[i][0], intervals[i][1]};
                result.add(current);
            }
        }
        return result.toArray(new int[0][0]);
    }


    private static void swap(int[] arr, int i, int j) {
        int temp = arr[i];
        arr[i] = arr[j];
        arr[j] = temp;
    }

    // 3. Longest Substring Without Repeating Characters
    // https://leetcode.com/problems/longest-substring-without-repeating-characters
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


    // 524. Longest Word in Dictionary through Deleting
    // https://leetcode.com/problems/longest-word-in-dictionary-through-deleting
    // https://www.google.com/about/careers/applications/candidate-prep/swe?utm_source=email&utm_medium=recruiter-email&utm_campaign=external-resource&utm_content=cx
    public static String findLongestWord(String s, List<String> words) {
        String maxWord = "";
        for ( String w : words) {
            if (!findLongestWordIsMatch(s, w)) continue;
            if (w.length() > maxWord.length()) {
                maxWord = w;
            } else if (w.length() == maxWord.length() && w.compareTo(maxWord) <= 0) {
                maxWord = w;
            }
        }
        return maxWord;
    }

    private static boolean findLongestWordIsMatch(String s, String w) {
        int j = 0;
        for (int i = 0; i < s.length(); i++) {
            if (j == w.length()) return true;
            if (s.charAt(i) == w.charAt(j)) j++;
        }
        return j == w.length();
    }


    // 394. Decode String
    // https://leetcode.com/problems/decode-string
    public static String decodeString(String s) {
        Queue<Character> q = new LinkedList<>();
        for (char c : s.toCharArray()) {
            q.add(c);
        }
        return decodeString(q);
    }

    private static String decodeString(Queue<Character> q) {
        StringBuilder sb = new StringBuilder();
        int count = 0;

        while (!q.isEmpty()) {
            char c = q.poll();

            if (c == '[') {
                String nested = decodeString(q);
                sb.append(nested.repeat(count));
                count = 0;
            } else if (c == ']') {
                return sb.toString();
            } else if (Character.isDigit(c)) {
                count = count * 10 + c - '0';
            } else {
                sb.append(c);
            }
        }
        return sb.toString();
    }


    // 529. Minesweeper
    // https://leetcode.com/problems/minesweeper
    public static char[][] minesweeper(char[][] board, int[] click) {
        if (board[click[0]][click[1]] == 'M') {
            board[click[0]][click[1]] = 'X';
            return board;
        }
        reveal(board, click[0], click[1]);
        return board;
    }

    private static void reveal(char[][] board, int row, int col) {
        final int[][] directions = new int[][] {
                {-1,-1}, {-1,0}, {-1,1},
                {0,-1}, {0,1},
                {1,-1}, {1,0}, {1,1},
        };

        Queue<Pair<Integer, Integer>> q = new LinkedList<>();
        Pair<Integer, Integer> start = Pair.of(row, col);
        q.add(start);
        Set<Pair<Integer, Integer>> seen = new HashSet<>();
        seen.add(start);

        while (!q.isEmpty()) {
            Pair<Integer, Integer> top = q.poll();

            int count = 0;
            List<Pair<Integer, Integer>> candidates = new ArrayList<>();
            for (int[] dir : directions) {
                int i = top.getKey() + dir[0];
                int j = top.getValue() + dir[1];
                Pair<Integer, Integer> next = Pair.of(i, j);

                if (i < 0 || i == board.length || j < 0 || j == board[0].length || seen.contains(next)) continue;

                if (board[i][j] == 'M') {
                    count++;
                    // q.add(null);
                    continue;
                }
                candidates.add(next);
            }
            board[top.getKey()][top.getValue()] = (count == 0) ? 'B' : (char)('0' + count);
            if (count == 0) {
                q.addAll(candidates);
                seen.addAll(candidates);
            }
        }
    }


    // 273. Integer to English Words
    // https://leetcode.com/problems/integer-to-english-words
    // Input: num = 1234567
    // Output: "One Million Two Hundred Thirty Four Thousand Five Hundred Sixty Seven"
    public static String numberToWords(int num) {
        Map<Integer, String> numberMap = new HashMap<>();
        numberMap.put(90, "Ninety");
        numberMap.put(80, "Eighty");
        numberMap.put(70, "Seventy");
        numberMap.put(60, "Sixty");
        numberMap.put(50, "Fifty");
        numberMap.put(40, "Forty");
        numberMap.put(30, "Thirty");
        numberMap.put(20, "Twenty");
        numberMap.put(19, "Nineteen");
        numberMap.put(18, "Eighteen");
        numberMap.put(17, "Seventeen");
        numberMap.put(16, "Sixteen");
        numberMap.put(15, "Fifteen");
        numberMap.put(14, "Fourteen");
        numberMap.put(13, "Thirteen");
        numberMap.put(12, "Twelve");
        numberMap.put(11, "Eleven");
        numberMap.put(10, "Ten");
        numberMap.put(9, "Nine");
        numberMap.put(8, "Eight");
        numberMap.put(7, "Seven");
        numberMap.put(6, "Six");
        numberMap.put(5, "Five");
        numberMap.put(4, "Four");
        numberMap.put(3, "Three");
        numberMap.put(2, "Two");
        numberMap.put(1, "One");

        if (num == 0) return "Zero";

        String numbers = numberToWords(num % 1000, "", numberMap);
        num /= 1000;

        String thousands = numberToWords(num % 1000, "Thousand", numberMap);
        num /= 1000;

        String millions = numberToWords(num % 1000, "Million", numberMap);
        num /= 1000;

        String billions = numberToWords(num % 1000, "Billion", numberMap);

        List<String> result = new ArrayList<>();
        if (billions.length() > 0 ) result.add(billions);
        if (millions.length() > 0 ) result.add(millions);
        if (thousands.length() > 0 ) result.add(thousands);
        if (numbers.length() > 0 ) result.add(numbers);
        return String.join(" ", result);
    }

    public static String numberToWords(int num, String label, Map<Integer, String> map) {
        StringBuilder sb = new StringBuilder();

        int hundreds = num / 100;
        if (hundreds > 0) {
            sb.append(map.get(hundreds)).append(' ').append("Hundred").append(' ');
        }
        num %= 100;

        for (int val : map.keySet()) {
            if (num < val) continue;
            sb.append(map.get(val)).append(' ');
            num -= val;
        }
        return sb.length() == 0 ? "" : (sb.toString() + label).trim();
    }

    // 1152. Analyze User Website Visit Pattern
    // https://leetcode.com/problems/analyze-user-website-visit-pattern/
    public static List<String> mostVisitedPattern(String[] username, int[] timestamp, String[] website) {
        // collect and sort by time all visits
        List<Triple<String, Integer, String>> visits = new ArrayList<>();
        for (int i = 0; i < username.length; i++) {
            visits.add(Triple.of(username[i], timestamp[i], website[i]));
        }
        Collections.sort(visits, (a, b) -> a.getMiddle() - b.getMiddle());

        // build map user to list of website visits
        // user -> <website>
        Map<String, List<String>> userVisits = new HashMap<>();
        for (Triple<String, Integer, String> visit : visits) {
            userVisits.computeIfAbsent(visit.getLeft(), k -> new ArrayList<>()).add(visit.getRight());
        }

        // build map of triple to count
        // use TreeMap to return lex smallest id scores are the same
        TreeMap<Triple<String, String, String>, Integer> counts = new TreeMap<>();
        Set<Pair<String, Triple<String, String, String>>> seen = new HashSet<>();
        for (String user : username) {
            List<String> websites = userVisits.get(user);
            for (int i = 0; i < websites.size() - 2; i++) {
                for (int j = i + 1; j < websites.size() - 1; j++) {
                    for (int k = j + 1; k < websites.size(); k++) {
                        String first = websites.get(i);
                        String second = websites.get(j);
                        String third = websites.get(k);
                        Triple<String, String, String> triple = Triple.of(first, second, third);
                        if (seen.contains(Pair.of(user, triple))) continue;
                        int count = counts.getOrDefault(triple, 0);
                        counts.put(triple, count + 1);
                        seen.add((Pair.of(user, triple)));
                    }
                }
            }
        }
        Map.Entry<Triple<String, String, String>, Integer> mostVisited = null;
        for (var entry : counts.entrySet()) {
            if (mostVisited == null || mostVisited.getValue() < entry.getValue()) mostVisited = entry;
        }
        return List.of(mostVisited.getKey().getLeft(), mostVisited.getKey().getMiddle(), mostVisited.getKey().getRight());
    }

    // 127. Word Ladder
    // https://leetcode.com/problems/word-ladder
    public static int ladderLength(String beginWord, String endWord, List<String> wordList) {
        Map<String, List<String>> patterns = new HashMap<>();
        for (String w : wordList) {
            for (int i = 0; i < w.length(); i++) {
                String key = w.substring(0, i) + "*" + w.substring(i + 1);
                patterns.computeIfAbsent(key, k -> new ArrayList<>()).add(w);
            }
        }

        Queue<String> q = new LinkedList<>();
        Set<String> seen = new HashSet<>();
        q.add(beginWord);
        seen.add(beginWord);
        q.add(null);
        int moves = 1;

        while (!q.isEmpty()) {
            String top = q.poll();

            if (top == null) continue;

            if (top.equals(endWord)) return moves;

            for (int i = 0; i < top.length(); i++) {
                String key = top.substring(0, i) + "*" + top.substring(i + 1);
                List<String> candidates = patterns.get(key);

                if (candidates == null) continue;

                for (String candidate : candidates) {
                    if (seen.contains(candidate)) continue;
                    q.add(candidate);
                    seen.add(candidate);
                }
            }

            if (q.peek() == null) {
                moves++;
                q.add(null);
            }
        }

        return 0;
    }

    // 1291. Sequential Digits
    // https://leetcode.com/problems/sequential-digits/
    public static List<Integer> sequentialDigits(int low, int high) {
        String digits = "123456789";
        int lowLen = String.valueOf(low).length();
        int highLen = String.valueOf(high).length();

        List<Integer> result = new ArrayList<>();
        for (int len = lowLen; len <= highLen; len++) {
            for (int i = 0; i <= digits.length() - len; i++) {
                int num = Integer.parseInt(digits.substring(i, i + len));
                if (num >= low && num <= high) {
                    result.add(num);
                }
            }
        }
        return result;
    }


    // 2340. Minimum Adjacent Swaps to Make a Valid Array
    // https://leetcode.com/problems/minimum-adjacent-swaps-to-make-a-valid-array
    public static int minimumSwaps(int[] nums) {
        int iMin = 0;
        int iMax = 0;

        for (int i = 1; i < nums.length; i++) {
            if (nums[i] < nums[iMin]) iMin = i;
            if (nums[i] >= nums[iMax]) iMax = i;
        }

        return iMin + (nums.length - 1 - iMax) - (iMin > iMax ? 1 : 0);
    }


    // 2268. Minimum Number of Keypresses
    // https://leetcode.com/problems/minimum-number-of-keypresses
    public static int minimumKeypresses(String s) {
        int[] freq = new int['z' - 'a' + 1];
        for (char c : s.toCharArray()) {
            freq[c - 'a']++;
        }

        PriorityQueue<Integer> q = new PriorityQueue<>((a, b) -> b - a);
        for (int f : freq) {
            if (f > 0) q.add(f);
        }

        int count = 0;
        int totalLetters = 0;
        while (!q.isEmpty()) {
            int multiplier = (totalLetters / 9) + 1;
            count += multiplier * q.poll();
            totalLetters++;
        }
        return count;
    }


    // 2222. Number of Ways to Select Buildings
    // https://leetcode.com/problems/number-of-ways-to-select-buildings
    // select 010, or 101, not required to be consecutive
    public static long numberOfWays(String s) {
        int count0 = 0;
        int count01 = 0;
        int count010 = 0;
        int count1 = 0;
        int count10 = 0;
        int count101 = 0;

        for (char c : s.toCharArray()) {
            if (c == '0') {
                count0++;
                count10 += count1;
                count010 += count01;
            } else {
                count1++;
                count01 += count0;
                count101 += count10;
            }
        }
        return count010 + count101;
    }

    // done
    // https://leetcode.com/problems/valid-number/ (hard) [string, dfa]
    // https://leetcode.com/problems/nested-list-weight-sum/ (medium)
}
