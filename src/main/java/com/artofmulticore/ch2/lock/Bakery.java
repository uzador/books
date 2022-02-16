package com.artofmulticore.ch2.lock;

import com.artofmulticore.appendix.ThreadID;

import java.util.Arrays;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReferenceArray;

class Bakery implements Lock {
    private final AtomicReferenceArray<Boolean> flag;
    private final AtomicInteger[] label;
    private final int n;

    public Bakery(int n) {
        this.n = n;
        flag = new AtomicReferenceArray<>(n);
        label = new AtomicInteger[n];
        for (int i = 0; i < n; i++) {
            flag.set(i, false);
            label[i] = new AtomicInteger(0);
        }
    }

    public void lock() {
        int i = ThreadID.getFromName();
        flag.set(i, true);
        //label[i] = max(label[0], ...,label[n - 1])+1;
        //while ((∃k != i)(flag[k] && (label[k], k) <<(label[i], i))){
        //}
        label[i].set(getMaximum() + 1);
        for (int k = 0; k < n; k++) {
            while(k != i && (flag.get(k) && lexicographicalLessCompare(k, i))) {
            }
        }
    }

    public void unlock() {
        flag.set(ThreadID.getFromName(), false);
    }

    private int getMaximum() {
        return Arrays.stream(label).mapToInt(AtomicInteger::get).max().orElse(0);
    }

    private boolean lexicographicalLessCompare(int k, int i) {
        return label[k].get() < label[i].get() ||
                (label[k].get() == label[i].get() && k < i);
    }
}
