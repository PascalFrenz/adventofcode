package me.frenz.day13

internal class IntPacket(private val value: Int) : PacketValue() {
    fun get(): Int {
        return value
    }

    override fun add(child: PacketValue?) {
        throw UnsupportedOperationException("Cannot add children to IntPacket")
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        return if (other !is IntPacket) false else value == other.value
    }

    override fun hashCode(): Int {
        return value
    }

    override fun toString(): String {
        return value.toString()
    }
}
