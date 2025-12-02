
package me.frenz.day02;

import me.frenz.Day;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

public class Day02 extends Day<Long, Integer> {

    public Day02(List<String> input) {
        super(input);
    }

    @Override
    protected Long part1() {
        return Arrays.stream(input.getFirst().split(",")) // whole input is on first line
                .map(IdRange::from)
                .flatMap(it -> it.findInvalidIds().stream())
                .mapToLong(ID::number)
                .sum();
    }

    @Override
    protected Integer part2() {
        return 0;
    }

    record IdRange(ID start, ID end) {
        static IdRange from(String string) {
            if (!string.contains("-")) {
                throw new IllegalArgumentException();
            }

            String[] split = string.split("-");
            try {
                final var start = Long.parseLong(split[0]);
                final var end = Long.parseLong(split[1]);

                return new IdRange(new ID(start), new ID(end));
            } catch (NumberFormatException e) {
                throw new IllegalArgumentException("Long too long!");
            }
        }

        /**
         * Use brute force again. Seems to keep up with the speed
         * @return All invalid IDs in this ID range
         */
        List<ID> findInvalidIds() {
            var result = new ArrayList<ID>();
            var current = this.start;
            while(current.number <= this.end.number) {
                if (current.isInvalid()) {
                    result.add(current);
                }
                current = current.next();
            }
            return result;
        }
    }

    record ID(long number) {
        /**
         * Find the invalid IDs by looking for any ID that is made only of some sequence of digits repeated twice.
         * So, 55 (5 twice), 6464 (64 twice), and 123123 (123 twice) would all be invalid IDs.
         * @return Whether the given number is invalid or not
         */
        boolean isInvalid() {
            final var numStr = String.valueOf(number);
            final var len = numStr.length();

            // Must have even length to be splittable into two equal parts
            if (len % 2 != 0) {
                return false;
            }

            final var half = len / 2;
            final var firstHalf = numStr.substring(0, half);
            final var secondHalf = numStr.substring(half);

            return firstHalf.equals(secondHalf);
        }

        ID next() {
            return new ID(number + 1);
        }
    }

}
