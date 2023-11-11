package me.frenz.day03;

import me.frenz.Day;

import java.util.List;

public class Day03 extends Day<Integer, Long> {

    private static final char TREE = '#';
    private final List<char[]> forest;

    public Day03(List<String> input) {
        super(input);
        this.forest = this.input.stream()
                .map(String::toCharArray)
                .toList();
    }

    private static int getNumberOfTreesHit(final List<char[]> forest, final int stepsToRight, final int stepsDown) {
        int treeHit = 0;
        int position = 0;

        for (int i = 0; i < forest.size(); i++) {
            if ((i % stepsDown) == 0) {
                char[] level = forest.get(i);
                treeHit += level[position] == TREE ? 1 : 0;
                position = (position + stepsToRight) % level.length;
            }
        }

        return treeHit;
    }

    @Override
    protected Integer part1() {
        return getNumberOfTreesHit(forest, 3, 1);
    }

    @Override
    protected Long part2() {final int a = getNumberOfTreesHit(forest, 1, 1);
        final int b = getNumberOfTreesHit(forest, 3, 1);
        final int c = getNumberOfTreesHit(forest, 5, 1);
        final int d = getNumberOfTreesHit(forest, 7, 1);
        final int e = getNumberOfTreesHit(forest, 1, 2);

        return (long) a * b * c * d * e;
    }
}
