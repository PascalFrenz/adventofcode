package me.frenz.day13;

import java.util.List;

class IntPacket extends PacketValue implements Comparable<PacketValue> {
    private final int value;

    IntPacket(int value) {
        this.value = value;
    }

    int get() {
        return value;
    }

    @Override
    void add(PacketValue child) {
        throw new UnsupportedOperationException("Cannot add children to IntPacket");
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof IntPacket intPacket)) return false;

        return value == intPacket.value;
    }

    @Override
    public int hashCode() {
        return value;
    }

    @Override
    public String toString() {
        return String.valueOf(value);
    }

    @Override
    public int compareTo(PacketValue o) {
        if (o instanceof IntPacket other) {
            return Integer.compare(other.value, value);
        } else if (o instanceof ContainerPacket c) {
            return new ContainerPacket(List.of(this)).compareTo(c);
        } else {
            throw new IllegalArgumentException("Unknown type to compare against! " + o.getClass().getName());
        }
    }
}
