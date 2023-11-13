package me.frenz.day11;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static me.frenz.day11.Seat.*;

class SeatMapTest {

    @Test
    void givenAMapShouldHandleEdgesCorrectly() {
        String rawMap = """
                L#L
                .L.
                L#L
                """;
        final SeatMap map = new SeatMap(rawMap.lines().toList());
        // Top Left
        final Seat[] adjacentTopLeft = map.adjacentSeats(0, 0, 1);
        final Seat[] tl = new Seat[]{
                null,
                null,
                null,
                null,
                OCCUPIED,
                null,
                FLOOR,
                EMPTY
        };
        Assertions.assertArrayEquals(tl, adjacentTopLeft);

        // Top Right
        final Seat[] adjacentTopRight = map.adjacentSeats(2, 0, 1);
        final Seat[] tr = new Seat[]{
                null,
                null,
                null,
                OCCUPIED,
                null,
                EMPTY,
                FLOOR,
                null
        };
        Assertions.assertArrayEquals(tr, adjacentTopRight);

        // Bottom Left
        final Seat[] adjacentBottomLeft = map.adjacentSeats(0, 2, 1);
        final Seat[] bl = new Seat[]{
                null,
                FLOOR,
                EMPTY,
                null,
                OCCUPIED,
                null,
                null,
                null
        };
        Assertions.assertArrayEquals(bl, adjacentBottomLeft);

        // Bottom Right
        final Seat[] adjacentBottomRight = map.adjacentSeats(2, 2, 1);
        final Seat[] br = new Seat[]{
                EMPTY,
                FLOOR,
                null,
                OCCUPIED,
                null,
                null,
                null,
                null
        };
        Assertions.assertArrayEquals(br, adjacentBottomRight);
    }
}
