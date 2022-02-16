package com.artofmulticore.ch7.spin;

import java.util.concurrent.TimeUnit;

public interface Lock {
    default void lock() {
        throw new RuntimeException("lock() not implemented yet");
    }

    void unlock();

    default boolean tryLock(long time, TimeUnit unit) {
        throw new RuntimeException("tryLock() not implemented yet");
    };
}
