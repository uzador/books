package com.artofmulticore.ch7.queue;

import com.artofmulticore.ch7.spin.Lock;

import java.util.concurrent.atomic.AtomicInteger;

public class ALockWithoutFalseSharingPadding implements Lock {
    ThreadLocal<Integer> mySlotIndex = ThreadLocal.withInitial(() -> 0);
    AtomicInteger tail;
    volatile boolean[] flag;
    int size;
    int paddingIdx = 4;

    public ALockWithoutFalseSharingPadding(int capacity) {
        size = capacity;
        tail = new AtomicInteger(0);
        flag = new boolean[capacity * paddingIdx];
        flag[0] = true;
    }

    public void lock() {
        int slot = (paddingIdx * tail.getAndIncrement()) % (size * paddingIdx);
        mySlotIndex.set(slot);
        while (!flag[slot]) {
        }
    }

    public void unlock() {
        int slot = mySlotIndex.get();
        flag[slot] = false;
        flag[(slot + paddingIdx) % (size * paddingIdx)] = true;
    }
}
