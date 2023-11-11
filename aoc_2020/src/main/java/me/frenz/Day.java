package me.frenz;

import java.util.List;

public abstract class Day<T, R> {

    protected final List<String> input;

    protected Day(List<String> input) {
        this.input = input;
    }

    protected abstract T part1();
    protected abstract R part2();
}
