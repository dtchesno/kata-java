package com.dtchesno.kata.bitops;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

public class Tasks {

    public static int replace(int n, int m, int i, int j) {
        int mask = (2 << (j - i)) - 1;
        m &= mask;
        m <<= i;
        n &= ~(mask << i);
        return n | m;
    }

    public static String printBinary(String n) {
        int decPos = n.indexOf('.');
        int intPart = Integer.parseInt(n.substring(0, decPos ));
        double decPart = Double.parseDouble(n.substring(decPos , n.length()));

        // have to be reversed at the end
        StringBuffer intString = new StringBuffer();
        while (intPart > 0) {
            intString.append(intPart & 1);
            intPart >>= 1;
        }

        StringBuffer decString = new StringBuffer();
        while (decPart > 0) {
            if (decString.length() > 32) {
                return "ERROR";
            }
            decPart *= 2;
            if (decPart >= 1 ) {
                decString.append('1');
                decPart -= 1;
            } else {
                decString.append('0');
            }
        }

        String i = intString.reverse().toString();
        String d = decString.toString();
        return (i.length() > 0 ? i : "0") + '.' + (d.length() > 0 ? d : "0");
    }

    public static int[] findNeighbors(int value) {
        if (value <= 1) {
            return null;
        }

        // find smaller

        // find 1st 1 we could flip to 0 with any 0 on right we could flip to 1
        int i = 0;
        while (getBit(value, i) != 0) {
            i++;
        }
        int j = i + 1;
        while (getBit(value, j) != 1) {
            j++;
        }

        // flip 1 & 0
        int little = setBit(value, j, 0);
        little = setBit(little, j - 1, 1);

        // move all 1 in [0, i) till [j - 2], inclusive; j - 2 - (i - 1) = j - i - 1
        int hi = little & (~0 << (j - 1));
        int lo = (little & ((1 << i) - 1)) << (j - i - 1);
        little = hi | lo;

        // find larger

        // find 1st 0 we could flip to 1 with any 1 on right we could flip to 0
        i = 0;
        while (getBit(value, i) != 1) {
            i++;
        }
        j = i + 1;
        while (getBit(value, j) != 0) {
            j++;
        }

        // flip 1 & 0
        int large = setBit(value, j, 1);
        large = setBit(large, j - 1, 0);

        // move all starting from [j - 2] to right (i - 1) times
        hi = large & (~0 << (j - 1));
        lo = (large & ((1 << (j - 1)) - 1)) >> i;
        large = hi | lo;

        return new int[] {little, large};
    }

    private static int getBit(int value, int i) {
        return (value & (1 << i)) > 0 ? 1 : 0;
    }

    private static int setBit(int value, int i, int bit) {
        if (bit == 0) {
            return value & ~(1 << i);
        } else {
            return value | (1 << i);
        }
    }

    public static int swapNeighbors(int value) {
        return ((value & 0xaaaa) >> 1) | ((value & 0x5555) << 1);
    }

    public static int findMissingElement(int[] array) {
        ArrayList<Integer> indices = new ArrayList<>(array.length);
        for (int i = 0; i < array.length; i++) {
            indices.add(i);
        }
        return findMissing(array, indices, 0);
    }

    private static int findMissing(int[] array, ArrayList<Integer> indices, int bit) {
        if (indices.size() == 0) {
            return 0;
        }

        ArrayList<Integer> indicesOf0 = new ArrayList<>();
        ArrayList<Integer> indicesOf1 = new ArrayList<>();

        for (int i : indices) {
            if (fetchBit(array, i, bit) == 0) {
                indicesOf0.add(i);
            } else {
                indicesOf1.add(i);
            }
        }
        if (indicesOf0.size() > indicesOf1.size()) {
            return findMissing(array, indicesOf1, bit + 1) << 1 | 1;
        } else {
            return findMissing(array, indicesOf0, bit + 1) << 1;
        }
    }

    private static int fetchBit(int[] array, int index, int bit) {
        return (array[index] & (1 << bit)) > 0 ? 1 : 0;
    }
}
