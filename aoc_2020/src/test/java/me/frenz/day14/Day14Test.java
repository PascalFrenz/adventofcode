package me.frenz.day14;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.List;

class Day14Test {

    @Test
    void testExample1() {
        final List<String> input = """
                mask = XXXXXXXXXXXXXXXXXXXXXXXXXXXXX1XXXX0X
                mem[8] = 11
                mem[7] = 101
                mem[8] = 0
                """.lines().toList();

        Assertions.assertEquals(165, new Day14(input).part1());
    }
}
