package com.artofmulticore.ch7.hierarchical;

public abstract class ClusterLocal<T> {
    abstract protected T initialValue();

    abstract T get();

    abstract void set(T value);
}
