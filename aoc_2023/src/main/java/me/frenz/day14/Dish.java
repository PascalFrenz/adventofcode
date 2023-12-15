package me.frenz.day14;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Dish {

    private final char[][] map;

    private interface Swapper {
        void swap(int row, int col);
    }

    private interface BoundChecker {
        boolean isValid(int row, int col);
    }

    public Dish(final List<String> input) {
        map = input.stream()
                .map(String::toCharArray)
                .toArray(char[][]::new);
    }

    public Dish tilt(Direction direction) {
        return switch (direction) {
            case NORTH -> moveRocksNorth();
            case SOUTH -> moveRocksSouth();
            case EAST -> moveRocksEast();
            case WEST -> moveRocksWest();
        };
    }

    public void spinDishNCyclesEfficient(long cycleCount) {
        final List<String> previousStates = new ArrayList<>();
        long cycleCounter = 0;

        // Keep spinning until the maximum number of cycles has been achieved
        while (cycleCounter < cycleCount) {

            final String currentState = Arrays.deepToString(map);
            if (previousStates.contains(currentState)) {

                // We found a cycle, let's calculate how many cycles are left after finishing the full cycles.
                int cycle = previousStates.indexOf(currentState);
                int cycleLength = previousStates.size() - cycle;
                long remainingCycles = (cycleCount - cycleCounter) % cycleLength;

                for (int i = 0; i < remainingCycles; i++) {
                    spinCycle(); // Simulate remaining cycles that didn't fit into full cycles.
                    cycleCounter++;
                }

                // Break from the loop after completing remaining cycles
                break;
            } else {
                previousStates.add(currentState); // Add this state into the list
                spinCycle(); // Simulate next cycle
                cycleCounter++;
            }
        }
    }

    public int calculateLoad() {
        int totalLoad = 0;

        int rows = map.length;
        int columns = map[0].length;

        for (int row = 0; row < rows; row++) {
            for (int col = 0; col < columns; col++) {
                if (map[row][col] == 'O') {
                    totalLoad += (rows - row);
                }
            }
        }

        return totalLoad;
    }

    private void spinCycle() {
        tilt(Direction.NORTH);
        tilt(Direction.WEST);
        tilt(Direction.SOUTH);
        tilt(Direction.EAST);
    }

    private Dish moveRocksNorth() {
        moveRocksGeneric(
                (r, c) -> { char temp = map[r][c]; map[r][c] = map[r + 1][c]; map[r + 1][c] = temp; },
                (r, c) -> r < map.length - 1 && map[r][c] == '.' && map[r + 1][c] == 'O'
        );
        return this;
    }

    private Dish moveRocksSouth() {
        moveRocksGeneric(
                (r, c) -> { char temp = map[r][c]; map[r][c] = map[r - 1][c]; map[r - 1][c] = temp; },
                (r, c) -> r > 0 && map[r][c] == '.' && map[r - 1][c] == 'O'
        );
        return this;
    }

    private Dish moveRocksEast() {
        moveRocksGeneric(
                (r, c) -> { char temp = map[r][c]; map[r][c] = map[r][c - 1]; map[r][c - 1] = temp; },
                (r, c) -> c > 0 && map[r][c] == '.' && map[r][c - 1] == 'O'
        );
        return this;
    }

    private Dish moveRocksWest() {
        moveRocksGeneric(
                (r, c) -> { char temp = map[r][c]; map[r][c] = map[r][c + 1]; map[r][c + 1] = temp; },
                (r, c) -> c < map[r].length - 1 && map[r][c] == '.' && map[r][c + 1] == 'O'
        );
        return this;
    }

    private void moveRocksGeneric(Swapper swapper, BoundChecker boundChecker) {
        int rows = map.length;
        int columns = map[0].length;
        boolean moved;

        do {
            moved = false;
            for (int row = 0; row < rows; row++) {
                for (int col = 0; col < columns; col++) {
                    if (boundChecker.isValid(row, col)) {
                        swapper.swap(row, col);
                        moved = true;
                    }
                }
            }
        } while (moved);
    }
}

