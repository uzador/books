package com.example.staff.singleton;

public class SimpleSingletonInitOnCallWithHolder {

    private static class holder {
        private static final SimpleSingletonInitOnCallWithHolder INSTANCE =
                new SimpleSingletonInitOnCallWithHolder();
    }

    public static SimpleSingletonInitOnCallWithHolder getInstance() {
        return holder.INSTANCE;
    }
}
