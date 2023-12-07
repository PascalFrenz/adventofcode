
package me.frenz.day07;

import me.frenz.Day;

import java.util.List;

public class Day07 extends Day<Long, Integer> {

    public Day07(List<String> input) {
        super(input);
    }

    @Override
    protected Long part1() {
        final List<Hand> orderedHands = input.stream().map(Hand::from).sorted(Hand::compareTo).toList();
        long sum = 0;
        for (int rank = 0; rank < orderedHands.size(); rank++) {
            sum += (long) orderedHands.get(rank).bid() * (rank + 1);
        }
        return sum;
    }

    @Override
    protected Integer part2() {
        return 0;
    }

}
