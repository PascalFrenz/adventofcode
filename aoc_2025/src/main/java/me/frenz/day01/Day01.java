
package me.frenz.day01;

import me.frenz.Day;

import java.util.List;

public class Day01 extends Day<Integer, Integer> {

    public Day01(List<String> input) {
        super(input);
    }

    @Override
    protected Integer part1() {
        var knob = new Knob(50);
        processInput(knob);
        return knob.timesExactlyAtZero();
    }

    @Override
    protected Integer part2() {
        var knob = new Knob(50);
        processInput(knob);
        return knob.timesExactlyAtZero() + knob.timesZeroWasCrossed();
    }

    private void processInput(Knob knob) {
        for (String line : input) {
            var direction = line.charAt(0);
            var amount = Integer.parseInt(line.substring(1));
            knob.turn(KnobDirection.valueOf(direction), amount);
        }
    }

}
