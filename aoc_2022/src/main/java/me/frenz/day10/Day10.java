package me.frenz.day10;

import me.frenz.Day;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class Day10 extends Day<Integer, String> {

    public Day10(List<String> input) {
        super(input);
    }

    @Override
    protected Integer part1() {
        final List<Integer> signalStrengths = new ArrayList<>();
        new CPU(input).execute((cycle, register) -> {
            if (Set.of(20, 60, 100, 140, 180, 220).contains(cycle)) {
                signalStrengths.add(cycle * register);
            }
        });
        return signalStrengths.stream().mapToInt(Integer::intValue).sum();
    }

    @Override
    protected String part2() {
        final List<String> crtRows = new ArrayList<>();
        final StringBuilder row = new StringBuilder();
        new CPU(input).execute((cycle, register) -> {
            if (Math.abs(register - ((cycle - 1) % 40)) <= 1) {
                row.append('#');
            } else {
                row.append('.');
            }
            if (Set.of(40, 80, 120, 160, 200, 240).contains(cycle)) {
                crtRows.add(row.toString());
                row.delete(0, row.length());
            }
        });
        crtRows.add(row.toString());
        return String.join("\n", crtRows);
    }
}
