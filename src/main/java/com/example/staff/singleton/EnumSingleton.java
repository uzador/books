package com.example.staff.singleton;

public enum EnumSingleton {
    INSTANCE("EnumSingleton");

    private String data;

    EnumSingleton(String data) {
        this.data = data;
    }

    public EnumSingleton getInstance() {
        return INSTANCE;
    }
}
