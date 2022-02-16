package com.example.staff.ti.linkedlist;

import java.util.HashSet;
import java.util.Set;

public class LinkedListNoDuplicates<T> {
    private Node<T> head = null;

    public void removeDuplicates() {
        Node<T> n = head;
        Set<T> dups = new HashSet<>();
        Node<T> previous = null;
        while (n != null) {
            if (dups.contains(n.value)) {
                previous.next = n.next;
            } else {
                dups.add(n.value);
                previous = n;
            }
            n = n.next;
        }
    }

    public void removeDuplicatesNoBuffer() {
        Node<T> n = head;
        while (n != null) {
            Node<T> nn = n;
            while (nn.next != null) {
                if (nn.next.value.equals(n.value)) {
                    nn.next = nn.next.next;
                } else {
                    nn = nn.next;
                }
            }

            n = n.next;
        }
    }

    public void addToEnd(T value) {
        Node<T> n = new Node<>(value);
        if (isEmpty()) {
            head = n;
        } else {
            Node<T> last = getLast();
            last.next = n;
        }
    }

    public void show() {
        if (isEmpty()) {
            System.out.println("List is empty");
        } else {
            Node<T> n = head;
            while (n != null) {
                System.out.print(n + " ");
                n = n.next;
            }
        }
    }

    private Node<T> getLast() {
        Node<T> tmp = head;
        while (tmp.next != null) {
            tmp = tmp.next;
        }

        return tmp;
    }

    private boolean isEmpty() {
        return head == null;
    }

    private static class Node<T> {
        public final T value;
        public Node<T> next;

        public Node(T value) {
            this.value = value;
        }

        @Override
        public String toString() {
            return "Node(" + value + ")";
        }
    }
}
