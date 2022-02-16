package com.artofmulticore.ch2.lock;

public class CounterImpl implements Counter {
    private int value;
    private final Lock lock;

    public CounterImpl(int c, Lock lock) { // constructor
        value = c;
        this.lock = lock;
    }

    // increment and return prior value
    public int getAndIncrement() {
        lock.lock();
        int temp;
        try {
            temp = value; // start of danger zone
            value = temp + 1; // end of danger zone
        } finally {
            lock.unlock();
        }
        return temp;
    }

    public int get() {
        return value;
    }
}
