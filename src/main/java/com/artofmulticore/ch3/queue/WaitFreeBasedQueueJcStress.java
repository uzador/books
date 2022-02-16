package com.artofmulticore.ch3.queue;

import org.openjdk.jcstress.annotations.*;
import org.openjdk.jcstress.infra.results.II_Result;

@JCStressTest
@Outcome(id = "1, 2", expect = Expect.ACCEPTABLE, desc = "First actor added first")
@Outcome(id = "2, 1", expect = Expect.ACCEPTABLE, desc = "Second actor added first")
@Outcome(id = {"100, 100"}, expect = Expect.ACCEPTABLE_INTERESTING, desc = "Missed enqueue")
@Outcome(id = {"1, 100", "100, 1"}, expect = Expect.ACCEPTABLE_INTERESTING, desc = "Missed enqueue")
@Outcome(id = {"2, 100", "100, 2"}, expect = Expect.ACCEPTABLE_INTERESTING, desc = "Missed enqueue")
@Outcome(id = {"0, 1", "1, 0", "1, 1"}, expect = Expect.ACCEPTABLE_INTERESTING, desc = "Enqueued partly")
@State
public class WaitFreeBasedQueueJcStress {

    WaitFreeQueue<Integer> q = new WaitFreeQueue<>(2);

    @Actor
    public void actor1() {
        try {
            q.enq(1);
        } catch (FullException e) {

        }
    }

    @Actor
    public void actor2() {
        try {
            q.enq(2);
        } catch (FullException e) {

        }
    }

    @Arbiter
    public void check(II_Result r) {
        try {
            r.r1 = q.deq();
        } catch (Exception e) {
            r.r1 = 100;
        }

        try {
            r.r2 = q.deq();
        } catch (Exception e) {
            r.r2 = 100;
        }
    }

}