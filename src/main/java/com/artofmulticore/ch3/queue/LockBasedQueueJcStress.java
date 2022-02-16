package com.artofmulticore.ch3.queue;

import org.openjdk.jcstress.annotations.*;
import org.openjdk.jcstress.infra.results.II_Result;

@JCStressTest
@Outcome(id = "1, 2", expect = Expect.ACCEPTABLE, desc = "First actor added first")
@Outcome(id = "2, 1", expect = Expect.ACCEPTABLE, desc = "Second actor added first")
@State
public class LockBasedQueueJcStress {

    LockBasedQueue<Integer> q = new LockBasedQueue<>(2);

    @Actor
    public void actor1() {
        q.enq(1);
    }

    @Actor
    public void actor2() {
        q.enq(2);
    }

    @Arbiter
    public void check(II_Result r) {
        r.r1 = q.deq();
        r.r2 = q.deq();
    }

}