package com.example.staff.ti.array;

import java.util.Arrays;

public class Permutation {
    public boolean isPermutation(String a, String b) {
        if (a == b) {
            return true;
        }

        char[] aAsArray = a.toCharArray();
        char[] bAsArray = b.toCharArray();
        Arrays.sort(aAsArray);
        Arrays.sort(bAsArray);

        return Arrays.equals(aAsArray, bAsArray);
    }
}
