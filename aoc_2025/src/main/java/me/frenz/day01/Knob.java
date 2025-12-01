package me.frenz.day01;

import static me.frenz.day01.KnobDirection.*;

public class Knob {

    private int position;
    private int crossedZero = 0;
    private int exactlyAtZero = 0;

    public Knob(int start) {
        this.position = start;
    }

    /**
     * Solves this basically by "brute-force", because we calculate every single step.
     * Still fast enough, so I don't really care.
     * @param direction Direction in which the knob will be turned
     * @param amount The number of "clicks" the knob should turn
     */
    void turn(KnobDirection direction, int amount) {
        int remainingSteps = amount;
        while (remainingSteps > 0) {
            if (direction == RIGHT && position == 99) position = -1; // we add one this cycle and want to be at 0
            if (direction == LEFT && position == 0) position = 100; // we remove one this cycle and want to be at 99

            position += direction.getValue();
            remainingSteps--;

            if (position == 0 && remainingSteps > 0) crossedZero++;
        }

        if (position == 0) exactlyAtZero++;
    }

    int timesZeroWasCrossed() {
        return crossedZero;
    }

    int timesExactlyAtZero() {
        return exactlyAtZero;
    }

}
