package me.frenz.day07;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;


final class AocDirectory extends AbstractAocFile {

    static final AocDirectory ROOT = new AocDirectory("/", null, new HashSet<>());

    private final String name;

    private final AocDirectory parent;

    private final Map<String, AbstractAocFile> files;

    AocDirectory(String name, AocDirectory parent, Set<AbstractAocFile> files) {
        this.name = name;
        this.parent = parent;
        this.files = files.stream().collect(Collectors.toMap(AbstractAocFile::name, Function.identity()));
    }

    public String name() {
        return name;
    }

    @Override
    AocDirectory directory() {
        return this;
    }

    public AocDirectory parent() {
        return parent;
    }

    public Set<AbstractAocFile> files() {
        return new HashSet<>(files.values());
    }

    public void appendFiles(final Collection<AbstractAocFile> files) {
        for (final AbstractAocFile file : files) {
            this.files.put(file.name(), file);
        }
    }

    @Override
    long size() {
        return files.values().stream().mapToLong(AbstractAocFile::size).sum();
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) return true;
        if (obj == null || obj.getClass() != this.getClass()) return false;
        var that = (AocDirectory) obj;
        return Objects.equals(this.name, that.name) &&
               Objects.equals(this.parent, that.parent) &&
               Objects.equals(this.files, that.files);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, parent);
    }

    @Override
    public String toString() {
        return "AocDirectory[" +
               "name=" + name + ", " +
               "parent=" + parent + ']';
    }

    public boolean contains(String key) {
        return files.containsKey(key);
    }

    public AbstractAocFile get(final String filename) {
        return files.get(filename);
    }

    public AocDirectory update(final String name, final AbstractAocFile entry) {
        files.put(name, entry);
        return entry.directory();
    }

    public List<AocDirectory> getDirsMatching(final Predicate<AocDirectory> predicate) {
        final ArrayList<AocDirectory> matchingDirs = new ArrayList<>();
        if (predicate.test(this)) {
            matchingDirs.add(this);
        }
        final List<AocDirectory> childs = files.values()
                .stream()
                .filter(AocDirectory.class::isInstance)
                .map(AocDirectory.class::cast)
                .flatMap(dir -> dir.getDirsMatching(predicate).stream())
                .toList();

        matchingDirs.addAll(childs);

        return matchingDirs;
    }
}
