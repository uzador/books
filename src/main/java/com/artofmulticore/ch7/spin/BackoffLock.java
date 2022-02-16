package com.artofmulticore.ch7.spin;

import java.util.concurrent.atomic.AtomicBoolean;

public class BackoffLock implements Lock {
    private static final int MIN_DELAY = 1;
    private static final int MAX_DELAY = 10;
    private final AtomicBoolean state = new AtomicBoolean(false);

    public void lock() {
        Backoff backoff = new Backoff(MIN_DELAY, MAX_DELAY);
        while (true) {
            while (state.get()) {
            }
            if (!state.getAndSet(true)) {
                return;
            } else {
                backoff.backoff();
            }
        }
    }

    public void unlock() {
        state.set(false);
    }
}
