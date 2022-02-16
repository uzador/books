package com.example.staff.ti.array;

public class Unique {

    public boolean isUnique(String str) {
        if (str.length() > 128) {
            return false;
        }

        boolean[] abc = new boolean[128];
        for (char c : str.toCharArray()) {
            if (abc[c]) {
                return false;
            }

            abc[c] = true;
        }

        return true;
    }

    public boolean isUnique2(String str) {
        if (str.length() > 128) {
            return false;
        }

        for (int i = 0; i < str.length(); i++) {
            for(int j = i + 1; j < str.length(); j++) {
                if (str.charAt(i) == str.charAt(j)) {
                    return false;
                }
            }
        }

        return true;
    }
}
