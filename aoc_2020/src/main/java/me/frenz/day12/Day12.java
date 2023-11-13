package me.frenz.day12;

import me.frenz.Day;

import java.util.List;

public class Day12 extends Day<Integer, Integer> {
    public Day12(List<String> input) {
        super(input);
    }

    @Override
    protected Integer part1() {
        final Ship ship = new Ship(0, 0);
        input.forEach(ship::move);
        return ship.manhattanDistance();
    }

    @Override
    protected Integer part2() {
        return 0;
    }
}
