package me.frenz.day09;

import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.assertEquals;

class Day09Test {

    @Test
    void part1() {
        final List<String> input = """
                R 4
                U 4
                L 3
                D 1
                R 4
                D 1
                L 5
                R 2
                """.lines().collect(Collectors.toList());
        assertEquals(13, new Day09(input).part1());
    }

    @Test
    void part2() {
        List<String> largerInput = """
                R 5
                U 8
                L 8
                D 3
                R 17
                D 10
                L 25
                U 20
                """.lines().collect(Collectors.toList());
        assertEquals(36, new Day09(largerInput).part2());
    }
}
