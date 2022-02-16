package com.artofmulticore.ch2.lock;

import com.artofmulticore.appendix.ThreadID;

class LockOne implements Lock {
    private final boolean[] flag = new boolean[2];

    // thread-local index, 0 or 1
    public void lock() {
        int i = ThreadID.get();
        int j = 1 - i;
        flag[i] = true;
        while (flag[j]) {
        } // wait
    }

    public void unlock() {
        int i = ThreadID.get();
        flag[i] = false;
    }
}