package me.frenz.day04;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

class Passport {
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

        this.birthDate = Integer.parseInt(values.get(Day04.BYR));
        this.issueYear = Integer.parseInt(values.get(Day04.IYR));
        this.expirationYear = Integer.parseInt(values.get(Day04.EYR));

        this.height = values.get(Day04.HGT);
        this.hairColor = values.get(Day04.HCL);
        this.eyeColor = values.get(Day04.ECL);
        this.pid = values.get(Day04.PID);
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
