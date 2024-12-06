package me.frenz.day03;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class Day03Test {

    @BeforeEach
    void setUp() {
    }

    @Test
    void testExampleA() {
        var day = new Day03("xmul(2,4)%&mul[3,7]!@^do_not_mul(5,5)+mul(32,64]then(mul(11,8)mul(8,5))".lines().toList());
        var result = day.part1();
        assertEquals(161L, result);
    }

    @Test
    void testExampleB() {
        var day  = new Day03("xmul(2,4)&mul[3,7]!^don't()_mul(5,5)+mul(32,64](mul(11,8)undo()?mul(8,5))".lines().toList());
        var result = day.part2();
        assertEquals(48L, result);
    }


}
