
package me.frenz.day06;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.BiFunction;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertLinesMatch;

class Day06Test {

    final List<String> input = """
        123 328  51 64\s
         45 64  387 23\s
          6 98  215 314
        *   +   *   + \s
        """.lines().toList();

    @Test
    void testExampleA() {
        assertEquals(4277556L, new Day06(input).part1());
    }

    @Test
    void testExampleB() {
        assertEquals(3263827L, new Day06(input).part2());
    }

    @Test
    void testExtractOperators() {
        final var map = Day06.extractOperators(input.getLast().toCharArray());
        assertEquals(Set.of(0, 4, 8, 12), map.keySet());
        assertEquals(5, map.get(0).apply(1, 5)); // multiply test
        assertEquals(1, map.get(4).apply(1, 0)); // add test
    }

    @Test
    void testColumnizeUsing() {
        final Map<Integer, List<String>> columnized = new Day06(List.of()).columnizeUsing(input.subList(0, input.size() - 1), new ArrayList<>(List.of(0, 4, 8, 12)));
        assertLinesMatch(List.of("123", " 45", "  6"), columnized.get(0));
        assertLinesMatch(List.of("328", "64 ", "98 "), columnized.get(4));
        assertLinesMatch(List.of(" 51", "387", "215"), columnized.get(8));
        assertLinesMatch(List.of("64 ", "23 ", "314"), columnized.get(12));
    }

    @Test
    void testPivot() {
        assertLinesMatch(List.of(""), new Day06(List.of()).pivot(List.of("")));
        assertLinesMatch(List.of("1"), new Day06(List.of()).pivot(List.of("1")));
        assertLinesMatch(List.of("1", "2", "3"), new Day06(List.of()).pivot(List.of("123")));
        assertLinesMatch(List.of("14", "25", "36"), new Day06(List.of()).pivot(List.of("123", "456")));
        assertLinesMatch(List.of("1", "25", "367"), new Day06(List.of()).pivot(List.of("123", " 56", "  7")));
    }
}
