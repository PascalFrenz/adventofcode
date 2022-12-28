package me.frenz.day11;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

import java.math.BigInteger;

import static lombok.AccessLevel.PACKAGE;


@EqualsAndHashCode
@ToString
final class MonkeyTest {

    private final String operation;
    @Getter(PACKAGE) private final BigInteger operand;

    MonkeyTest(String operation, int operand) {
        this.operation = operation;
        this.operand = new BigInteger(String.valueOf(operand));
    }

    boolean test(BigInteger toTest) {
        if ("divisible".equals(operation)) {
            return toTest.mod(operand).equals(BigInteger.ZERO);
        } else {
            throw new IllegalArgumentException("Unknown operation: " + operation);
        }
    }

}
