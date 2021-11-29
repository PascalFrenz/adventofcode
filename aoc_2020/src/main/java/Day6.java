import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.function.BiConsumer;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class Day6 {

    public static final String ABC = "abcdefghijklmnopqrstuvwxyz";

    public static void main(String[] args) {
        final List<String> inputA = Util.readFile(Day5.class, "input6.txt")
                .orElse(Stream.empty())
                .collect(Collectors.toList());

        int sumAll = calculateSum(
                inputA,
                HashSet::new,
                (groupAnswers, input) -> groupAnswers.addAll(input.transform(Day6::toCharacterList))
        );

        System.out.println(sumAll);

        final List<String> inputB = Util.readFile(Day5.class, "input6.txt")
                .orElse(Stream.empty())
                .collect(Collectors.toList());

        final int sumIntersection = calculateSum(
                inputB,
                () -> new HashSet<>(ABC.transform(Day6::toCharacterList)),
                (groupAnswers, input) -> groupAnswers.retainAll(input.transform(Day6::toCharacterList))
        );

        System.out.println(sumIntersection);
    }

    private static int calculateSum(
            final List<String> input,
            final Supplier<Set<Character>> initValue,
            final BiConsumer<Set<Character>, String> compute
    ) {
        final Stream.Builder<Set<Character>> builder = Stream.builder();

        Set<Character> groupInput = initValue.get();
        for (String answer : input) {
            if (answer.isEmpty()) {
                builder.accept(new HashSet<>(groupInput));
                groupInput = initValue.get();
            } else {
                compute.accept(groupInput, answer);
            }
        }

        if (!groupInput.isEmpty())
            builder.accept(new HashSet<>(groupInput));

        return builder.build().mapToInt(Set::size).sum();
    }

    private static List<Character> toCharacterList(final String s) {
        return IntStream.range(0, s.length())
                .mapToObj(s::charAt)
                .collect(Collectors.toList());
    }
}
