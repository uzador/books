package com.example.staff.singleton;

public class SimpleSingletonInitOnCallThreadSafeDCL {

    private static volatile SimpleSingletonInitOnCallThreadSafeDCL INSTANCE;

    private SimpleSingletonInitOnCallThreadSafeDCL() {}

    public static SimpleSingletonInitOnCallThreadSafeDCL getInstance() {
        if (INSTANCE == null) {
            synchronized (SimpleSingletonInitOnCallThreadSafeDCL.class) {
                if (INSTANCE == null) {
                    INSTANCE = new SimpleSingletonInitOnCallThreadSafeDCL();
                }
            }
        }

        return INSTANCE;
    }
}
