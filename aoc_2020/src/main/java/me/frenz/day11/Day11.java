package me.frenz.day11;

import me.frenz.Day;

import java.util.Arrays;
import java.util.List;
import java.util.function.BiFunction;

public class Day11 extends Day<Integer, Integer> {

    private final SeatMap seatMap;

    public Day11(List<String> input) {
        super(input);
        seatMap = new SeatMap(input);
    }

    @Override
    protected Integer part1() {
        boolean layoutChanged = true;
        while (layoutChanged) {
            layoutChanged = seatMap.doTurn((x, y) -> seatMap.adjacentSeats(x, y, 1), 3);
        }
        return seatMap.getOccupiedSeats();
    }

    @Override
    protected Integer part2() {
        boolean layoutChanged = true;
        while (layoutChanged) {
            layoutChanged = seatMap.doTurn(seatMap::visibleAdjacentSeats, 4);
        }
        return seatMap.getOccupiedSeats();
    }

    enum Seat {
        FLOOR("."),
        EMPTY("L"),
        OCCUPIED("#");

        private final String value;

        Seat(final String value) {
            this.value = value;
        }

        static Seat[] from(final String line) {
            final Seat[] seats = new Seat[line.length()];
            char[] charArray = line.toCharArray();

            for (int i = 0; i < charArray.length; i++) {
                seats[i] = Seat.of(charArray[i]);
            }

            return seats;
        }

        static Seat of(char sign) {
            switch (sign) {
                case '.' -> {
                    return FLOOR;
                }
                case 'L' -> {
                    return EMPTY;
                }
                case '#' -> {
                    return OCCUPIED;
                }
                default -> throw new IllegalArgumentException("Character not supported: %s".formatted(sign));
            }
        }


        @Override
        public String toString() {
            return value;
        }
    }

    static class SeatMap {

        private Seat[][] map;
        private boolean leaveTurn;

        SeatMap(final List<String> input) {
            int rows = input.size();
            map = new Seat[rows][];
            for (int y = 0; y < map.length; y++) {
                map[y] = Seat.from(input.get(y));
            }
            leaveTurn = false;
        }

        @Override
        public String toString() {
            final StringBuilder out = new StringBuilder();
            for (Seat[] row : map) {
                for (Seat seat : row) {
                    out.append(seat.value);
                }
                out.append(System.lineSeparator());
            }
            return out.toString();
        }

        Seat[] adjacentSeats(int x, int y, int range) {
            assert map[0] != null;
            assert x >= 0;
            assert y >= 0;

            final int mapWidth = map[0].length - 1;
            final int mapHeight = map.length - 1;

            assert x <= mapWidth;
            assert y <= mapHeight;

            Seat[] adjacentSeats = new Seat[8]; // up, down, left, right and diagonal seats

            adjacentSeats[0] = x - range >= 0 && y - range >= 0 ? map[y - range][x - range] : null;
            adjacentSeats[1] = y - range >= 0 ? map[y - range][x] : null;
            adjacentSeats[2] = x + range <= mapWidth && y - range >= 0 ? map[y - range][x + range] : null;
            adjacentSeats[3] = x - range >= 0 ? map[y][x - range] : null;
            adjacentSeats[4] = x + range <= mapWidth ? map[y][x + range] : null;
            adjacentSeats[5] = x - range >= 0 && y + range <= mapHeight ? map[y + range][x - range] : null;
            adjacentSeats[6] = y + range <= mapHeight ? map[y + range][x] : null;
            adjacentSeats[7] = x + range <= mapWidth && y + range <= mapHeight ? map[y + range][x + range] : null;

            return adjacentSeats;
        }

        Seat[] visibleAdjacentSeats(int x, int y) {
            Seat[] visibleAdjacent = new Seat[8];
            Arrays.fill(visibleAdjacent, Seat.FLOOR);

            int range = 1;
            while (Arrays.asList(visibleAdjacent).contains(Seat.FLOOR)) {
                Seat[] adjacentAtRange = adjacentSeats(x, y, range);
                for (int i = 0; i < adjacentAtRange.length; i++) {
                    Seat seat = adjacentAtRange[i];
                    if (Seat.FLOOR.equals(visibleAdjacent[i]) && !Seat.FLOOR.equals(adjacentAtRange[i])) {
                        visibleAdjacent[i] = seat;
                    }
                }
                range++;
            }
            return visibleAdjacent;
        }

        boolean shouldOccupy(int x, int y, final Seat[] adjacentSeats) {
            final boolean isEmpty = Seat.EMPTY.equals(map[y][x]);
            final boolean hasOccupiedAdjacent = !Arrays.asList(adjacentSeats).contains(Seat.OCCUPIED);
            return !leaveTurn && isEmpty && hasOccupiedAdjacent;
        }

        boolean shouldLeave(int x, int y, Seat[] adjacentSeats, int toleratedPeople) {
            final boolean isOccupied = Seat.OCCUPIED.equals(map[y][x]);
            final long adjacentOccupiedSeats = Arrays.stream(adjacentSeats).filter(Seat.OCCUPIED::equals).count();
            return leaveTurn && isOccupied && adjacentOccupiedSeats > toleratedPeople;
        }

        boolean doTurn(BiFunction<Integer, Integer, Seat[]> adjacencyFunction, int toleratedPeople) {
            boolean didChange = false;
            final Seat[][] newMap = new Seat[map.length][];
            for (int y = 0; y < map.length; y++) {
                Seat[] row = map[y];

                newMap[y] = new Seat[row.length];
                System.arraycopy(row, 0, newMap[y], 0, row.length);

                for (int x = 0; x < row.length; x++) {
                    if (shouldOccupy(x, y, adjacencyFunction.apply(x, y))) {
                        newMap[y][x] = Seat.OCCUPIED;
                        didChange = true;
                    }
                    if (shouldLeave(x, y, adjacencyFunction.apply(x, y), toleratedPeople)) {
                        newMap[y][x] = Seat.EMPTY;
                        didChange = true;
                    }
                }
            }
            leaveTurn = !leaveTurn; // Every second turn, leaving is processed
            map = newMap;
            return didChange;
        }

        int getOccupiedSeats() {
            return (int) Arrays.stream(map).flatMap(Arrays::stream).filter(Seat.OCCUPIED::equals).count();
        }
    }
}
