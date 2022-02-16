package com.artofmulticore.ch7.hierarchical;

import com.artofmulticore.ch7.spin.Lock;

public class CohortLock implements Lock {
    final Lock globalLock;
    final ClusterLocal<CohortDetectionLock> clusterLock;
    final TurnArbiter localPassArbiter;
    ClusterLocal<Boolean> passedLocally;

    public CohortLock(Lock gl, ClusterLocal<CohortDetectionLock> cl, int passLimit) {
        globalLock = gl;
        clusterLock = cl;
        localPassArbiter = new TurnArbiter(passLimit);
    }

    public void lock() {
        clusterLock.get().lock();
        if (passedLocally.get()) return;
        globalLock.lock();
    }

    public void unlock() {
        CohortDetectionLock cl = clusterLock.get();
        if (cl.alone() || !localPassArbiter.goAgain()) {
            localPassArbiter.passed();
            passedLocally.set(false);
            globalLock.unlock();
        } else {
            localPassArbiter.wentAgain();
            passedLocally.set(true);
        }
        cl.unlock();
    }
}
