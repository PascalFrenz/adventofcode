package me.frenz.day09;

import me.frenz.Day;

import java.util.List;

public class Day09 extends Day<Long, Long> {


    public static final int WINDOW_SIZE = 25;
    private final long[] xmas;

    public Day09(List<String> input) {
        super(input);
        xmas = input.stream().mapToLong(Long::valueOf).toArray();
        assert xmas.length > WINDOW_SIZE;
    }

    @Override
    protected Long part1() {
        int idx = WINDOW_SIZE;
        final Window window = new Window(WINDOW_SIZE, xmas);
        while (window.containsTwoNumbersThatSumTo(xmas[idx])) {
            window.advance();
            idx++;
        }

        return xmas[idx];
    }

    @Override
    protected Long part2() {
        final long searched = part1();

        int windowSize = 2;
        Window window = new Window(windowSize, xmas);

        boolean resultFound = false;
        while (!resultFound) {
            long lastWindowIdx = windowSize - 1;
            while (!window.sumEquals(searched) && ++lastWindowIdx < xmas.length) {
                window.advance();
            }

            resultFound = window.sumEquals(searched);
            window = resultFound ? window : new Window(++windowSize, xmas);
        }

        return window.getMinMaxSum();
    }

}


