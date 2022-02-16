package com.artofmulticore.ch9;

public interface MySet<T> {
    boolean add(T x);

    boolean remove(T x);

    boolean contains(T x);

    void show();
}
