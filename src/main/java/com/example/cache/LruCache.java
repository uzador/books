package com.example.cache;

import java.util.HashMap;
import java.util.Map;

class Node<K, V> {
    K key;
    V value;
    Node<K, V> next;
    Node<K, V> prev;

    public Node(K key, V value) {
        this.key = key;
        this.value = value;
    }
}

public class LruCache<K, V> {
    private final Map<K, Node<K, V>> cache;
    private final int capacity;

    private Node<K, V> mru;
    private Node<K, V> lru;

    public LruCache(int capacity) {
        this.capacity = capacity;
        cache = new HashMap<>(capacity);
    }

    int size() {
        return cache.size();
    }

    V get(K key) {
        if (cache.containsKey(key)) {
            Node<K, V> node = cache.get(key);
            deleteFromList(node);
            setToHead(node);

            return node.value;
        }

        return null;
    }

    void put(K key, V value) {
        if (cache.containsKey(key)) {
            return;
        }

        Node<K, V> newNode = new Node<>(key, value);
        if (cache.size() == capacity) {
            cache.remove(lru.key);
            evict();
        }
        cache.put(key, newNode);
        setToHead(newNode);
    }

    private void evict() {
        lru.prev.next = null;
        lru = lru.prev;
    }

    private void setToHead(Node<K, V> node) {
        if (mru != null) {
            mru.prev = node;
            node.next = mru;
        }
        mru = node;

        if (lru == null) {
            lru = node;
        }
    }

    private void deleteFromList(Node<K, V> node) {
        if (node.prev != null) {
            node.prev.next = node.next;
        }

        if (node.next != null) {
            node.next.prev = node.prev;
        }
    }
}
