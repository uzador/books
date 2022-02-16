package com.example.staff.ti.linkedlist;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class LinkedListNoDuplicatesTest {

    private LinkedListNoDuplicates<String> list;

    @BeforeEach
    public void setUp() {
        list = new LinkedListNoDuplicates<>();
    }

    @Test
    public void removeDuplicates() {
        list.addToEnd("1");
        list.addToEnd("2");
        list.addToEnd("3");
        list.addToEnd("2");
        list.addToEnd("3");
        list.removeDuplicatesNoBuffer();
        list.show();
    }

    @Test
    public void removeDuplicates2() {
        list.addToEnd("1");
        list.addToEnd("1");
        list.removeDuplicatesNoBuffer();
        list.show();
    }
}