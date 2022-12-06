package me.frenz.day06;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.function.Function;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;


class Day06Test {

    private static final List<String> INPUTS = List.of(
            "mjqjpqmgbljsphdztnvjfqwrcgsmlb",
            "bvwbjplbgvbhsrlpgdmjqwftvncz",
            "nppdvjthqldpwncqszvftbrmjlhg",
            "nznrnfrfntjfmvfwmzdfjlvtqnbhcprsg",
            "zcfzfwzzqfrljwzlrfnpqdbhtmscgvjw"
    );

    private final Function<String, Integer> part1 =
            input -> new Day06(input.lines().toList()).part1();

    private final Function<String, Integer> part2 =
            input -> new Day06(input.lines().toList()).part2();

    @BeforeEach
    void setUp() {
    }

    @Test
    void part1() {
        final Integer[] results = INPUTS.stream().map(part1).toArray(Integer[]::new);
        assertArrayEquals(new Integer[]{7, 5, 6, 10, 11}, results);
    }

    @Test
    void part2() {
        final Integer[] results = INPUTS.stream().map(part2).toArray(Integer[]::new);
        assertArrayEquals(new Integer[]{19,  23, 23, 29, 26}, results);
    }

}