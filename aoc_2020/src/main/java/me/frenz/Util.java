package me.frenz;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Optional;
import java.util.stream.Stream;

public class Util {

    public static <T> Optional<Stream<String>> readFile(Class<T> tClass, final String name) {
        return Optional.ofNullable(tClass.getResource("/" + name))
                .map(url -> {
                    try {
                        return url.toURI();
                    } catch (URISyntaxException e) {
                        return null;
                    }
                })
                .map(Path::of)
                .map(path -> {
                    try {
                        return Files.lines(path);
                    } catch (IOException e) {
                        System.err.printf("Die angegebene Datei (%s) konnte nicht gefunden werden!\n", name);
                        e.printStackTrace();
                        return null;
                    }
                });
    }

    public static class Pair<L, R> {
        private final L left;
        private final R right;

        private Pair(L left, R right) {
            this.left = left;
            this.right = right;
        }

        public L getLeft() {
            return left;
        }

        public R getRight() {
            return right;
        }

        public static <L, R> Pair<L, R> of(L left, R right) {
            return new Pair<>(left, right);
        }

        @Override
        public String toString() {
            return String.format("(%s, %s)", left.toString(), right.toString());
        }
    }


    public static class Triple<L, C, R> {
        private final L left;
        private final C center;
        private final R right;

        private Triple(L left, C center, R right) {
            this.left = left;
            this.right = right;
            this.center = center;
        }

        public static <L, C, R> Triple<L, C, R> of(L left, C center, R right) {
            return new Triple<>(left, center, right);
        }

        public L getLeft() {
            return left;
        }

        public R getRight() {
            return right;
        }

        public C getCenter() {
            return center;
        }

        @Override
        public String toString() {
            return String.format("(%s, %s, %s)", left.toString(), center != null ? center.toString() : "", right.toString());
        }
    }
}
