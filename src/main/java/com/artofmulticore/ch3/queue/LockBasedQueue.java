package com.artofmulticore.ch3.queue;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

class LockBasedQueue<T> {
    int head, tail;
    T[] items;
    Lock lock;

    public LockBasedQueue(int capacity) {
        head = 0;
        tail = 0;
        lock = new ReentrantLock();
        items = (T[]) new Object[capacity];
    }

    public void enq(T x) throws FullException {
        lock.lock();
        try {
            if (tail - head == items.length)
                throw new FullException();
            items[tail % items.length] = x;
            tail++;
        } finally {
            lock.unlock();
        }
    }

    public T deq() throws EmptyException {
        lock.lock();
        try {
            if (tail == head)
                throw new EmptyException();
            T x = items[head % items.length];
            head++;
            return x;
        } finally {
            lock.unlock();
        }
    }
}
