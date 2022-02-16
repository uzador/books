package com.example.staff.ti.array;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PermutationTest {

    private Permutation permutation;

    @BeforeEach
    public void setUp() {
        permutation = new Permutation();
    }

    @Test
    public void isPermutation() {
        String a = "abc";
        String b = "cba";
        assertTrue(permutation.isPermutation(a, b));
    }

    @Test
    public void isNotPermutation() {
        String a = "abc1";
        String b = "cba2";
        assertFalse(permutation.isPermutation(a, b));
    }

}