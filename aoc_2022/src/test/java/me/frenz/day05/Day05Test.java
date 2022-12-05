package me.frenz.day05;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.List;
import java.util.Stack;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.assertEquals;

class Day05Test {

    private Day05 dut;

    @BeforeEach
    void setUp() {
        String EXAMPLE_INPUT = """
                    [D]   \s
                [N] [C]   \s
                [Z] [M] [P]
                 1   2   3\s
                                
                move 1 from 2 to 1
                move 3 from 1 to 3
                move 2 from 2 to 1
                move 1 from 1 to 2
                """;
        dut = new Day05(EXAMPLE_INPUT.lines().collect(Collectors.toList()));
    }

    @Test
    void part1() {
        assertEquals("CMZ", dut.part1());
    }

    @Test
    void part2() {
        assertEquals("MCD", dut.part2());
    }

    @Test
    void givenInputStacks_shouldParseCorrectly() {
        final HashMap<Integer, Stack<Character>> actual = dut.parseInputStacks();
        final Stack<Character> first = new Stack<>();
        first.insertElementAt('N', 0);
        first.insertElementAt('Z', 0);
        assertEquals(first, actual.get(1));
        final Stack<Character> second = new Stack<>();
        second.insertElementAt('D', 0);
        second.insertElementAt('C', 0);
        second.insertElementAt('M', 0);
        assertEquals(second, actual.get(2));
        final Stack<Character> third = new Stack<>();
        third.insertElementAt('P', 0);
        assertEquals(third, actual.get(3));
    }

    @Test
    void givenCraneMovements_shouldParseCorrectly() {
        final List<CraneMovement> craneMovements = dut.parseCraneMovements();
        final List<CraneMovement> expected = List.of(
                new CraneMovement(1, 2, 1),
                new CraneMovement(3, 1, 3),
                new CraneMovement(2, 2, 1),
                new CraneMovement(1, 1, 2)
        );
        assertEquals(expected, craneMovements);
    }

}
