package me.frenz.day05;

import lombok.Value;

@Value
public class BoardingPass {
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
