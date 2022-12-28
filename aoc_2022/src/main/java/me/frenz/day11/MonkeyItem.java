package me.frenz.day11;

import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.math.BigInteger;

@EqualsAndHashCode
@ToString
class MonkeyItem {

    private final int toMonkey;

    private final BigInteger item;

    MonkeyItem(int toMonkey, BigInteger itemWorryLevel) {
        this.toMonkey = toMonkey;
        this.item = itemWorryLevel;
    }

    int toMonkey() {
        return toMonkey;
    }

    BigInteger itemWorryLevel() {
        return item;
    }
}
