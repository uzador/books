package com.artofmulticore.ch7.hierarchical;

import com.artofmulticore.ch7.spin.Backoff;
import com.artofmulticore.ch7.spin.Lock;

import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import java.util.concurrent.atomic.AtomicReference;
import java.util.concurrent.atomic.AtomicStampedReference;

enum State {FREE, WAITING, RELEASED, ABORTED}

public class CompositeLock implements Lock {
    private static final int SIZE = 16;
    private static final int MIN_BACKOFF = 1;
    private static final int MAX_BACKOFF = 2;
    AtomicStampedReference<QNode> tail;
    QNode[] waiting;
    ThreadLocal<QNode> myNode = ThreadLocal.withInitial(() -> null);

    public CompositeLock() {
        tail = new AtomicStampedReference<>(null, 0);
        waiting = new QNode[SIZE];
        for (int i = 0; i < waiting.length; i++) {
            waiting[i] = new QNode();
        }
    }

    public void unlock() {
        QNode acqNode = myNode.get();
        acqNode.state.set(State.RELEASED);
        myNode.set(null);
    }

    public boolean tryLock(long time, TimeUnit unit) {
        long patience = TimeUnit.MILLISECONDS.convert(time, unit);
        long startTime = System.currentTimeMillis();
        Backoff backoff = new Backoff(MIN_BACKOFF, MAX_BACKOFF);
        try {
            QNode node = acquireQNode(backoff, startTime, patience);
            QNode pred = spliceQNode(node, startTime, patience);
            waitForPredecessor(pred, node, startTime, patience);
            return true;
        } catch (TimeoutException | InterruptedException e) {
            return false;
        }
    }

    private QNode acquireQNode(Backoff backoff, long startTime, long patience)
            throws TimeoutException, InterruptedException {
        QNode node = waiting[ThreadLocalRandom.current().nextInt(SIZE)];
        QNode currTail;
        int[] currStamp = {0};
        while (true) {
            if (node.state.compareAndSet(State.FREE, State.WAITING)) {
                return node;
            }
            currTail = tail.get(currStamp);
            State state = node.state.get();
            if (state == State.ABORTED || state == State.RELEASED) {
                if (node == currTail) {
                    QNode myPred = null;
                    if (state == State.ABORTED) {
                        myPred = node.pred;
                    }
                    if (tail.compareAndSet(currTail, myPred, currStamp[0], currStamp[0] + 1)) {
                        node.state.set(State.WAITING);
                        return node;
                    }
                }
            }
            backoff.backoff();
            if (timeout(patience, startTime)) {
                throw new TimeoutException();
            }
        }
    }

    private QNode spliceQNode(QNode node, long startTime, long patience)
            throws TimeoutException {
        QNode currTail;
        int[] currStamp = {0};
        do {
            currTail = tail.get(currStamp);
            if (timeout(startTime, patience)) {
                node.state.set(State.FREE);
                throw new TimeoutException();
            }
        } while (!tail.compareAndSet(currTail, node, currStamp[0], currStamp[0] + 1));
        return currTail;
    }

    private void waitForPredecessor(QNode pred, QNode node,
                                    long startTime, long patience)
            throws TimeoutException {
        if (pred == null) {
            myNode.set(node);
            return;
        }
        State predState = pred.state.get();
        while (predState != State.RELEASED) {
            if (predState == State.ABORTED) {
                QNode temp = pred;
                pred = pred.pred;
                temp.state.set(State.FREE);
            }
            if (timeout(patience, startTime)) {
                node.pred = pred;
                node.state.set(State.ABORTED);
                throw new TimeoutException();
            }
            predState = pred.state.get();
        }
        pred.state.set(State.FREE);
        myNode.set(node);
    }

    private boolean timeout(long startTime, long patience) {
        return System.currentTimeMillis() - startTime < patience;
    }
}

class QNode {
    AtomicReference<State> state;
    QNode pred;

    public QNode() {
        state = new AtomicReference<>(State.FREE);
    }
}
