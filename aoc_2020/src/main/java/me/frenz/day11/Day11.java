package me.frenz.day11;

import me.frenz.Day;

import java.util.List;

public class Day11 extends Day<Integer, Integer> {

    public Day11(List<String> input) {
        super(input);
    }

    @Override
    protected Integer part1() {
        final SeatMap seatMap = new SeatMap(input);
        boolean layoutChanged = true;
        while (layoutChanged) {
            layoutChanged = seatMap.doTurn((x, y) -> seatMap.adjacentSeats(x, y, 1), 3);
        }
        return seatMap.getOccupiedSeats();
    }

    @Override
    protected Integer part2() {
        final SeatMap seatMap = new SeatMap(input);
        boolean layoutChanged = true;
        while (layoutChanged) {
            layoutChanged = seatMap.doTurn(seatMap::visibleAdjacentSeats, 4);
        }
        return seatMap.getOccupiedSeats();
    }

}
