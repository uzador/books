package com.artofmulticore.appendix;

public class ThreadID {
    private static class ThreadLocalID extends ThreadLocal<Integer> {
        private static volatile int nextID = 0;

        protected synchronized Integer initialValue() {
            return nextID++;
        }
    }

    private static final ThreadLocalID threadID = new ThreadLocalID();

    public static int get() {
        return threadID.get();
    }

    public static void set(int index) {
        threadID.set(index);
    }

    public static int getFromName() {
        return Integer.parseInt(Thread.currentThread().getName());
    }

    public static int getCluster() {
        return 1;
    }
}
