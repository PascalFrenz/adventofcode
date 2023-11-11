package me.frenz.day08;

import me.frenz.Day;

import java.util.List;

public class Day08 extends Day<Long, Long> {

    private final String[] instructions;

    public Day08(List<String> input) {
        super(input);
        instructions = input.toArray(String[]::new);
    }

    @Override
    protected Long part1() {
        return new Console(instructions).executeNormally();
    }

    @Override
    protected Long part2() {
        return new Console(instructions).executeMutably();
    }

}
