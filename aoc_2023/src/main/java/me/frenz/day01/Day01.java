package me.frenz.day01;

import me.frenz.Day;

import java.util.List;

public class Day01 extends Day<Integer, Integer> {

    public Day01(List<String> input) {
        super(input);
    }

    @Override
    protected Integer part1() {
        int result = 0;

        for (String line : input) {
            final String fromStart = findFromStart(line, false);
            final String fromEnd = findFromEnd(line, false);
            result += Integer.parseInt(fromStart.concat(fromEnd));
        }

        return result;
    }

    @Override
    protected Integer part2() {
        int result = 0;

        for (String line : input) {
            final String fromStart = findFromStart(line, true);
            final String fromEnd = findFromEnd(line, true);
            result += Integer.parseInt(fromStart.concat(fromEnd));
        }

        return result;
    }

    private String findFromStart(String line, boolean withWords) {
        for (int i = 1; i <= line.length(); i++) {
            final String window = line.substring(0, i);
            final String number = getNumberFrom(window, withWords);
            if (!number.isEmpty()) {
                return number;
            }
        }
        throw new IllegalArgumentException("No number on line! [%s]".formatted(line));
    }

    private String findFromEnd(String line, boolean withWords) {
        for (int i = line.length() - 1; i >= 0; i--) {
            final String window = line.substring(i);
            final String number = getNumberFrom(window, withWords);
            if (!number.isEmpty()) {
                return number;
            }
        }
        throw new IllegalArgumentException("No number on line! [%s]".formatted(line));
    }

    private static String getNumberFrom(String window, boolean withWords) {
        if ((withWords && window.contains("one")) || window.contains("1")) {
            return "1";
        }
        if ((withWords && window.contains("two")) || window.contains("2")) {
            return "2";
        }
        if ((withWords && window.contains("three")) || window.contains("3")) {
            return "3";
        }
        if ((withWords && window.contains("four")) || window.contains("4")) {
            return "4";
        }
        if ((withWords && window.contains("five")) || window.contains("5")) {
            return "5";
        }
        if ((withWords && window.contains("six")) || window.contains("6")) {
            return "6";
        }
        if ((withWords && window.contains("seven")) || window.contains("7")) {
            return "7";
        }
        if ((withWords && window.contains("eight")) || window.contains("8")) {
            return "8";
        }
        if ((withWords && window.contains("nine")) || window.contains("9")) {
            return "9";
        }
        return "";
    }
}
