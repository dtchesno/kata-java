package com.dtchesno.kata.bitops;

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
        // TODO
        // find smaller
        // find larger
        return new int[0];
    }
}
