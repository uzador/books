package com.example.staff.singleton;

public class SimpleSingletonInitOnCall {

    private static SimpleSingletonInitOnCall INSTANCE;

    private SimpleSingletonInitOnCall() {}

    public static SimpleSingletonInitOnCall getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new SimpleSingletonInitOnCall();
        }

        return INSTANCE;
    }
}
