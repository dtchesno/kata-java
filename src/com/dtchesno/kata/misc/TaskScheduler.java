package com.dtchesno.kata.misc;

import java.util.*;

public class TaskScheduler {

    private static class Element {
        char value;
        int count;
        int lastPos;

        Element(char value, int count, int pos) {
            this.value = value;
            this.count = count;
            this.lastPos = pos;
        }
    }

    // Given a characters array tasks, representing the tasks a CPU needs to do, where each letter represents a different task.
    // Tasks could be done in any order. Each task is done in one unit of time. For each unit of time,
    // the CPU could complete either one task or just be idle.
    // However, there is a non-negative integer n that represents the cooldown period between two same tasks
    // (the same letter in the array), that is that there must be at least n units of time between any two same tasks.
    // Return the least number of units of times that the CPU will take to finish all the given tasks.
    //
    // https://leetcode.com/problems/task-scheduler/, #621
    // Med - facebook
    public static int leastInterval(char[] tasks, int n) {
        return leastIntervalMath(tasks, n);
    }

    // match solution based on most frequent tasks and intervals
    private static int leastIntervalMath(char[] tasks, int n) {
        int fmax = 0;
        int[] freq = new int['Z' - 'A' + 1];
        for (int t : tasks) {
            freq[t - 'A']++;
            fmax = Math.max(fmax, freq[t - 'A']);
        }

        int nmax = 0;
        for (int f : freq) {
            if (f == fmax) {
                nmax++;
            }
        }

        return Math.max(tasks.length, (fmax - 1) * (n + 1) + nmax);
    }

    private static int leastIntervalBruteForce(char[] tasks, int n) {
        // using map will slow things down while populating
        Element[] elements = new Element['Z' - 'A' + 1];
        for (char ch : tasks) {
            Element e = elements[ch -'A'];
            if (e == null) {
                e = new Element(ch, 0, -(n + 1));
                elements[ch -'A'] = e;
            }
            e.count++;
        }

        int count = tasks.length;
        int position = 0;
        while (count != 0) {
            int maxCount = 0;
            int maxCountIndex = Integer.MAX_VALUE;
            for (int i = 'A'; i <= 'Z'; i++) {
                Element e = elements[i - 'A'];
                if (e == null || position - e.lastPos <= n) {
                    continue;
                }
                if (e.count > maxCount) {
                    maxCount = e.count;
                    maxCountIndex = i;
                }
            }
            if (maxCount == 0) {
                position++;
                continue;
            }
            Element e = elements[maxCountIndex - 'A'];
            position = Math.max(position, e.lastPos + (n + 1));
            e.lastPos = position;
            e.count--;
            if (e.count == 0) {
                elements[maxCountIndex - 'A'] = null;
            }
            position++;
            count--;
        }
        return position;
    }

    // Given a non-empty string s and an integer k, rearrange the string such that the same characters are at least distance k from each other.
    // All input strings are given in lowercase letters. If it is not possible to rearrange the string, return an empty string "".
    //
    // https://leetcode.com/problems/rearrange-string-k-distance-apart/, #532
    // Hard - microsoft
    public static String rearrangeString(String input, int k) {
        Element[] chars = new Element['z' - 'a' + 1];
        for (char ch : input.toCharArray()) {
            if (chars[ch - 'a'] == null) {
                chars[ch - 'a'] = new Element(ch, 0, -k);
            }
            chars[ch - 'a'].count++;
        }

        TreeSet<Element> sorted = new TreeSet<Element>((a, b) -> {
           if (a.count != b.count) {
               return b.count - a.count;
           }
           if (a.lastPos != b.lastPos) {
               return a.lastPos - b.lastPos;
           }
           return a.value - b.value;
        });

        for (Element e : chars) {
            if (e != null) {
                sorted.add(e);
            }
        }

        char[] result = new char[input.length()];
        for (int i = 0; i < input.length(); i++) {
            Iterator<Element> it = sorted.iterator();
            while (it.hasNext()) {
                Element e = it.next();
                if (e.lastPos + k <= i) {
                    result[i] = e.value;
                    e.lastPos = i;
                    e.count--;
                    it.remove();
                    if (e.count > 0) {
                        sorted.add(e);
                    }
                    break;
                }
            }
        }

        return sorted.isEmpty() ? new String(result) : "";
    }
}
