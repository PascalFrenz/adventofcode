package me.frenz.day04;

import me.frenz.*;

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
            found += countMatchesAt(map, pos.x(), pos.y(), "XMAS");
        }

        return (long) found;
    }

    // check a string in all 8 directions from a given position on a 2d map
    private int countMatchesAt(TaskMap<Character> map, int x, int y, String word) {
        int matchesAtPosition = 0;
        for (Direction dir : Direction.allDirections()) {
            boolean found = true;
            for (int i = 0; i < word.length(); i++) {
                Character c = map.atRelativeFrom(
                        new Position(x, y),
                        dir,
                        i,
                        ' '
                );
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

        Set<Position> validXes = new HashSet<>();
        var it = map.iterator();
        while (it.hasNext()) {
            var pos = it.next();
            if (map.at(pos, ' ') == 'A') {
                boolean upLeftM = Objects.equals(map.atRelativeFrom(
                        pos,
                        Direction.UP_LEFT,
                        1,
                        '.'
                ), 'M');
                boolean upLeftS = Objects.equals(map.atRelativeFrom(
                        pos,
                        Direction.UP_LEFT,
                        1,
                        '.'
                ), 'S');
                boolean upRightM = Objects.equals(map.atRelativeFrom(
                        pos,
                        Direction.UP_RIGHT,
                        1,
                        '.'
                ), 'M');
                boolean upRightS = Objects.equals(map.atRelativeFrom(
                        pos,
                        Direction.UP_RIGHT,
                        1,
                        '.'
                ), 'S');
                boolean downLeftM = Objects.equals(map.atRelativeFrom(
                        pos,
                        Direction.DOWN_LEFT,
                        1,
                        '.'
                ), 'M');
                boolean downLeftS = Objects.equals(map.atRelativeFrom(
                        pos,
                        Direction.DOWN_LEFT,
                        1,
                        '.'
                ), 'S');
                boolean downRightM = Objects.equals(map.atRelativeFrom(
                        pos,
                        Direction.DOWN_RIGHT,
                        1,
                        '.'
                ), 'M');
                boolean downRightS = Objects.equals(map.atRelativeFrom(
                        pos,
                        Direction.DOWN_RIGHT,
                        1,
                        '.'
                ), 'S');

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
