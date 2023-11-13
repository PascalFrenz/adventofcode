package me.frenz.day12;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.List;

class Day12Test {

    @Test
    void testParseInput() {
        final List<String> input = """
                F10
                N3
                F7
                R90
                F11
                """.lines().toList();

        final Ship ship = new Ship(0, 0);
        input.forEach(ship::move);

        Assertions.assertEquals(17, ship.getEast());
        Assertions.assertEquals(-8, ship.getNorth());
    }
}
