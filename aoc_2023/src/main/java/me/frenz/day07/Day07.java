
package me.frenz.day07;

import me.frenz.Day;

import java.util.List;
import java.util.stream.IntStream;

public class Day07 extends Day<Long, Long> {

    public Day07(List<String> input) {
        super(input);
    }

    @Override
    protected Long part1() {
        final List<Hand> orderedHands = input.stream().map(Hand::from).sorted(Hand.comparator()).toList();
        return IntStream.range(0, orderedHands.size())
                .mapToLong(rank -> (long) orderedHands.get(rank).bid() * (rank + 1))
                .sum();
    }

    @Override
    protected Long part2() {
        final List<Hand> orderedHands = input.stream().map(Hand::from).sorted(Hand.comparatorWithJoker()).toList();
        return IntStream.range(0, orderedHands.size())
                .mapToLong(rank -> (long) orderedHands.get(rank).bid() * (rank + 1))
                .sum();
    }

}
