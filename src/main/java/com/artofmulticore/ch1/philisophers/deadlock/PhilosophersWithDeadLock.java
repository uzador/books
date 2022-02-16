package com.artofmulticore.ch1.philisophers.deadlock;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

class Fork {
    private final String name;

    private final Lock lock = new ReentrantLock();

    public Fork(String name) {
        this.name = name;
    }

    public void acquire() {
        lock.lock();
    }

    public void release() {
        lock.unlock();
    }

    @Override
    public String toString() {
        return name;
    }
}

class Philosopher extends Thread {

    private static final Logger log = LoggerFactory.getLogger(Philosopher.class);

    private final CountDownLatch toEatFlag;
    private final Fork left;
    private final Fork right;
    private int eat;

    Philosopher(String name, CountDownLatch toEatFlag, final Fork left, final Fork right) {
        setName(name);
        this.toEatFlag = toEatFlag;
        this.left = left;
        this.right = right;
    }

    @Override
    public void run() {
        try {
            toEatFlag.await();
        } catch (InterruptedException ignore) {
            log.error("can't wait");
        }

        while (true) {
            log.info("thinking");

            log.info("acquiring {}", left);
            left.acquire();
            log.info("has acquired {}", left);

            log.info("acquiring {}", right);
            right.acquire();
            log.info("has acquired {}", right);

            log.info("eats {} times", ++eat);

            left.release();
            log.info("released {}", left);

            right.release();
            log.info("released {}", right);
        }
    }
}

public class PhilosophersWithDeadLock {
    public static void main(String[] args) {
        final Fork f1 = new Fork("Fork1");
        final Fork f2 = new Fork("Fork2");
        final Fork f3 = new Fork("Fork3");
        final Fork f4 = new Fork("Fork4");
        final Fork f5 = new Fork("Fork5");

        final CountDownLatch toEatFlag = new CountDownLatch(1);

        final Philosopher p1 = new Philosopher("Philosopher1", toEatFlag, f1, f2);
        final Philosopher p2 = new Philosopher("Philosopher2", toEatFlag, f2, f3);
        final Philosopher p3 = new Philosopher("Philosopher3", toEatFlag, f3, f4);
        final Philosopher p4 = new Philosopher("Philosopher4", toEatFlag, f4, f5);
        final Philosopher p5 = new Philosopher("Philosopher5", toEatFlag, f5, f1);

        p1.start();
        p2.start();
        p3.start();
        p4.start();
        p5.start();

        toEatFlag.countDown();
    }
}
