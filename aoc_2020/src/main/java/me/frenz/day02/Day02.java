package me.frenz.day02;

import me.frenz.Day;
import me.frenz.Pair;

import java.util.List;

public class Day02 extends Day<Long, Long> {

    public Day02(List<String> input) {
        super(input);
    }

    @Override
    protected Long part1() {
        return this.input.stream()
                .map(s -> Pair.of(s.split(":")[1].trim(), PasswordRule.from(s)))
                .filter(pair -> pair.right().validateSledRentalPlace(pair.left()))
                .count();
    }

    @Override
    protected Long part2() {
        return this.input.stream()
                .map(s -> Pair.of(s.split(":")[1].trim(), PasswordRule.from(s)))
                .filter(pair -> pair.right().validateTobogganCorporate(pair.left()))
                .count();
    }
}
