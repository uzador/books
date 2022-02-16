package com.artofmulticore.ch7.hierarchical;

import com.artofmulticore.appendix.ThreadID;
import com.artofmulticore.ch7.spin.Backoff;
import com.artofmulticore.ch7.spin.Lock;

import java.util.concurrent.atomic.AtomicInteger;

public class HBOLock implements Lock {
    private static final int LOCAL_MIN_DELAY = 1;
    private static final int LOCAL_MAX_DELAY = 2;
    private static final int REMOTE_MIN_DELAY = 3;
    private static final int REMOTE_MAX_DELAY = 4;
    private static final int FREE = -1;
    AtomicInteger state;

    public HBOLock() {
        state = new AtomicInteger(FREE);
    }

    public void lock() {
        int myCluster = ThreadID.getCluster();
        Backoff localBackoff =
                new Backoff(LOCAL_MIN_DELAY, LOCAL_MAX_DELAY);
        Backoff remoteBackoff =
                new Backoff(REMOTE_MIN_DELAY, REMOTE_MAX_DELAY);
        while (true) {
            if (state.compareAndSet(FREE, myCluster)) {
                return;
            }
            int lockState = state.get();
            if (lockState == myCluster) {
                localBackoff.backoff();
            } else {
                remoteBackoff.backoff();
            }
        }
    }

    public void unlock() {
        state.set(FREE);
    }
}
