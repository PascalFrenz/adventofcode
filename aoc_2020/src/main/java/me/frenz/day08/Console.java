package me.frenz.day08;

import lombok.Getter;

import java.util.Arrays;
import java.util.Optional;

class Console {
    private long accu;
    private final String[] instructions;
    private final int[] executionCounts;

    public Console(final String[] instructions) {
        this.instructions = instructions;
        this.accu = 0;
        this.executionCounts = new int[instructions.length];
        Arrays.fill(executionCounts, 0);
    }

    public long executeNormally() {
        int instIdx = 0;
        boolean instructionAlreadyExecuted = false;

        while (!instructionAlreadyExecuted) {
            executionCounts[instIdx] += 1;
            instIdx += (int) Instruction.from(instructions[instIdx]).execute(this);
            instructionAlreadyExecuted = executionCounts[instIdx] > 0;
        }

        return accu;
    }

    public long executeMutably() {
        Optional<Long> result = Optional.empty();

        int instrIdx = 0;

        while (result.isEmpty()) {
            final int idx = instrIdx;

            executionCounts[instrIdx] += 1;
            instrIdx += (int) Instruction.from(instructions[instrIdx]).execute(this);

            if (executionCounts[instrIdx] > 0) {
                final String[] instrCopy = new String[instructions.length];
                System.arraycopy(instructions, 0, instrCopy, 0, instructions.length);

                if (instructions[idx].contains(Instruction.NOP)) {
                    instrCopy[idx] = instructions[idx].replace(Instruction.NOP, Instruction.JMP);
                    result = new Console(instrCopy).tryExecute();
                } else if (instructions[idx].contains(Instruction.JMP)) {
                    instrCopy[idx] = instructions[idx].replace(Instruction.JMP, Instruction.NOP);
                    result = new Console(instrCopy).tryExecute();
                }
            }
        }

        return result.get();
    }

    private Optional<Long> tryExecute() {
        int instIdx = 0;
        boolean instructionAlreadyExecuted = false;
        boolean done = false;

        while (!done && !instructionAlreadyExecuted && instIdx < instructions.length) {
            executionCounts[instIdx] += 1;
            instIdx += (int) Instruction.from(instructions[instIdx]).execute(this);
            done = instIdx == instructions.length;
            instructionAlreadyExecuted = instIdx < instructions.length && executionCounts[instIdx] > 0;
        }

        return done ? Optional.of(accu) : Optional.empty();
    }


    @Getter
    private static abstract class Instruction {

        private static final String NOP = "nop";
        private static final String ACC = "acc";
        private static final String JMP = "jmp";

        private final String argument;

        protected Instruction(String argument) {
            this.argument = argument;
        }

        public static Instruction from(String str) {
            final String instruction = str.split(" ")[0];
            final String argument = str.split(" ")[1];

            return switch (instruction) {
                case NOP -> new Instruction.NoOperation(argument);
                case ACC -> new Instruction.Accumulate(argument);
                case JMP -> new Instruction.Jump(argument);
                default -> throw new IllegalArgumentException();
            };
        }

        public abstract long execute(final Console console);

        private static class NoOperation extends Instruction {
            private NoOperation(final String argument) {
                super(argument);
            }

            @Override
            public long execute(final Console console) {
                return 1;
            }
        }

        private static class Accumulate extends Instruction {
            private Accumulate(final String argument) {
                super(argument);
            }

            @Override
            public long execute(final Console console) {
                console.accu += Long.parseLong(super.argument);
                return 1;
            }
        }

        private static class Jump extends Instruction {
            private Jump(final String argument) {
                super(argument);
            }

            @Override
            public long execute(final Console console) {
                return Long.parseLong(super.argument);
            }
        }
    }
}
