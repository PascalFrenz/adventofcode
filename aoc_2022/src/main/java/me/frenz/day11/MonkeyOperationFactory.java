package me.frenz.day11;

import lombok.EqualsAndHashCode;

import java.math.BigInteger;

@EqualsAndHashCode
final class MonkeyOperationFactory {
    private final String operator;
    private final String left;
    private final String right;

    MonkeyOperationFactory(String operator, String left, String right) {
        this.operator = operator;
        this.left = left;
        this.right = right;
    }

    MonkeyOperation create(BigInteger old) {
        BigInteger left = "old".equals(this.left) ? old : new BigInteger(this.left);
        BigInteger right = "old".equals(this.right) ? old : new BigInteger(this.right);
        return switch (this.operator) {
            case "+" -> new AddMonkeyOperation(left, right);
            case "*" -> new MultMonkeyOperation(left, right);
                default -> throw new IllegalArgumentException("Unknown op: " + this.operator);
        };
    }
}
