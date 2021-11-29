import java.util.Arrays;
import java.util.Optional;
import java.util.stream.Stream;

public class Day2 {

    private static class PasswordRule {

        public final int min;
        public final int max;
        public final char character;

        public PasswordRule(int min, int max, char character) {
            this.min = min;
            this.max = max;
            this.character = character;
        }

        public boolean validate_sledRentalPlace(final String password) {
            int numOfChars = password.replaceAll("[^" + character + "]", "").length();
            return numOfChars >= min && numOfChars <= max;
        }

        public boolean validate_TobogganCorporate(final String password) {
            return password.charAt(min - 1) == character ^ password.charAt(max - 1) == character;
        }

        public static PasswordRule from(final String string) {
            String sanitized = string.replaceAll("[ \\n\\t\\-:]+", ";");
            String[] splitted = Arrays.stream(sanitized.split(";")).filter(s -> !s.isEmpty()).toArray(String[]::new);
            int min = Integer.parseInt(splitted[0]);
            int max = Integer.parseInt(splitted[1]);
            return new PasswordRule(min, max, splitted[2].charAt(0));
        }

        @Override
        public String toString() {
            return String.format("%d-%d %s", min, max, character);
        }
    }

    public static void main(String[] args) {
        final Optional<Stream<String>> inputA = Util.readFile(Day2.class, "input2.txt");

        inputA.ifPresent(stringStream -> {
            long validPasswordCount = stringStream
                    .map(s -> Util.Pair.of(s.split(":")[1].trim(), PasswordRule.from(s)))
                    .filter(pair -> pair.getRight().validate_sledRentalPlace(pair.getLeft()))
                    .count();

            System.out.printf("Es wurden %d valide Passwörter gefunden (Sled Rental Place)\n", validPasswordCount);
        });

        final Optional<Stream<String>> inputB = Util.readFile(Day2.class, "input4.txt");

        inputB.ifPresent(stringStream -> {
            long validPasswordCount = stringStream
                    .map(s -> Util.Pair.of(s.split(":")[1].trim(), PasswordRule.from(s)))
                    .filter(pair -> pair.getRight().validate_TobogganCorporate(pair.getLeft()))
                    .count();

            System.out.printf("Es wurden %d valide Passwörter gefunden (Toboggan Corporate)\n", validPasswordCount);
        });
    }
}
