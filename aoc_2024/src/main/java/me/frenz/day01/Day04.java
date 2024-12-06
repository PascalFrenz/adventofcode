package me.frenz.day01;

import me.frenz.Day;
import me.frenz.Direction;
import me.frenz.Pair;
import me.frenz.TaskMap;

import java.util.*;
import java.util.function.Function;

public class Day04 extends Day<Long, Long> {

    public Day04(List<String> input) {
        super(input);
    }

    @Override
    protected Long part1() {
        TaskMap<Character> map = TaskMap.fromString(input, Function.identity());

        int found = 0;
        var it = map.iterator();
        while (it.hasNext()) {
            var pos = it.next();
            found += countMatchesAt(map, pos.left(), pos.right(), "XMAS");
        }

        return (long) found;
    }

    // check a string in all 8 directions from a given position on a 2d map
    private int countMatchesAt(TaskMap<Character> map, int x, int y, String word) {
        int matchesAtPosition = 0;
        for (Direction dir : Direction.allDirections()) {
            boolean found = true;
            for (int i = 0; i < word.length(); i++) {
                Character c = map.atRelativeFrom(x, y, dir, i, ' ');
                if (c != word.charAt(i)) {
                    found = false;
                    break;
                }
            }
            if (found) {
                matchesAtPosition++;
            }
        }
        return matchesAtPosition;
    }

    @Override
    protected Long part2() {
        TaskMap<Character> map = TaskMap.fromString(
                input,
                Function.identity()
        );

        Set<Pair<Integer, Integer>> validXes = new HashSet<>();
        var it = map.iterator();
        while (it.hasNext()) {
            var pos = it.next();
            int x = pos.left();
            int y = pos.right();
            if (map.at(x, y, ' ') == 'A') {
                boolean upLeftM = Objects.equals(map.atRelativeFrom(x, y, Direction.UP_LEFT, 1, '.'), 'M');
                boolean upLeftS = Objects.equals(map.atRelativeFrom(x, y, Direction.UP_LEFT, 1, '.'), 'S');
                boolean upRightM = Objects.equals(map.atRelativeFrom(x, y, Direction.UP_RIGHT, 1, '.'), 'M');
                boolean upRightS = Objects.equals(map.atRelativeFrom(x, y, Direction.UP_RIGHT, 1, '.'), 'S');
                boolean downLeftM = Objects.equals(map.atRelativeFrom(x, y, Direction.DOWN_LEFT, 1, '.'), 'M');
                boolean downLeftS = Objects.equals(map.atRelativeFrom(x, y, Direction.DOWN_LEFT, 1, '.'), 'S');
                boolean downRightM = Objects.equals(map.atRelativeFrom(x, y, Direction.DOWN_RIGHT, 1, '.'), 'M');
                boolean downRightS = Objects.equals(map.atRelativeFrom(x, y, Direction.DOWN_RIGHT, 1, '.'), 'S');

                boolean validCross = (upLeftM && downRightS && downLeftM && upRightS)
                                     || (upLeftS && downRightM && downLeftS && upRightM)
                                     || (upLeftM && downRightS && downLeftS && upRightM)
                                     || (upLeftS && downRightM && downLeftM && upRightS);

                if (validCross) {
                    validXes.add(pos);
                }
            }

        }
        return (long) validXes.size();
    }
}
