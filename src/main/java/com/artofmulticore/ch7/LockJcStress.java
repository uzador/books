package com.artofmulticore.ch7;

import com.artofmulticore.ch7.spin.Lock;
import com.artofmulticore.ch7.spin.TTASLock;
import org.openjdk.jcstress.annotations.*;
import org.openjdk.jcstress.infra.results.II_Result;

@JCStressTest
@Outcome(id = {"1, 2", "2, 1"}, expect = Expect.ACCEPTABLE, desc = "Sequential execution")
@Outcome(expect = Expect.FORBIDDEN, desc = "Other cases are forbidden")
@State
public class LockJcStress {

    private final Lock lock = new TTASLock();
    private int v;

    @Actor
    public void actor1(II_Result r) {
        lock.lock();
        try {
            r.r1 = ++v;
        } finally {
            lock.unlock();
        }
    }

    @Actor
    public void actor2(II_Result r) {
        lock.lock();
        try {
            r.r2 = ++v;
        } finally {
            lock.unlock();
        }
    }
}