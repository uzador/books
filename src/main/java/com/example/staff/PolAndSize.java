package com.example.staff;

public class PolAndSize {
    public static void main(String[] args) {
        int x = 1221;
        System.out.println(isPolindrom(x));
        System.out.println(getIntSize(x));
    }

    private static int getIntSize(int x) {
        int length = 0;
        int tmp = 1;
        while (tmp <= x) {
            length++;
            tmp *= 10;
        }

        return length;
    }

    private static boolean isPolindrom(int x) {
        String xAsString = String.valueOf(x);
        int j = xAsString.length();
        for (int i = 0; i < xAsString.length() / 2; i++) {
            if (xAsString.charAt(i) != xAsString.charAt(--j)) {
                return false;
            }
        }

        return true;
    }
}
