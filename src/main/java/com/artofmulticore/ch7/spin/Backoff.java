package com.artofmulticore.ch7.spin;

import java.util.concurrent.ThreadLocalRandom;

public class Backoff {
    final int minDelay, maxDelay;
    int limit;

    public Backoff(int min, int max) {
        minDelay = min;
        maxDelay = max;
        limit = minDelay;
    }

    public void backoff() {
        int delay = ThreadLocalRandom.current().nextInt(limit);
        limit = Math.min(maxDelay, 2 * limit);
        try {
            Thread.sleep(delay);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
