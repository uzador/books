package com.artofmulticore.ch2.lock;

import com.artofmulticore.appendix.ThreadID;

public class LockTwo {
    private volatile int victim;

    public void lock() {
        int i = ThreadID.get();
        victim = i; // let the other go first
        while (victim == i) {
        } // wait
    }

    public void unlock() {
    }
}
