package me.frenz;

public record Pair<T, R>(T left, R right) {

    public String toString() {
        return String.format("(%s, %s)", left, right);
    }

    public Pair<T, R> copy() {
        return new Pair<>(this.left, this.right);
    }

    public static <T, R> Pair<T, R> of(T left, R right) {
        return new Pair<>(left, right);
    }
}
