package me.frenz;

import java.util.List;

abstract class Day<T, R> {

    protected final List<String> input;

    Day(List<String> input) {
        this.input = input;
    }

    abstract T part1();
    abstract R part2();
}
