package com.example.staff.singleton;

public class SimpleSingletonInitOnCallThreadSafe {

    private static SimpleSingletonInitOnCallThreadSafe INSTANCE;

    private SimpleSingletonInitOnCallThreadSafe() {}

    public synchronized static SimpleSingletonInitOnCallThreadSafe getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new SimpleSingletonInitOnCallThreadSafe();
        }

        return INSTANCE;
    }
}
