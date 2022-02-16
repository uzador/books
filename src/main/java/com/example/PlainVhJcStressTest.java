package com.example;

import org.openjdk.jcstress.annotations.*;
import org.openjdk.jcstress.infra.results.II_Result;

import java.lang.invoke.MethodHandles;
import java.lang.invoke.VarHandle;

@JCStressTest
@Outcome(id = {"0, 0", "1, 1", "0, 1", "1, 0"}, expect = Expect.ACCEPTABLE, desc = "expected")
@State
public class PlainVhJcStressTest {

    private static final VarHandle VH_X;
    private static final VarHandle VH_Y;

    static {
        try {
            VH_X = MethodHandles.lookup().
                    findVarHandle(PlainVhJcStressTest.class, "x", int.class);
            VH_Y = MethodHandles.lookup().
                    findVarHandle(PlainVhJcStressTest.class, "y", int.class);
        } catch (NoSuchFieldException|IllegalAccessException e) {
            throw new IllegalStateException();
        }
    }

    private int x;
    private int y;

    @Actor
    public void actor1() {
        VH_Y.set(this, 1);
        VH_X.set(this, 1);
    }

    @Actor
    public void actor2(II_Result r) {
        r.r1 = (int) VH_X.get(this);
        r.r2 = (int) VH_Y.get(this);
    }
}
