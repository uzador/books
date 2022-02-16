package com.artofmulticore.ch3.queue;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReferenceArray;

class WaitFreeFixedQueue<T> {
    AtomicInteger head, tail;
    AtomicReferenceArray<T> items;

    public WaitFreeFixedQueue(int capacity) {
        items = new AtomicReferenceArray<>(capacity);
        head = new AtomicInteger(0);
        tail = new AtomicInteger(0);
    }

    public void enq(T x) throws FullException {
        if (tail.get() - head.get() == items.length())
            throw new FullException();
        items.set(tail.get() % items.length(), x);
        tail.incrementAndGet();
    }

    public T deq() throws EmptyException {
        if (tail.get() - head.get() == 0)
            throw new EmptyException();
        T x = items.get(head.get() % items.length());
        head.incrementAndGet();
        return x;
    }
}
