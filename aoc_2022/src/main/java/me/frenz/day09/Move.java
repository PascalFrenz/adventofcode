package me.frenz.day09;

record Move(int x, int y, int steps) {
    static Move parse(final String instruction) {
        if (instruction.isBlank()) {
            throw new IllegalArgumentException("Instruction must not be empty");
        }

        final String[] parts = instruction.split(" ");
        final String direction = parts[0];
        final int steps = Integer.parseInt(parts[1]);
        return switch (direction) {
            case "L" -> new Move(-1, 0, steps);
            case "R" -> new Move(1, 0, steps);
            case "U" -> new Move(0, 1, steps);
            case "D" -> new Move(0, -1, steps);
            default -> throw new IllegalStateException("Unexpected value: " + direction);
        };
    }
}
