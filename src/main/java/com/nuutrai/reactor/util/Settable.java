package com.nuutrai.reactor.util;

public class Settable<T> {

    private T value;

    public void set(T value) {
        this.value = value;
    }

    public T get() {
        return value;
    }

    public boolean isNull() {
        return value == null;
    }
}
