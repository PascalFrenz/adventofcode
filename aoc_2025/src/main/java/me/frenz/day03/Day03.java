
package me.frenz.day03;

import me.frenz.Day;

import java.util.*;
import java.util.stream.Collectors;

public class Day03 extends Day<Long, Long> {

    public Day03(List<String> input) {
        super(input);
    }

    @Override
    protected Long part1() {
        return input.stream()
                .map(in -> new BatteryBank(2, in))
                .mapToLong(BatteryBank::findLargestJoltage)
                .sum();
    }

    @Override
    protected Long part2() {
        return input.stream()
                .map(in -> new BatteryBank(12, in))
                .mapToLong(BatteryBank::findLargestJoltage)
                .sum();
    }

    static class BatteryBank {

        private final int capacity;
        private final String batteries;
        private final Stack<Integer> bank = new Stack<>();

        public BatteryBank(int capacity, String batteries) {
            this.capacity = capacity;
            this.batteries = batteries;
        }

        long findLargestJoltage() {
            char[] charArray = batteries.toCharArray();
            for (int i = 0; i < charArray.length; i++) {
                char c = charArray[i];
                var val = Integer.valueOf(String.valueOf(c));
                while (!bank.isEmpty() && bank.peek() < val && capacity - bank.size() < batteries.length() - i) {
                    bank.pop();
                }
                if (bank.size() < capacity) {
                    bank.push(val);
                }
            }
            final String resultStr = bank.stream().map(String::valueOf).collect(Collectors.joining());
            return Long.parseLong(resultStr);
        }
    }

}
