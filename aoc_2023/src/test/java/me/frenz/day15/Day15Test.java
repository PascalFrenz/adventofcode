
package me.frenz.day15;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class Day15Test {

    final List<String> input = """
        rn=1,cm-,qp=3,cm=2,qp-,pc=4,ot=9,ab=5,pc-,pc=6,ot=7
        """.lines().toList();

    @Test
    void testExampleA() {
        assertEquals(1320, new Day15(input).part1());
    }

    @Test
    void testExampleB() {
        assertEquals(145, new Day15(input).part2());
    }
}
