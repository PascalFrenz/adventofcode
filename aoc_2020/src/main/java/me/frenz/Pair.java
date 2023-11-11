package me.frenz;

public record Pair<T>(T left, T right) {

    public String toString() {
        return String.format("(%s, %s)", left, right);
    }

    public Pair<T> copy() {
        return new Pair<>(this.left, this.right);
    }
}
