package com.artofmulticore.ch8;

import org.openjdk.jcstress.annotations.*;
import org.openjdk.jcstress.infra.results.II_Result;

@JCStressTest
@Outcome(id = {"0, 1", "1, 0"}, expect = Expect.ACCEPTABLE, desc = "correct")
@State
public class LockedQueueJcStressTest {

    private final LockedQueue<Integer> queue = new LockedQueue<>(16);

    @Actor
    public void actor1() {
        queue.enq(0);
    }

    @Actor
    public void actor2() {
        queue.enq(1);
    }

    @Arbiter
    public void arbiter(II_Result r) {
        r.r1 = queue.deq();
        r.r2 = queue.deq();
    }

}
