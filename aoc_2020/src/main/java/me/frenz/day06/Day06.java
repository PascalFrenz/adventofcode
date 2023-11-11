package me.frenz.day06;

import me.frenz.Day;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.function.BiConsumer;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class Day06 extends Day<Integer, Integer> {

    public static final String ABC = "abcdefghijklmnopqrstuvwxyz";

    public Day06(List<String> input) {
        super(input);
    }

    @Override
    protected Integer part1() {
        return calculateSum(
                input,
                HashSet::new,
                (groupAnswers, input) -> groupAnswers.addAll(input.transform(Day06::toCharacterList))
        );
    }

    @Override
    protected Integer part2() {
        return calculateSum(
                input,
                () -> new HashSet<>(ABC.transform(Day06::toCharacterList)),
                (groupAnswers, input) -> groupAnswers.retainAll(input.transform(Day06::toCharacterList))
        );
    }

    private static int calculateSum(
            final List<String> input,
            final Supplier<Set<Character>> initValue,
            final BiConsumer<Set<Character>, String> compute
    ) {
        final Stream.Builder<Set<Character>> builder = Stream.builder();

        Set<Character> groupInput = initValue.get();
        for (String answer : input) {
            if (answer.isEmpty()) {
                builder.accept(new HashSet<>(groupInput));
                groupInput = initValue.get();
            } else {
                compute.accept(groupInput, answer);
            }
        }

        if (!groupInput.isEmpty())
            builder.accept(new HashSet<>(groupInput));

        return builder.build().mapToInt(Set::size).sum();
    }

    private static List<Character> toCharacterList(final String s) {
        return IntStream.range(0, s.length())
                .mapToObj(s::charAt)
                .collect(Collectors.toList());
    }
}
