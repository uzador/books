package com.artofmulticore.ch2.lock;

import com.artofmulticore.appendix.ThreadID;

import java.util.concurrent.atomic.AtomicReferenceArray;

class Peterson implements Lock {
    // thread-local index, 0 or 1
    private final AtomicReferenceArray<Boolean> flag;
    private volatile int victim;

    public Peterson() {
        flag = new AtomicReferenceArray<>(2);
        flag.set(0, false);
        flag.set(1, false);
    }

    public void lock() {
        int i = ThreadID.getFromName();
        int j = 1 - i;
        flag.set(i, true); // I’m interested
        victim = i; // you go first
        while (flag.get(j) && victim == i) {
        } // wait
    }

    public void unlock() {
        int i = ThreadID.getFromName();
        flag.set(i, false); // I’m not interested
    }
}
