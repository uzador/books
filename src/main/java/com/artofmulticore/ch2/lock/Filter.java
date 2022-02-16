package com.artofmulticore.ch2.lock;

import com.artofmulticore.appendix.ThreadID;

import java.util.concurrent.atomic.AtomicIntegerArray;

class Filter implements Lock {
    private final AtomicIntegerArray level;
    private final AtomicIntegerArray victim;
    private final int n;

    public Filter(int n) {
        this.n = n;
        this.level = new AtomicIntegerArray(n);
        this.victim = new AtomicIntegerArray(n); // use 1..n-1
        for (int i = 0; i < n; i++) {
            level.set(i, 0);
            victim.set(i, 0);
        }
    }

    public void lock() {
        int me = ThreadID.getFromName();
        for (int i = 1; i < n; i++) { // attempt to enter level i
            level.set(me, i);
            victim.set(i, me);
            // spin while conflicts exist
            // while ((∃k != me)(level[k] >= i && victim[i] == me)){
            for (int k = 0; k < n; k++) {
                while (k != me && (level.get(k) >= i && victim.get(i) == me)) {
                }
            }
        }

        int[] arr = new int[10];
    }

    public void unlock() {
        int me = ThreadID.getFromName();
        level.set(me, 0);
    }
}
