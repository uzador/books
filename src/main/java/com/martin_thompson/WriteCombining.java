package com.martin_thompson;

import static java.lang.System.out;

public final class WriteCombining
{
    private static final int ITERATIONS = Integer.MAX_VALUE;
    private static final int ITEMS = 1 << 24;
    private static final int MASK = ITEMS - 1;

    private static final byte[] arrayA = new byte[ITEMS];
    private static final byte[] arrayB = new byte[ITEMS];
    private static final byte[] arrayC = new byte[ITEMS];
    private static final byte[] arrayD = new byte[ITEMS];
    private static final byte[] arrayE = new byte[ITEMS];
    private static final byte[] arrayF = new byte[ITEMS];
    private static final byte[] arrayG = new byte[ITEMS];
    private static final byte[] arrayH = new byte[ITEMS];
    private static final byte[] arrayI = new byte[ITEMS];
    private static final byte[] arrayJ = new byte[ITEMS];
    private static final byte[] arrayK = new byte[ITEMS];
    private static final byte[] arrayL = new byte[ITEMS];

    public static void main(final String[] args)
    {
        for (int i = 1; i <= 3; i++)
        {
            out.println(i + " SingleLoop duration (ns) = " + runCaseOne());
            out.println(i + " SplitLoop  duration (ns) = " + runCaseTwo());
        }

        int result = arrayA[1] + arrayB[2] + arrayC[3] +
                arrayD[4] + arrayE[5] + arrayF[6];
        out.println("result = " + result);
    }

    public static long runCaseOne()
    {
        long start = System.nanoTime();

        int i = ITERATIONS;
        while (--i != 0)
        {
            int slot = i & MASK;
            byte b = (byte)i;
            arrayA[slot] = b;
            arrayB[slot] = b;
            arrayC[slot] = b;
            arrayD[slot] = b;
            arrayE[slot] = b;
            arrayF[slot] = b;
            arrayJ[slot] = b;
            arrayH[slot] = b;
            arrayI[slot] = b;
            arrayJ[slot] = b;
            arrayK[slot] = b;
            arrayL[slot] = b;
        }

        return System.nanoTime() - start;
    }

    public static long runCaseTwo()
    {
        long start = System.nanoTime();

        int i = ITERATIONS;
        while (--i != 0)
        {
            int slot = i & MASK;
            byte b = (byte)i;
            arrayA[slot] = b;
            arrayB[slot] = b;
            arrayC[slot] = b;
            arrayD[slot] = b;
            arrayE[slot] = b;
            arrayF[slot] = b;
        }

        i = ITERATIONS;
        while (--i != 0)
        {
            int slot = i & MASK;
            byte b = (byte)i;
            arrayJ[slot] = b;
            arrayH[slot] = b;
            arrayI[slot] = b;
            arrayJ[slot] = b;
            arrayK[slot] = b;
            arrayL[slot] = b;
        }

        return System.nanoTime() - start;
    }
}