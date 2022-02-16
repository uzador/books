package com.example.staff.singleton;

public class SimpleSingletonInitImmediately {

    private static final SimpleSingletonInitImmediately INSTANCE = new SimpleSingletonInitImmediately();

    private SimpleSingletonInitImmediately() {}

    public static SimpleSingletonInitImmediately getInstance() {
        return INSTANCE;
    }
}
