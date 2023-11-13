package me.frenz.day12;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.List;

class Day12Test {

    @Test
    void testExampleA() {
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

    @Test
    void testExampleB() {
        final List<String> input = """
                F10
                N3
                F7
                R90
                F11
                """.lines().toList();

        final Ship ship = new Ship(0, 0);
        input.forEach(ship::moveWithWaypoint);

        Assertions.assertEquals(214, ship.getEast());
        Assertions.assertEquals(-72, ship.getNorth());
    }
}
