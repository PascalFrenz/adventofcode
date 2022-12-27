package me.frenz.day10;

import java.util.Collections;
import java.util.List;
import java.util.Stack;
import java.util.function.BiConsumer;
import java.util.stream.Collectors;

final class CPU {
    private int cycle = 0;
    private int register = 1;

    private final Stack<Instruction> instructions;

    CPU(List<String> input) {
        this.instructions = input.stream().map(Instruction::parse).collect(Collectors.toCollection(Stack::new));
        Collections.reverse(instructions);
    }

    public void execute(final BiConsumer<Integer, Integer> operation) {
        while (!instructions.isEmpty()) {
            this.cycle(operation);
        }
    }

    private void cycle(final BiConsumer<Integer, Integer> execute) {
        final Instruction i = instructions.pop();
        boolean doAdd = false;
        if (Operation.ADD.equals(i.op())) {
            if (i.cycleCount() > 1) {
                instructions.push(new Instruction(i.op(), i.r(), i.value(), i.cycleCount() - 1));
            } else {
                doAdd = true;
            }
        }
        cycle++;
        execute.accept(cycle, register);
        if (doAdd) {
            register += i.value();
        }
    }
}
