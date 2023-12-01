package me.frenz;

public record Triple<L, C, R>(L left, C center, R right) {

    public static <L, C, R> Triple<L, C, R> of(L left, C center, R right) {
        return new Triple<>(left, center, right);
    }

    @Override
    public String toString() {
        return String.format("(%s, %s, %s)", left.toString(), center != null ? center.toString() : "", right.toString());
    }
}
