package me.frenz.day06;

import me.frenz.Day;

import java.util.List;


public class Day06 extends Day<Integer, Integer> {

    private final String buffer;

    public Day06(final List<String> input) {
        super(input);
        if (input.size() == 1) {
            buffer = input.get(0);
        } else {
            buffer = "";
            throw new IllegalArgumentException("Buffer had more than one line!");
        }
    }

    @Override
    protected Integer part1() {
        return getProcessedChars(4);
    }

    @Override
    protected Integer part2() {
        return getProcessedChars(14);
    }

    private int getProcessedChars(final int distinctCharsLen) {
        if (buffer.length() < distinctCharsLen) {
            throw new IllegalArgumentException("Buffer is too small! Must be at least length >= 4");
        }

        boolean markerFound = false;
        int i = 0, j = distinctCharsLen;
        while (j <= buffer.length() && !markerFound) {
            markerFound = buffer.substring(i, j).chars().distinct().count() == distinctCharsLen;
            i++;
            j++;
        }
        return j - 1;
    }
}
