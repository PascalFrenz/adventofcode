package me.frenz.day01;

import me.frenz.Day;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Day03 extends Day<Long, Long> {

    public static final Pattern MUL_REGEX = Pattern.compile("mul\\(\\d+,\\d+\\)");
    public static final Pattern DO_REGEX = Pattern.compile("do\\(\\)");
    public static final Pattern DONT_REGEX = Pattern.compile("don't\\(\\)");

    public Day03(List<String> input) {
        super(input);
    }

    @Override
    protected Long part1() {
        String memInstructions = input.stream().reduce("", String::concat);
        return MUL_REGEX.matcher(memInstructions)
                .results()
                .map(res -> res.group().substring(4, res.group().length() - 1).split(","))
                .mapToLong(mul -> {
                    long a = Long.parseLong(mul[0]);
                    long b = Long.parseLong(mul[1]);
                    return a * b;
                })
                .reduce(Long::sum)
                .orElseThrow();
    }

    @Override
    protected Long part2() {
        String memInstructions = input.stream().reduce("", String::concat);
        Matcher mulMatcher = MUL_REGEX.matcher(memInstructions);
        Matcher doMatcher = DO_REGEX.matcher(memInstructions);
        Matcher dontMatcher = DONT_REGEX.matcher(memInstructions);

        boolean mulEnabled = true;
        long sum = 0;
        int lastIndex = 0;

        while (mulMatcher.find()) {
            int mulStart = mulMatcher.start();

            // Check for do() or don't() instructions before the current mul instruction
            while (doMatcher.find(lastIndex) && doMatcher.start() < mulStart) {
                mulEnabled = true;
                lastIndex = doMatcher.end();
            }
            while (dontMatcher.find(lastIndex) && dontMatcher.start() < mulStart) {
                mulEnabled = false;
                lastIndex = dontMatcher.end();
            }

            if (mulEnabled) {
                String[] mul = mulMatcher.group().substring(4, mulMatcher.group().length() - 1).split(",");
                long a = Long.parseLong(mul[0]);
                long b = Long.parseLong(mul[1]);
                sum += a * b;
            }
        }

        return sum;
    }
}
