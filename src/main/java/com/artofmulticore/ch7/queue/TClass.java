package com.artofmulticore.ch7.queue;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.IntStream;

public class TClass {
    public static void main(String[] args) {
        AtomicInteger tail = new AtomicInteger(0);
        int size = 4;
        int pad = 32;

        IntStream.rangeClosed(0, 10)
                .forEach(i -> {
                    int slot = (pad * tail.getAndIncrement()) % (size * pad);
                    System.out.print(slot);
                    System.out.print(" ");
                    System.out.print((slot + pad) % (size * pad));
                    System.out.println();
                });
    }
}
