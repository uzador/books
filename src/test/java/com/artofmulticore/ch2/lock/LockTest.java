package com.artofmulticore.ch2.lock;

import com.artofmulticore.appendix.ThreadID;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.concurrent.CountDownLatch;
import java.util.stream.IntStream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

public class LockTest {

    private static final int AMOUNT_OF_ITERATIONS_PER_THREAD = 100_000;

    @BeforeEach
    public void setUp() {
        ThreadID.set(0);
    }

    @Test
    public void notThreadSafeLock() throws InterruptedException {
        assertNotEquals(2 * AMOUNT_OF_ITERATIONS_PER_THREAD,
                runCounterInTwoThreads(new NotThreadSafeLock()));
    }

    @Test
    public void threadSafePetersonLock() throws InterruptedException {
        assertEquals(2 * AMOUNT_OF_ITERATIONS_PER_THREAD,
                runCounterInTwoThreads(new Peterson()));
    }

    @Test
    public void threadSafeFilterLock() throws InterruptedException {
        assertEquals(2 * AMOUNT_OF_ITERATIONS_PER_THREAD,
                runCounterInTwoThreads(new Filter(2)));
    }

    @Test
    public void threadSafeBakeryLock() throws InterruptedException {
        assertEquals(2 * AMOUNT_OF_ITERATIONS_PER_THREAD,
                runCounterInTwoThreads(new Bakery(2)));
    }

    private int runCounterInTwoThreads(Lock lock) throws InterruptedException {
        final Counter counter = new CounterImpl(0, lock);
        final CountDownLatch start = new CountDownLatch(1);

        Runnable runnable = () -> {
            try {
                start.await();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            IntStream.rangeClosed(1, AMOUNT_OF_ITERATIONS_PER_THREAD).forEach(i -> {
                counter.getAndIncrement();
            });
        };

        Thread t0 = new Thread(runnable);
        t0.setName("0");

        Thread t1 = new Thread(runnable);
        t1.setName("1");

        t0.start();
        t1.start();

        start.countDown();

        /*Runnable second = () -> {
            try {
                start.await();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            IntStream.rangeClosed(1, AMOUNT_OF_ITERATIONS_PER_THREAD).forEach(i -> {
                counter.getAndIncrement();
            });
        };*/

        //ExecutorService executor = Executors.newCachedThreadPool();
        //executor.execute(first);
        //executor.execute(second);

        //start.countDown();

        //executor.shutdown();
        //while (!executor.isTerminated()) {

        //}

        t0.join();
        t1.join();

        return counter.get();
    }

}