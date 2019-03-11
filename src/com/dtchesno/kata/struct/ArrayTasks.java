package com.dtchesno.kata.struct;

public class ArrayTasks {

    // https://www.programcreek.com/2015/03/rotate-array-in-java/
    // {1, 2, 3, 4, 5, 6, 7}, 4 -> {4, 5, 6, 7, 1, 2, 3}
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
}
