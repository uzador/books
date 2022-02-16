package com.artofmulticore.ch2.lock;

public interface Lock {
    void lock(); // before entering critical section
    void unlock(); // before leaving critical section
}
