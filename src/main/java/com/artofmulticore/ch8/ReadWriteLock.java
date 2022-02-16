package com.artofmulticore.ch8;

import java.util.concurrent.locks.Lock;

public interface ReadWriteLock {
    Lock readLock();
    Lock writeLock();
}
