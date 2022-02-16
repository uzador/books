package com.artofmulticore.ch2.lock;

public interface Timestamp {
    boolean compare(Timestamp t);
}
