package aoc2020;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Day4 {

    public static final String BYR = "byr";
    public static final String IYR = "iyr";
    public static final String EYR = "eyr";
    public static final String HGT = "hgt";
    public static final String HCL = "hcl";
    public static final String ECL = "ecl";
    public static final String PID = "pid";

    public static void main(String[] args) {
        final Optional<Stream<String>> input = Util.readFile(Day4.class, "input4.txt");

        final List<String> lines = input.orElse(Stream.empty()).collect(Collectors.toList());

        final List<String> passports = getPassports(lines);

        System.out.printf("There are %d complete passports.\n", countCompletePassports(passports));
        System.out.printf("There are %d valid passports.\n", countValidPassports(passports));
    }

    private static class Passport {
        private final int birthDate;
        private final int issueYear;
        private final int expirationYear;
        private final String height;
        private final String hairColor;
        private final String eyeColor;
        private final String pid;

        public Passport(final String passport) {
            final Map<String, String> values = new HashMap<>();
            for (String keyValuePair : passport.split(" ")) {
                String[] splitted = keyValuePair.split(":");
                values.put(splitted[0], splitted[1]);
            }

            this.birthDate = Integer.parseInt(values.get(BYR));
            this.issueYear = Integer.parseInt(values.get(IYR));
            this.expirationYear = Integer.parseInt(values.get(EYR));

            this.height = values.get(HGT);
            this.hairColor = values.get(HCL);
            this.eyeColor = values.get(ECL);
            this.pid = values.get(PID);
        }

        public boolean isValid() {
            return isValidBirthDate()
                    && isValidIssueYear()
                    && isValidExpirationYear()
                    && isValidHeight()
                    && isValidHairColor()
                    && isValidEyeColor()
                    && isValidPid();
        }

        private boolean isValidHeight() {
            if (height.contains("cm")) {
                final int cm = Integer.parseInt(height.replaceAll("cm", ""));
                return cm >= 150 && cm <= 193;
            } else if (height.contains("in")) {
                final int in = Integer.parseInt(height.replaceAll("in", ""));
                return in >= 59 && in <= 76;
            } else {
                return false;
            }
        }

        private boolean isValidHairColor() {
            return hairColor.matches("#[0-9a-f]{6}");
        }

        private boolean isValidEyeColor() {
            return Set.of("amb", "blu", "brn", "gry", "grn", "hzl", "oth").contains(eyeColor);
        }

        private boolean isValidPid() {
            return pid.matches("[0-9]{9}");
        }

        private boolean isValidBirthDate() {
            return birthDate >= 1920 && birthDate <= 2002;
        }

        private boolean isValidIssueYear() {
            return issueYear >= 2010 && issueYear <= 2020;
        }

        private boolean isValidExpirationYear() {
            return expirationYear >= 2020 && expirationYear <= 2030;
        }
    }

    public static long countCompletePassports(final List<String> passports) {
        return filterIncompletePassports(passports).count();
    }

    public static long countValidPassports(final List<String> passports) {
        return filterIncompletePassports(passports)
                .map(Passport::new)
                .filter(Passport::isValid)
                .count();
    }

    private static Stream<String> filterIncompletePassports(List<String> passports) {
        return passports.stream()
                .filter(passport -> passport.contains(BYR))
                .filter(passport -> passport.contains(IYR))
                .filter(passport -> passport.contains(EYR))
                .filter(passport -> passport.contains(HGT))
                .filter(passport -> passport.contains(HCL))
                .filter(passport -> passport.contains(ECL))
                .filter(passport -> passport.contains(PID));
    }

    public static List<String> getPassports(final List<String> lines) {
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
