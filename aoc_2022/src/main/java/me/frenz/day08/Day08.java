package me.frenz.day08;

import me.frenz.Day;

import java.util.List;


public class Day08 extends Day<Integer, Integer> {

    public Day08(final List<String> input) {
        super(input);
    }

    @Override
    protected Integer part1() {
        final Forest forest = new Forest(input);
        return forest.countVisibleTrees();
    }

    @Override
    protected Integer part2() {
        final Forest forest = new Forest(input);
        return forest.getHighestScenicScore();
    }
}
