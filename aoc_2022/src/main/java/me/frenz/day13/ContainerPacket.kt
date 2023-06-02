package me.frenz.day13

internal class ContainerPacket : PacketValue {
    private val packets: MutableList<PacketValue?>

    fun get(): List<PacketValue?> = packets

    constructor() : this(ArrayList())

    constructor(packets: List<PacketValue?>) : super() {
        this.packets = ArrayList(packets)
    }

    fun unwrap(): ContainerPacket {
        assert(packets.size == 1)
        with(packets[0]) {
            when (this) {
                is ContainerPacket -> return this
                else -> throw IllegalStateException("Did not find inner container packet to unwrap")
            }
        }
    }

    override fun add(child: PacketValue?) {
        packets.add(child)
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        return if (other !is ContainerPacket) false else packets == other.packets
    }

    override fun hashCode(): Int = packets.hashCode()

    override fun toString(): String = packets.toString()
}
