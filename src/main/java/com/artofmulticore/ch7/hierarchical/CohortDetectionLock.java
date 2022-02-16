package com.artofmulticore.ch7.hierarchical;

import com.artofmulticore.ch7.spin.Lock;

public interface CohortDetectionLock extends Lock {
    boolean alone();
}