
package me.frenz.day08;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class Day08Test {

    final List<String> input = """
            RL
                    
            AAA = (BBB, CCC)
            BBB = (DDD, EEE)
            CCC = (ZZZ, GGG)
            DDD = (DDD, DDD)
            EEE = (EEE, EEE)
            GGG = (GGG, GGG)
            ZZZ = (ZZZ, ZZZ)
            """.lines().toList();

    final List<String> otherInput = """
            LLR
                        
            AAA = (BBB, BBB)
            BBB = (AAA, ZZZ)
            ZZZ = (ZZZ, ZZZ)
            """.lines().toList();

    @Test
    void testExampleA() {
        assertEquals(2, new Day08(input).part1());
        assertEquals(6, new Day08(otherInput).part1());
    }

    @Test
    void testExampleB() {
        final List<String> input = """
                LR
                                
                11A = (11B, XXX)
                11B = (XXX, 11Z)
                11Z = (11B, XXX)
                22A = (22B, XXX)
                22B = (22C, 22C)
                22C = (22Z, 22Z)
                22Z = (22B, 22B)
                XXX = (XXX, XXX)
                """.lines().toList();
        assertEquals(6, new Day08(input).part2());
    }
}
