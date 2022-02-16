package com.artofmulticore.ch7.queue;

import com.artofmulticore.ch7.spin.Lock;

import java.util.concurrent.atomic.AtomicInteger;

public class ALockWithFalseSharing implements Lock {
    ThreadLocal<Integer> mySlotIndex = ThreadLocal.withInitial(() -> 0);
    AtomicInteger tail;
    volatile boolean[] flag;
    int size;

    public ALockWithFalseSharing(int capacity) {
        size = capacity;
        tail = new AtomicInteger(0);
        flag = new boolean[capacity];
        flag[0] = true;
    }

    public void lock() {
        int slot = tail.getAndIncrement() % size;
        mySlotIndex.set(slot);
        while (!flag[slot]) {
        }
    }

    public void unlock() {
        int slot = mySlotIndex.get();
        flag[slot] = false;
        flag[(slot + 1) % size] = true;
    }
}
