package com.example.cache;

import org.junit.jupiter.api.Test;

import java.util.stream.IntStream;

import static org.junit.jupiter.api.Assertions.assertEquals;

class LruCacheTest {
    @Test
    public void getTest() {
        LruCache<Integer, Integer> cache = new LruCache<>(3);
        IntStream.rangeClosed(1, 9)
                .forEach(i -> cache.put(i, i));
        assertEquals(7, (int) cache.get(7));
        assertEquals(8, (int) cache.get(8));
        assertEquals(9, (int) cache.get(9));

        assertEquals(3, cache.size());
    }
}