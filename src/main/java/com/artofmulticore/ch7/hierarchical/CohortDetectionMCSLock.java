package com.artofmulticore.ch7.hierarchical;

import com.artofmulticore.ch7.queue.MCSLock;

public class CohortDetectionMCSLock extends MCSLock
        implements CohortDetectionLock {
    public boolean alone() {
        return (myNode.get().next == null);
    }
}
