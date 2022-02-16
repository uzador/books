package com.artofmulticore.ch7.hierarchical;

import com.artofmulticore.ch7.spin.BackoffLock;

public class CohortBackoffMCSLock extends CohortLock {
    public CohortBackoffMCSLock(int passLimit) {
        super(new BackoffLock(), new ClusterLocal<>() {
            protected CohortDetectionMCSLock initialValue() {
                return new CohortDetectionMCSLock();
            }

            @Override
            CohortDetectionLock get() {
                return null;
            }

            @Override
            void set(CohortDetectionLock value) {

            }
        }, passLimit);
    }
}
