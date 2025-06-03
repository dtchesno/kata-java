package com.dtchesno.kata.bitops;

public class BitsSolution {

    // replace bits i..j in <n> by bits from <m>
    // Cracking... 5.1 pg.133
    // [selected - 1]
    // Input: N=10000000000,M=10011,i=2,j=6 Output:N = 10001001100
    //
    // j=3, i=2
    // 10
    // 10 + 1 = 11
    // 11 - 1 = 10
    public static int replace(int n, int m, int i, int j) {
        int mask = (1 << (j - i + 1)) - 1;

        // this is what we want to apply as patch
        m &= mask;
        m <<= i;

        // clear patch space
        n &= ~(mask << i);

        // now 'm' will only apply to cleared patch space
        return n | m;
    }

    // print decimal string in binary format; e.g. 3.5 -> 11.1 (2 + 1 + 1/2)
    // Cracking... 5.2 pg.133
    public static String printBinary(String n) {
        int dotPos = n.indexOf('.');
        int intPart = Integer.parseInt(n.substring(0, dotPos));
        double doublePart = Double.parseDouble(n.substring(dotPos));

        StringBuilder intSB = new StringBuilder();
        while (intPart > 0) {
            intSB.append(intPart & 1);
            intPart >>>= 1;
        }

        StringBuilder doubleSB = new StringBuilder();
        while (doublePart > 0) {
            doublePart *= 2;
            if (doublePart >= 1) {
                doubleSB.append('1');
                doublePart -= 1;
            } else {
                doubleSB.append('0');
            }
            if (doubleSB.length() > 32) return "ERROR";
        }

        String left = intSB.length() > 0 ? intSB.reverse().toString() : "0";
        String right = doubleSB.length() > 0 ? doubleSB.toString() : "0";
        return left + "." + right;
    }

    // return prev & next numbers with same number of 1 bits
    // Cracking... 5.3 pg.133; similar to Aziz 5.4 pg.50
    // [selected - 3]
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

    // swap even & odd bits
    public static int swapNeighbors(int value) {
        return ((value & 0xaaaa) >> 1) | ((value & 0x5555) << 1);
    }

    // compute parity: 0 - even, 1 - odd
    // Aziz 5.1 pg45
    public static int parity(long x) {
        int result = 0;
        while (x != 0) {
            result ^= 1;
            x &= (x - 1); // drops last 1
        }
        return result;
    }

    public static int findMissingElement(int[] array) {
        int missing = 0;
        for (int i = 0; i < array.length; i++) {
            missing ^= i;
            missing ^= array[i];
        }
        return missing ^ array.length;
    }

    // Aziz 12.10 pg 203
    public static int[] findMissAndDupElements(int[] array) {
        int[] result = new int[] { -1, -1 }; // [missing, duplicate]
        int missXorDup = 0;
        for (int i = 0; i < array.length; i++) {
            missXorDup ^= i;
            missXorDup ^= array[i];
        }
        int differBit = missXorDup & (~(missXorDup - 1));

        int missOrDup = 0;
        for (int i = 0; i < array.length; i++) {
            if ((i & differBit) != 0) {
                missOrDup ^= i;
            }
            if ((array[i] & differBit) != 0) {
                missOrDup ^= array[i];
            }
        }
        for (int i = 0; i < array.length; i++) {
            if (array[i] == missOrDup) {
                result[1] = missOrDup;
                result[0] = missXorDup ^ result[1];
                break;
            }
        }
        if (result[1] == -1) {
            result[0] = missOrDup;
            result[1] = missXorDup ^ result[0];
        }
        return result;
    }

    // multiply w/o arithmetic ops
    // Aziz 5.5 pg.51
    public static int multiply(int x, int y) {
        int product = 0;
        while (y != 0) {
            if ((y & 1) != 0) {
                product = add(product, x);
            }
            y >>>= 1;
            x <<= 1;
        }
        return product;
    }


    // sum w/o arithmetic ops
    // Aziz 5.5 pg.51 + byte-by-byte
    public static int add(int x, int y) {
        int carry = 0;
        do {
            int sum = x ^ y;
            carry = (x & y) << 1;
            x = sum;
            y = carry;
        } while (carry != 0);
        return x;
    }


    // rotate number to right
    // byte-by-byte #36 pg32
    public static int rotate(int value, int n) {
        n %= 32;
        int mask = (1 << n) - 1;
        int lower = value & mask;
        int upper = value & (~mask);
        return (lower << (32 - n)) | (upper >>> n);
    }


    /**
     * COMMON BIT TASKS!!!
     */

    public static boolean getBitB(int num, int i) {
        return (num & (1 << i)) != 0;
    }

    public static int setBit(int num, int i) {
        return num | (1 << i);
    }

    public static int clearBit(int num, int i) {
        int mask = ~(1 << i);
        return num & mask;
    }

    public static int toggleBit(int num, int i) {
        int mask = (1 << i);
        return num ^ mask;
    }

    // i - inclusive
    public int clearBitsHigh(int num, int i) {
        int mask = (1 << i) - 1;
        return num & mask;
    }

    // i - inclusive
    public int clearBitsLow(int num, int i) {
        int mask = ~((1 << (i + 1)) - 1);
        return num & mask;
    }
}
