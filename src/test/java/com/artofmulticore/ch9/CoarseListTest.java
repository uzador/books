package com.artofmulticore.ch9;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CoarseListTest {

    private MySet<String> mySet;

    @BeforeEach
    public void setUp() {
        mySet = new CoarseList<>();
    }

    @Test
    public void add() {
        mySet.add("1");
        mySet.add("2");
        mySet.add("1");
        mySet.show();
    }

    @Test
    public void remove() {
        mySet.add("1");
        mySet.add("2");
        mySet.add("1");
        mySet.add("3");
        mySet.remove("1");
        mySet.show();
    }

    @Test
    public void contains() {
        mySet.add("1");
        mySet.add("2");
        mySet.add("3");
        assertTrue(mySet.contains("1"));
        assertTrue(mySet.contains("2"));
        assertTrue(mySet.contains("3"));
        assertFalse(mySet.contains("4"));
    }

}