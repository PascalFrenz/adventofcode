package me.frenz.day06;

import me.frenz.Day;
import me.frenz.Direction;
import me.frenz.Position;
import me.frenz.TaskMap;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Set;
import java.util.function.Function;

public class Day06 extends Day<Long, Long> {

    public Day06(List<String> input) {
        super(input);
    }

    @Override
    protected Long part1() {
        TaskMap<Character> map = TaskMap.fromString(input, Function.identity());
        Position guardPosition = findGuardPosition(map);
        Direction guardDirection = Direction.UP;
        Set<Position> visitedPositions = new HashSet<>();
        visitedPositions.add(guardPosition);

        while (true) {
            Position nextPosition = guardPosition.move(guardDirection);
            if (map.at(nextPosition, '.') == '#') {
                guardDirection = guardDirection.turnRight();
            } else {
                guardPosition = nextPosition;
                visitedPositions.add(guardPosition);
                if (!map.isWithinBounds(guardPosition)) {
                    break;
                }
            }
        }

        return (long) visitedPositions.size() - 1;
    }

    @Override
    protected Long part2() {
        TaskMap<Character> map = TaskMap.fromString(input, Function.identity());
        Position guardPosition = findGuardPosition(map);
        Set<Position> validObstructionPositions = new HashSet<>();

        var iter = map.iterator();
        while (iter.hasNext()) {
            Position p = iter.next();
            if (p.equals(guardPosition) || map.at(p, '.') == '#') {
                continue;
            }

            if (causesLoop(map, guardPosition, p)) {
                validObstructionPositions.add(p);
            }
        }

        return (long) validObstructionPositions.size();
    }

    private boolean causesLoop(TaskMap<Character> map, Position guardPosition, Position obstructionPosition) {
        TaskMap<Character> modifiedMap = map.set(obstructionPosition, '#');
        Direction guardDirection = Direction.UP;
        Set<Position> visitedPositions = new HashSet<>();
        Queue<Position> queue = new LinkedList<>();
        queue.add(guardPosition);

        while (!queue.isEmpty()) {
            Position currentPosition = queue.poll();
            if (visitedPositions.contains(currentPosition)) {
                return true;
            }
            visitedPositions.add(currentPosition);

            Position nextPosition = currentPosition.move(guardDirection);
            if (modifiedMap.at(nextPosition, '.') == '#') {
                guardDirection = guardDirection.turnRight();
            } else {
                queue.add(nextPosition);
                if (!modifiedMap.isWithinBounds(nextPosition)) {
                    return false;
                }
            }
        }

        return false;
    }

    private Position findGuardPosition(TaskMap<Character> map) {
        var iter = map.iterator();
        while (iter.hasNext()) {
            Position p = iter.next();
            if (map.at(p, '.') == '^') {
                return p;
            }
        }
        throw new IllegalStateException("No guard position found");
    }
}