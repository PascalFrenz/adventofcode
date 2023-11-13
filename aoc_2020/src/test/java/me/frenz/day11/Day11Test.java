package me.frenz.day11;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class Day11Test {

    @Test
    void testExampleA() {
        List<String> input = """
                L.LL.LL.LL
                LLLLLLL.LL
                L.L.L..L..
                LLLL.LL.LL
                L.LL.LL.LL
                L.LLLLL.LL
                ..L.L.....
                LLLLLLLLLL
                L.LLLLLL.L
                L.LLLLL.LL
                """.lines().toList();
        final Day11 day = new Day11(input);
        assertEquals(37, day.part1());
    }

    @Test
    void testExampleB() {
        List<String> input = """
                L.LL.LL.LL
                LLLLLLL.LL
                L.L.L..L..
                LLLL.LL.LL
                L.LL.LL.LL
                L.LLLLL.LL
                ..L.L.....
                LLLLLLLLLL
                L.LLLLLL.L
                L.LLLLL.LL
                """.lines().toList();
        final Day11 day = new Day11(input);
        assertEquals(26, day.part2());
    }
}
