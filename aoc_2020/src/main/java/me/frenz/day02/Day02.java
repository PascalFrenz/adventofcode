package me.frenz.day02;

import me.frenz.Day;
import me.frenz.Util;

import java.util.List;

public class Day02 extends Day<Long, Long> {

    public Day02(List<String> input) {
        super(input);
    }

    @Override
    protected Long part1() {
        return this.input.stream()
                .map(s -> Util.Pair.of(s.split(":")[1].trim(), PasswordRule.from(s)))
                .filter(pair -> pair.getRight().validateSledRentalPlace(pair.getLeft()))
                .count();
    }

    @Override
    protected Long part2() {
        return this.input.stream()
                .map(s -> Util.Pair.of(s.split(":")[1].trim(), PasswordRule.from(s)))
                .filter(pair -> pair.getRight().validateTobogganCorporate(pair.getLeft()))
                .count();
    }
}
