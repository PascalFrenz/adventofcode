package aoc2020.day5;

public class BoardingPass {
    private final int row;
    private final int column;
    private final int seatId;

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

    public int getRow() {
        return row;
    }

    public int getColumn() {
        return column;
    }

    public int getSeatId() {
        return seatId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        BoardingPass that = (BoardingPass) o;

        return getSeatId() == that.getSeatId();
    }

    @Override
    public int hashCode() {
        int result = getRow();
        result = 31 * result + getColumn();
        return result;
    }

    @Override
    public String toString() {
        return String.format("Seat [%d, %d]", row, column);
    }
}
