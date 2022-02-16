package com.artofmulticore.ch2.lock;

public interface Counter {
    int getAndIncrement();
    int get();
}
