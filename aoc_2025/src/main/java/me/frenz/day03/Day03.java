
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
                .map(BatteryBank::new)
                .mapToLong(batteryBank -> batteryBank.findLargestPossibleJoltage(2))
                .sum();
    }

    @Override
    protected Long part2() {
        return input.stream()
                .map(BatteryBank::new)
                .mapToLong(batteryBank -> batteryBank.findLargestPossibleJoltage(12))
                .sum();
    }

    record BatteryBank(String joltages) {

        long findLargestPossibleJoltage(int digits) {
            // create a temporary array to retain positional information about highest numbers
            final var bankArray = new int[joltages.length()];
            Arrays.fill(bankArray, -1);

            // locate the highest numbers
            final List<JoltageIndex> highestNumbers = new ArrayList<>();
            var current = JoltageIndex.NONE;
            var remainingBank = joltages;
            for (int j = 0; j < digits; j++) {
                current = highestJoltage(remainingBank);
                remainingBank = remainingJoltages(current, remainingBank);
                highestNumbers.add(current);
            }

            // insert found numbers into the temporary array at the correct locations
            for (JoltageIndex joltage : highestNumbers) {
                bankArray[joltage.index] = joltage.value;
            }
            // collapse the array by removing all "filler values" and concatenating the remainder to a string
            final var resultStr = Arrays.stream(bankArray)
                    .filter(it -> it > 0)
                    .mapToObj(Integer::toString)
                    .collect(Collectors.joining());

            // parse the resulting string back into a number
            return Long.parseLong(resultStr);
        }

        /**
         * Find the highest number in a given string, with its index.
         * @param bank The string to search in
         * @return The highest number in a given string, with its index.
         */
        private JoltageIndex highestJoltage(String bank) {
            final int value = bank
                    .chars()
                    .mapToObj(Character::toString)
                    .mapToInt(Integer::valueOf)
                    .max()
                    .orElseThrow();
            final int index = bank.indexOf(Integer.toString(value));
            return new JoltageIndex(value, index);
        }

        /**
         * After finding the first joltage, we have to build the search space for the second joltage.
         * We have to retain positional information, so we need to replace it instead of truncating.
         * Additionally, we have to handle the special case where the first value found is at the very last position
         * of the string. In that case we only replace it and return the whole remaining string as we have to search
         * the second value in front of the first one. Otherwise, we replace all prepending values with 0 and return that
         * string.
         * @param first The first found value
         * @param bank The bank in which the value was found
         * @return The new bank to search in, but all values that are irrelevant have been replaced with 0
         */
        private String remainingJoltages(JoltageIndex first, String bank) {
            // TODO: Fix for task 2
            if (JoltageIndex.NONE.equals(first)) {
                return bank;
            }
            String remainingJoltages;
            final boolean isEmpty = bank.substring(0, first.index).isEmpty();
            final boolean onlyHasZerosPrefixed = bank.substring(0, first.index).replaceAll("0", "").isEmpty();
            final boolean onlyHasZerosSuffixed = bank.substring(first.index + 1).replaceAll("0", "").isEmpty();
            if (isEmpty || onlyHasZerosPrefixed) {
                remainingJoltages = "0".repeat(first.index) + "0" + bank.substring(first.index + 1);
            } else if (onlyHasZerosSuffixed) {
                remainingJoltages = bank.substring(0, first.index) + "0".repeat(bank.length() - first.index);
            } else {
                remainingJoltages = "0".repeat(first.index) + "0" + bank.substring(first.index + 1);
            }
            return remainingJoltages;
        }
    }

    record JoltageIndex(int value, int index) {
        static JoltageIndex NONE = new JoltageIndex(0, 0);
    }

}
