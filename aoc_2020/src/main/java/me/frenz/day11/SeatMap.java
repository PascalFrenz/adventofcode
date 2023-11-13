package me.frenz.day11;

import java.util.Arrays;
import java.util.List;
import java.util.function.BiFunction;

class SeatMap {

    private Seat[][] map;

    SeatMap(final List<String> input) {
        int rows = input.size();
        map = new Seat[rows][];
        for (int y = 0; y < map.length; y++) {
            map[y] = Seat.from(input.get(y));
        }
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
        return isEmpty && hasOccupiedAdjacent;
    }

    boolean shouldLeave(int x, int y, Seat[] adjacentSeats, int toleratedPeople) {
        final boolean isOccupied = Seat.OCCUPIED.equals(map[y][x]);
        final long adjacentOccupiedSeats = Arrays.stream(adjacentSeats).filter(Seat.OCCUPIED::equals).count();
        return isOccupied && adjacentOccupiedSeats > toleratedPeople;
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
        map = newMap;
        return didChange;
    }

    int getOccupiedSeats() {
        return (int) Arrays.stream(map).flatMap(Arrays::stream).filter(Seat.OCCUPIED::equals).count();
    }
}
