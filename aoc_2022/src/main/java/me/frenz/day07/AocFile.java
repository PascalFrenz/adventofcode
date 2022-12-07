package me.frenz.day07;

class AocFile extends AbstractAocFile {

    private final long size;
    private final String name;
    private final AocDirectory parent;

    AocFile(final long size, final String name, final AocDirectory parent) {
        this.size = size;
        this.name = name;
        this.parent = parent;
    }

    @Override
    long size() {
        return size;
    }

    @Override
    String name() {
        return name;
    }

    @Override
    AocDirectory directory() {
        return parent;
    }
}
