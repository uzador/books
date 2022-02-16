package com.artofmulticore.ch8;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class MyLockedQueue<T> {

    private final Lock lock = new ReentrantLock();
    private final Condition full = lock.newCondition();
    private final Condition empty = lock.newCondition();

    private final T[] items;
    private int head, tail, size;

    public MyLockedQueue(int capacity) {
        items = (T[]) new Object[capacity];
    }

    public void enq(T value) throws InterruptedException {
        lock.lock();
        try {
            while (items.length == size) {
                full.await();
            }
            items[tail] = value;
            if (++tail == size) {
                tail = 0;
            }
            ++size;
            empty.signal();
        } finally {
            lock.unlock();
        }
    }

    public T deq() throws InterruptedException {
        lock.lock();
        try {
            while (size == 0) {
                empty.await();
            }
            T value = items[head];
            if (++head == size) {
                head = 0;
            }
            --size;
            return value;
        } finally {
            lock.unlock();
        }
    }
}
