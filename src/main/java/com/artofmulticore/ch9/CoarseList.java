package com.artofmulticore.ch9;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class CoarseList<T> implements MySet<T> {

    private final Node<T> head;
    private final Lock lock = new ReentrantLock();

    public CoarseList() {
        head = new Node<>(Integer.MIN_VALUE);
        head.next = new Node<>(Integer.MAX_VALUE);
    }

    @Override
    public boolean add(T item) {
        Node<T> pred, curr;
        int key = item.hashCode();
        lock.lock();
        try {
            pred = head;
            curr = head.next;
            while (curr.key < key) {
                pred = curr;
                curr = curr.next;
            }

            if (key == curr.key) {
                return false;
            }

            Node<T> n = new Node<>(item);
            n.next = curr;
            pred.next = n;

            return true;
        } finally {
            lock.unlock();
        }
    }

    @Override
    public boolean remove(T item) {
        Node<T> pred, curr;
        int key = item.hashCode();
        lock.lock();
        try {
            pred = head;
            curr = pred.next;
            while (curr.key < key) {
                pred = curr;
                curr = curr.next;
            }
            if (key == curr.key) {
                pred.next = curr.next;
                return true;
            } else {
                return false;
            }
        } finally {
            lock.unlock();
        }
    }

    @Override
    public void show() {
        lock.lock();
        try {
            Node<T> curr = head;
            while (curr != null) {
                System.out.print(curr.value + " ");
                curr = curr.next;
            }
        } finally {
            lock.unlock();
        }
    }

    @Override
    public boolean contains(T item) {
        lock.lock();
        try {
            Node<T> curr = head;
            while (curr != null) {
                if (item.equals(curr.value)) {
                    return true;
                }

                curr = curr.next;
            }

            return false;
        } finally {
            lock.unlock();
        }
    }

    private static class Node<T> {
        T value;
        int key;
        Node<T> next;

        public Node(int key) {
            this.key = key;
        }

        public Node(T value) {
            this.value = value;
            this.key = value.hashCode();
        }
    }
}
