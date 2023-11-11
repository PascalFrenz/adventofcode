package me.frenz.day04;

import lombok.Getter;
import me.frenz.Day;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

@Getter
public class Day04 extends Day<Long, Long> {

    public static final String BYR = "byr";
    public static final String IYR = "iyr";
    public static final String EYR = "eyr";
    public static final String HGT = "hgt";
    public static final String HCL = "hcl";
    public static final String ECL = "ecl";
    public static final String PID = "pid";

    private final List<String> passports;

    public Day04(List<String> input) {
        super(input);
        passports = parsePassports(input);
    }

    @Override
    protected Long part1() {
        return countCompletePassports();
    }

    @Override
    protected Long part2() {
        return countValidPassports();
    }

    long countCompletePassports() {
        return filterIncompletePassports(passports).count();
    }

    long countValidPassports() {
        return filterIncompletePassports(passports)
                .map(Passport::new)
                .filter(Passport::isValid)
                .count();
    }

    private Stream<String> filterIncompletePassports(List<String> passports) {
        return passports.stream()
                .filter(passport -> passport.contains(BYR))
                .filter(passport -> passport.contains(IYR))
                .filter(passport -> passport.contains(EYR))
                .filter(passport -> passport.contains(HGT))
                .filter(passport -> passport.contains(HCL))
                .filter(passport -> passport.contains(ECL))
                .filter(passport -> passport.contains(PID));
    }

    private List<String> parsePassports(final List<String> lines) {
        final List<String> passports = new ArrayList<>();
        StringBuilder passport = new StringBuilder();
        for (String line : lines) {
            if (line.isEmpty()) {
                passports.add(passport.toString().trim());
                passport = new StringBuilder();
            } else {
                passport.append(line).append(" ");
            }
        }

        if (!passport.isEmpty())
            passports.add(passport.toString().trim());

        return passports;
    }
}
