
package me.frenz.day06;

import me.frenz.Day;

import java.util.Arrays;
import java.util.List;
import java.util.stream.IntStream;
import java.util.stream.LongStream;

public class Day06 extends Day<Integer, Long> {

    public Day06(List<String> input) {
        super(input);
    }

    @Override
    protected Integer part1() {
        final List<Integer> times = Arrays.stream(input.get(0).split(":")[1].trim().split(" ")).filter(it -> !it.isEmpty()).mapToInt(Integer::parseInt).boxed().toList();
        final List<Integer> distances = Arrays.stream(input.get(1).split(":")[1].trim().split(" ")).filter(it -> !it.isEmpty()).mapToInt(Integer::parseInt).boxed().toList();
        int result = 1;
        for (int i = 0; i < times.size(); i++) {
            final int time = times.get(i);
            final int distance = distances.get(i);

            result *= (int) IntStream
                    .rangeClosed(0, time)
                    .filter(j -> j * (time - j) > distance)
                    .count();
        }
        return result;
    }

    @Override
    protected Long part2() {
        final long time = Long.parseLong(input.get(0).split(":")[1].trim().replace(" ", ""));
        final long distance = Long.parseLong(input.get(1).split(":")[1].trim().replace(" ", ""));
        long result = 1;
        result *= LongStream
                .rangeClosed(0, time)
                .filter(j -> j * (time - j) > distance)
                .count();
        return result;
    }

}
