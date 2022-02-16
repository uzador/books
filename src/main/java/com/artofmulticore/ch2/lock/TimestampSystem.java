package com.artofmulticore.ch2.lock;

public interface TimestampSystem {
    Timestamp[] scan();

    void label(Timestamp timestamp, int i);
}
