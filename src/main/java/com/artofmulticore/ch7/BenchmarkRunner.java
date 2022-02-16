package com.artofmulticore.ch7;

import com.artofmulticore.ch7.hierarchical.CompositeFastPathLock;
import com.artofmulticore.ch7.hierarchical.CompositeLock;
import com.artofmulticore.ch7.queue.*;
import com.artofmulticore.ch7.spin.BackoffLock;
import com.artofmulticore.ch7.spin.Lock;
import com.artofmulticore.ch7.spin.TASLock;
import com.artofmulticore.ch7.spin.TTASLock;
import org.openjdk.jmh.annotations.*;
import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.options.Options;
import org.openjdk.jmh.runner.options.OptionsBuilder;

import java.util.concurrent.TimeUnit;

@BenchmarkMode(Mode.Throughput)
@OutputTimeUnit(TimeUnit.SECONDS)
@Warmup(iterations = 5, time = 1)
@Measurement(iterations = 5, time = 1)
@Fork(5)
@State(Scope.Benchmark)
public class BenchmarkRunner {

    private static final int NUM_THREADS = 10;

    @Param({/*"TASLock", "TTASLock", "BackoffLock",*/ "ALockWithFalseSharing",
            "ALockWithoutFalseSharing", /*"CLHLock", "MCSLock", "TOLock", "CompositeLock", "CompositeFastPathLock"*/})
    public String lockType;

    public static void main(String[] args) throws Exception {
        Options opt = new OptionsBuilder().include(BenchmarkRunner.class.getSimpleName())
                .build();

        new Runner(opt).run();
    }

    @Benchmark
    @Group("group")
    @GroupThreads(NUM_THREADS)
    public void measure(TASState tasstate,
                        TTASState ttasstate,
                        BackoffState backoffState,
                        ALockWithFalseSharingState aLockWithFalseState,
                        ALockWithoutFalseSharingState aLockWithoutFalseState,
                        CLHState clhState,
                        MCSState mcsState,
                        TOState toState,
                        CompositeState compositeState,
                        CompositeFastPathState compositeFastPathState,
                        Counter counter) {
        switch (lockType) {
            case "TASLock":
                doLockAndUnlock(tasstate.lock, counter);
                break;
            case "TTASLock":
                doLockAndUnlock(ttasstate.lock, counter);
                break;
            case "BackoffLock":
                doLockAndUnlock(backoffState.lock, counter);
                break;
            case "ALockWithFalseSharing":
                doLockAndUnlock(aLockWithFalseState.lock, counter);
                break;
            case "ALockWithoutFalseSharing":
                doLockAndUnlock(aLockWithoutFalseState.lock, counter);
                break;
            case "CLHLock":
                doLockAndUnlock(clhState.lock, counter);
                break;
            case "MCSLock":
                doLockAndUnlock(mcsState.lock, counter);
                break;
            case "TOLock":
                doTryLockAndUnlock(toState.lock, counter);
                break;
            case "CompositeLock":
                doTryLockAndUnlock(compositeState.lock, counter);
                break;
            case "CompositeFastPathLock":
                doTryLockAndUnlock(compositeFastPathState.lock, counter);
                break;
            default:
                throw new RuntimeException();
        }

    }

    public void doLockAndUnlock(Lock lock, Counter counter) {
        lock.lock();
        try {
            counter.value++;
        } finally {
            lock.unlock();
        }
    }

    public void doTryLockAndUnlock(Lock lock, Counter counter) {
        while (!lock.tryLock(1, TimeUnit.MILLISECONDS)) {
        }
        try {
            counter.value++;
        } finally {
            lock.unlock();
        }
    }

    @org.openjdk.jmh.annotations.State(Scope.Group)
    public static class Counter {
        private long value;
    }

    @org.openjdk.jmh.annotations.State(Scope.Group)
    public static class TASState {
        private final Lock lock = new TASLock();
    }

    @org.openjdk.jmh.annotations.State(Scope.Group)
    public static class TTASState {
        private final Lock lock = new TTASLock();
    }

    @org.openjdk.jmh.annotations.State(Scope.Group)
    public static class BackoffState {
        private final Lock lock = new BackoffLock();
    }

    @org.openjdk.jmh.annotations.State(Scope.Group)
    public static class ALockWithoutFalseSharingState {
        private final Lock lock = new ALockWithoutFalseSharingPadding(NUM_THREADS);
    }

    @org.openjdk.jmh.annotations.State(Scope.Group)
    public static class ALockWithFalseSharingState {
        private final Lock lock = new ALockWithFalseSharing(NUM_THREADS);
    }

    @org.openjdk.jmh.annotations.State(Scope.Group)
    public static class CLHState {
        private final Lock lock = new CLHLock();
    }

    @org.openjdk.jmh.annotations.State(Scope.Group)
    public static class MCSState {
        private final Lock lock = new MCSLock();
    }

    @org.openjdk.jmh.annotations.State(Scope.Group)
    public static class TOState {
        private final Lock lock = new TOLock();
    }

    @org.openjdk.jmh.annotations.State(Scope.Group)
    public static class CompositeState {
        private final Lock lock = new CompositeLock();
    }

    @org.openjdk.jmh.annotations.State(Scope.Group)
    public static class CompositeFastPathState {
        private final Lock lock = new CompositeFastPathLock();
    }
}