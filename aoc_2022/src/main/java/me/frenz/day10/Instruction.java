package me.frenz.day10;

record Instruction(Operation op, Register r, int value, int cycleCount) {
    static Instruction parse(String input) {
        if (input.isBlank()) {
            throw new IllegalArgumentException("input cannot be empty or blank!");
        }

        if (input.toLowerCase().startsWith("noop")) {
            return new Instruction(Operation.NOOP, null, 0, 1);
        } else if (input.toLowerCase().startsWith("add")) {
            Register r = switch (input.charAt(3)) {
                case 'x' -> Register.X;
                default -> throw new IllegalStateException("Unexpected value: " + input.charAt(3));
            };
            return new Instruction(Operation.ADD, r, Integer.parseInt(input.split(" ")[1]), 2);
        } else {
            throw new UnsupportedOperationException("Cannot parse line: " + input);
        }
    }
}
