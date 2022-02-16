package com.example;

import org.openjdk.jcstress.annotations.*;
import org.openjdk.jcstress.infra.results.II_Result;

@JCStressTest
@Outcome(id = {"0, 0", "1, 1", "0, 1", "1, 0"}, expect = Expect.ACCEPTABLE, desc = "expected")
@State
public class PlainJcStressTest {

    private int x;
    private int y;

    @Actor
    public void actor1() {
        y = 1;
        x = 1;
    }

    @Actor
    public void actor2(II_Result r) {
        r.r1 = x;
        r.r2 = y;
    }
}
