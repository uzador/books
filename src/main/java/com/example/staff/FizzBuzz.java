package com.example.staff;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.IntFunction;
import java.util.stream.IntStream;

interface Expression<P, R> {
    boolean execute(P parameter);

    R getResult();
}

class FizzBuzzExpr implements Expression<Integer, String> {

    @Override
    public boolean execute(Integer parameter) {
        return parameter % 5 == 0 && parameter % 3 == 0;
    }

    @Override
    public String getResult() {
        return "FizzBuzz";
    }
}

class FizzExpr implements Expression<Integer, String> {

    @Override
    public boolean execute(Integer parameter) {
        return parameter % 3 == 0;
    }

    @Override
    public String getResult() {
        return "Fizz";
    }
}

class BuzzExpr implements Expression<Integer, String> {

    @Override
    public boolean execute(Integer parameter) {
        return parameter % 5 == 0;
    }

    @Override
    public String getResult() {
        return "Buzz";
    }
}

public class FizzBuzz implements IntFunction<String> {

    private final List<Expression<Integer, String>> expressions;

    FizzBuzz(Expression<Integer, String> ... expressions) {
        this.expressions = new ArrayList<>();
        this.expressions.addAll(Arrays.asList(expressions));
    }

    @Override
    public String apply(int number) {
        for (Expression<Integer, String> expr : expressions) {
            if (expr.execute(number)) {
                return expr.getResult();
            }
        }

        return String.valueOf(number);
    }

    public static void main(String[] args) {
        final FizzBuzz f = new FizzBuzz(new FizzBuzzExpr(), new FizzExpr(), new BuzzExpr());
        final int[] array = new int[100];

        IntStream
                .rangeClosed(0, 99)
                .forEach(i -> array[i] = i + 1);

        Arrays.stream(array)
                .mapToObj(f)
                .forEach(System.out::println);
    }
}
