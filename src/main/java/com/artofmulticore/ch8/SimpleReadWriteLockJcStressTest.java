package com.artofmulticore.ch8;

import org.openjdk.jcstress.annotations.*;
import org.openjdk.jcstress.infra.results.III_Result;

import java.util.concurrent.locks.Lock;

@JCStressTest
@Outcome(id = {"0, 0, 1", "0, 1, 0", "1, 0, 0"}, expect = Expect.ACCEPTABLE, desc = "correct")
@Outcome(id = {"0, 1, 1", "1, 0, 1", "1, 1, 0"}, expect = Expect.ACCEPTABLE, desc = "correct")
@Outcome(id = {"1, 1, 1"}, expect = Expect.ACCEPTABLE, desc = "correct")
@State
public class SimpleReadWriteLockJcStressTest {

    private final ReadWriteLock lock = new SimpleReadWriteLock();
    private final Lock readLock = lock.readLock();
    private final Lock writeLock = lock.writeLock();
    private int v;

    @Actor
    public void actor1(III_Result r) {
        writeLock.lock();
        try {
            r.r1 = ++v;
        } finally {
            writeLock.unlock();
        }
    }

    @Actor
    public void actor2(III_Result r) {
        readLock.lock();
        try {
            r.r2 = v;
        } finally {
            readLock.unlock();
        }
    }

    @Actor
    public void actor3(III_Result r) {
        readLock.lock();
        try {
            r.r3 = v;
        } finally {
            readLock.unlock();
        }
    }

}
