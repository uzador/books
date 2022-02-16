package com.example.staff.singleton;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class SimpleSingletonInitImmediatelyTest {

    @Test
    public void isSimpleSingletonInitImmediatelyIsSingleton() {
        SimpleSingletonInitImmediately singleton1 = SimpleSingletonInitImmediately.getInstance();
        SimpleSingletonInitImmediately singleton2 = SimpleSingletonInitImmediately.getInstance();

        assertEquals(singleton1, singleton2);
    }

    @Test
    public void isSimpleSingletonInitOnCallIsSingleton() {
        SimpleSingletonInitOnCall singleton1 = SimpleSingletonInitOnCall.getInstance();
        SimpleSingletonInitOnCall singleton2 = SimpleSingletonInitOnCall.getInstance();

        assertEquals(singleton1, singleton2);
    }

    @Test
    public void isEnumSingletonSingleton() {
        EnumSingleton singleton1 = EnumSingleton.INSTANCE.getInstance();
        EnumSingleton singleton2 = EnumSingleton.INSTANCE.getInstance();

        assertEquals(singleton1, singleton2);
    }
}