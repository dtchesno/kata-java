package com.dtchesno.kata.test;

import java.util.List;

public class AssertExtensions {
    public static <T> boolean areEqual(List<T> l1, List<T> l2) {
        if (l1.size() != l2.size())
            return false;

        for (int i = 0; i < l1.size(); i++) {
            if (!l1.get(i).equals(l2.get(i))) {
                return false;
            }
        }

        return true;
    }

    public static <T> boolean areEqual(T[] arr, List<T> l) {
        if (arr.length != l.size())
            return false;

        for (int i = 0; i < arr.length; i++) {
            if (!l.get(i).equals(arr[i])) {
                return false;
            }
        }

        return true;
    }
}
