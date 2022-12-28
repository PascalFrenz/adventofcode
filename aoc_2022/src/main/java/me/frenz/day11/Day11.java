package me.frenz.day11;

import me.frenz.Day;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

public class Day11 extends Day<Long, Long> {

    public Day11(List<String> input) {
        super(input);
    }

    @Override
    protected Long part1() {
        final List<Monkey> monkeys = parseMonkeys();
        playRounds(20, monkeys, monkey -> monkey.throwItem(worry -> worry.divide(BigInteger.valueOf(3))));
        return calculateResult(monkeys);
    }

    @Override
    protected Long part2() {
        final List<Monkey> monkeys = parseMonkeys();
        final BigInteger mod = monkeys.stream()
                .map(Monkey::getTest)
                .map(MonkeyTest::getOperand)
                .reduce(BigInteger.ONE, BigInteger::multiply);
        playRounds(10000, monkeys, monkey -> monkey.throwItem(worry -> worry.mod(mod)));
        return calculateResult(monkeys);
    }

    List<Monkey> parseMonkeys() {
        final HashMap<Integer, List<String>> monkeys = new HashMap<>();
        int monkey = 0;
        for (String row : input) {
            if (row.isEmpty()) {
                monkey += 1;
            } else {
                monkeys.computeIfAbsent(monkey, k -> new ArrayList<>()).add(row);
            }
        }
        return monkeys.values().stream().map(Monkey::parse).collect(Collectors.toList());
    }

    private void playRounds(int rounds, List<Monkey> monkeys, Function<Monkey, MonkeyItem> turnFn) {
        for (int i = 0; i < rounds; i++) {
            for (final Monkey monkey : monkeys) {
                while (monkey.hasItems()) {
                    final MonkeyItem item = turnFn.apply(monkey);
                    monkeys.get(item.toMonkey()).receive(item.itemWorryLevel());
                }
            }
        }
    }

    private static Long calculateResult(final List<Monkey> monkeys) {
        return monkeys.stream()
                .map(Monkey::getInspections)
                .sorted(Comparator.reverseOrder())
                .limit(2)
                .reduce(1L, (a, b) -> a * b);
    }
}
