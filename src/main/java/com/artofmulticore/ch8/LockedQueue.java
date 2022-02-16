package com.artofmulticore.ch8;

import java.util.concurrent.locks.ReentrantLock;

class LockedQueue<T> {

    final java.util.concurrent.locks.Lock lock = new ReentrantLock();
    final java.util.concurrent.locks.Condition notFull = lock.newCondition();
    final java.util.concurrent.locks.Condition notEmpty = lock.newCondition();
    final T[] items;
    int tail, head, count;

    public LockedQueue(int capacity) {
        items = (T[]) new Object[capacity];
    }


    public void enq(T x) {
        lock.lock();
        try {
            while (count == items.length) {
                try {
                    notFull.await();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            items[tail] = x;
            if (++tail == items.length) {
                tail = 0;
            }
            ++count;
            notEmpty.signal();
        } finally {
            lock.unlock();
        }
    }

    public void enqBroken(T x) {
        lock.lock();
        try {
            while (count == items.length) {
                try {
                    notFull.await();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            items[tail] = x;
            if (++tail == items.length) {
                tail = 0;
            }
            ++count;
            if (count == 1) { // Wrong!
                notEmpty.signal();
            }
        } finally {
            lock.unlock();
        }
    }

    public T deq() {
        lock.lock();
        try {
            while (count == 0) {
                try {
                    notEmpty.await();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            T x = items[head];
            if (++head == items.length) {
                head = 0;
            }
            --count;
            notFull.signal();
            return x;
        } finally {
            lock.unlock();

        }
    }
}
