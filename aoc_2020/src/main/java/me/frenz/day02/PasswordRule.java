package me.frenz.day02;

import java.util.Arrays;

record PasswordRule(int min, int max, char character) {

    public boolean validateSledRentalPlace(final String password) {
        int numOfChars = password.replaceAll("[^" + character + "]", "").length();
        return numOfChars >= min && numOfChars <= max;
    }

    public boolean validateTobogganCorporate(final String password) {
        return password.charAt(min - 1) == character ^ password.charAt(max - 1) == character;
    }

    public static me.frenz.day02.PasswordRule from(final String string) {
        String sanitized = string.replaceAll("[ \\n\\t\\-:]+", ";");
        String[] splitted = Arrays.stream(sanitized.split(";")).filter(s -> !s.isEmpty()).toArray(String[]::new);
        int min = Integer.parseInt(splitted[0]);
        int max = Integer.parseInt(splitted[1]);
        return new me.frenz.day02.PasswordRule(min, max, splitted[2].charAt(0));
    }

    @Override
    public String toString() {
        return String.format("%d-%d %s", min, max, character);
    }
}
