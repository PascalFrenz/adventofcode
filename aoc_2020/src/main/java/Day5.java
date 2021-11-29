import lombok.Value;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Day5 {

    public static void main(String[] args) {
        solveA();
        solveB();
    }

    private static void solveA() {
        final Optional<Stream<String>> inputA = Util.readFile(Day5.class, "input5.txt");

        inputA.orElse(Stream.empty())
                .map(BoardingPass::from)
                .map(BoardingPass::getSeatId)
                .max(Integer::compareTo)
                .ifPresent(System.out::println);
    }

    private static void solveB() {
        final Optional<Stream<String>> inputB = Util.readFile(Day5.class, "input5.txt");

        final List<Integer> seats = inputB.orElse(Stream.empty())
                .map(BoardingPass::from)
                .filter(boardingPass -> boardingPass.getRow() != 0)
                .filter(boardingPass -> boardingPass.getRow() != 127)
                .map(BoardingPass::getSeatId)
                .sorted()
                .collect(Collectors.toList());

        // As the adjacent seats are guaranteed to be in the list we can safely
        // assume that the missing seat is not the first one
        int missingSeat = seats.get(0);
        for (Integer seat : seats) {
            if (seat != missingSeat)
                break;
            missingSeat++;
        }

        System.out.println(missingSeat);
    }

    @Value
    public static class BoardingPass {
        int row;
        int column;
        int seatId;

        public BoardingPass(int row, int column) {
            this.row = row;
            this.column = column;
            this.seatId = row * 8 + column;
        }

        public static BoardingPass from(final String string) {
            if (string == null)
                throw new IllegalArgumentException("Input was null!");
            if (!string.matches("[FB]{7}[LR]{3}"))
                throw new IllegalArgumentException("Input does not represent a valid boarding pass! Valid: [FB]{7}[LR]{3}");

            final String rowDefinition = string.substring(0, 7);
            final String columnDefinition = string.substring(7);

            return new BoardingPass(findRow(rowDefinition, 0, 127), findColumn(columnDefinition, 0, 7));
        }

        private static int findRow(final String definition, final int min, final int max) {
            if (definition.isEmpty())
                return min;
            else if (definition.charAt(0) == 'F')
                return findRow(definition.substring(1), min, max - ((max - min) / 2 + 1));
            else
                return findRow(definition.substring(1), min + ((max - min) / 2 + 1), max);
        }

        private static int findColumn(final String definition, final int min, final int max) {
            if (definition.isEmpty())
                return min;
            else if (definition.charAt(0) == 'L')
                return findColumn(definition.substring(1), min, max - ((max - min) / 2 + 1));
            else
                return findColumn(definition.substring(1), min + ((max - min) / 2 + 1), max);
        }
    }
}
