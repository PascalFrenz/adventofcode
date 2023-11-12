package me.frenz.day11;

import me.frenz.day11.Day11.SeatMap;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.List;

import static me.frenz.day11.Day11.Seat.*;

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
        final Day11.Seat[] adjacentTopLeft = map.adjacentSeats(0, 0, 1);
        final Day11.Seat[] tl = new Day11.Seat[]{
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
        final Day11.Seat[] adjacentTopRight = map.adjacentSeats(2, 0, 1);
        final Day11.Seat[] tr = new Day11.Seat[]{
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
        final Day11.Seat[] adjacentBottomLeft = map.adjacentSeats(0, 2, 1);
        final Day11.Seat[] bl = new Day11.Seat[]{
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
        final Day11.Seat[] adjacentBottomRight = map.adjacentSeats(2, 2, 1);
        final Day11.Seat[] br = new Day11.Seat[]{
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

    @Test
    void name() {
        List<String> rawMap = """
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

        final SeatMap seatMap = new SeatMap(rawMap);
        while (seatMap.doTurn(seatMap::visibleAdjacentSeats, 4)) {
            // just process turns
        }
        System.out.println(seatMap);
        Assertions.assertEquals(26, seatMap.getOccupiedSeats());
    }
}
