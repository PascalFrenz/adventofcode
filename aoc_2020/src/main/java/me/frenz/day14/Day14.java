package me.frenz.day14;

import me.frenz.Day;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Day14 extends Day<Long, Integer> {

    public Day14(List<String> input) {
        super(input);
    }

    @Override
    protected Long part1() {
        final Map<Long, Long> memory = new HashMap<>();
        Map<Integer, Integer> shiftOps = new HashMap<>();
        for (String line : input) {
            if (line.startsWith("mask = ")) {
                shiftOps = parseShiftOps(line);
            } else {
                long memAddr = Long.parseLong(line.substring(line.indexOf("[") + 1, line.indexOf("]")));
                long value = applyShiftOps(shiftOps, Long.parseLong(line.split(" = ")[1]));
                memory.put(memAddr, value);
            }
        }

        return memory.values().stream().reduce(0L, Long::sum);
    }

    private static long applyShiftOps(Map<Integer, Integer> shiftOps, long initialValue) {
        long value = initialValue;
        for (Map.Entry<Integer, Integer> op : shiftOps.entrySet()) {
            long mask = 1L << op.getKey();
            if (op.getValue() == 0) {
                value &= mask ^ Long.MAX_VALUE;
            } else { // must be 1
                value |= mask;
            }
        }
        return value;
    }

    private static Map<Integer, Integer> parseShiftOps(String line) {
        final Map<Integer, Integer> newOps = new HashMap<>();
        final String mask = line.substring(7); // length of "mask = "
        char[] charArray = mask.toCharArray();
        for (int i = 0; i < charArray.length; i++) {
            char c = charArray[i];
            if (c == '1' || c == '0') {
                newOps.put(charArray.length - i - 1, c == '1' ? 1 : 0);
            }
        }
        return newOps;
    }

    @Override
    protected Integer part2() {
        return 0;
    }
}
