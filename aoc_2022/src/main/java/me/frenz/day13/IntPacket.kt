package me.frenz.day13;

class IntPacket extends PacketValue {
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
}
