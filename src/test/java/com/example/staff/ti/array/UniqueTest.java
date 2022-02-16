package com.example.staff.ti.array;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class UniqueTest {

    private Unique unique;

    @BeforeEach
    public void setUp() {
        unique = new Unique();
    }

    @Test
    public void stringIsUnique() {
        String str = "abcdefghij";
        assertTrue(unique.isUnique2(str));
    }

    @Test
    public void stringIsNotUnique() {
        String str = "1abcdefghij1";
        assertFalse(unique.isUnique2(str));
    }

}