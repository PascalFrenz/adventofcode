package me.frenz.day13

import me.frenz.Pair
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNotNull
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

internal class Day13Test {
    private var dut: Day13? = null

    @BeforeEach
    fun setUp() {
        val exampleInput = """
                [1,1,3,1,1]
                [1,1,5,1,1]
                                
                [[1],[2,3,4]]
                [[1],4]
                                
                [9]
                [[8,7,6]]
                                
                [[4,4],4,4]
                [[4,4],4,4,4]
                                
                [7,7,7,7]
                [7,7,7]
                                
                []
                [3]
                                
                [[[]]]
                [[]]
                                
                [1,[2,[3,[4,[5,6,7]]]],8,9]
                [1,[2,[3,[4,[5,6,0]]]],8,9]
                """.trimIndent()
        dut = Day13(exampleInput.lines())
    }

    @Test
    fun testParsePacket_singleList() {
        val parser = PacketParser()
        val result = parser.parsePacket("[1,1,3,1,1]")
        assertNotNull(result)
        assertEquals(
            containerPacket(*intPackets(1, 1, 3, 1, 1)),
            result
        )
    }

    @Test
    fun testParsePacket_innerLists() {
        val parser = PacketParser()
        val result = parser.parsePacket("[[1],[2,3,4]]")
        assertNotNull(result)
        assertEquals(
            containerPacket(containerPacket(intPacket(1)), containerPacket(*intPackets(2, 3, 4))),
            result
        )
    }

    @Test
    fun testParsePacket_doubleList() {
        val parser = PacketParser()
        val result = parser.parsePacket("[[8,7,6]]")
        assertNotNull(result)
        assertEquals(
            containerPacket(containerPacket(*intPackets(8, 7, 6))),
            result
        )
    }

    @Test
    fun testParsePacket_multiplePairs() {
        val parsedInput = dut!!.parsedInput
        assertEquals(
            Pair(
                containerPacket(*intPackets(1, 1, 3, 1, 1)),
                containerPacket(*intPackets(1, 1, 5, 1, 1))
            ),
            parsedInput[0]
        )
        assertEquals(
            Pair(
                containerPacket(containerPacket(intPacket(1)), containerPacket(*intPackets(2, 3, 4))),
                containerPacket(containerPacket(intPacket(1)), intPacket(4))
            ),
            parsedInput[1]
        )
        assertEquals(
            Pair(
                containerPacket(intPacket(9)),
                containerPacket(containerPacket(*intPackets(8, 7, 6)))
            ),
            parsedInput[2]
        )
    }

    private fun intPackets(vararg vals: Int): Array<IntPacket> {
        return vals.map(::IntPacket).toTypedArray()
    }

    @Test
    fun givenTwoInts_ifLeftIsSmaller_shouldReturnOne() {
        val result = dut!!.compare(intPacket(0), intPacket(1))
        assertEquals(1, result)
    }

    @Test
    fun givenTwoInts_ifRightIsSmaller_shouldReturnMinusOne() {
        val result = dut!!.compare(intPacket(1), intPacket(0))
        assertEquals(-1, result)
    }

    @Test
    fun givenOneIntAndOneList_whenComparing_shouldConvertToListAndCompareInts_RightOrder() {
        val result = dut!!.compare(intPacket(0), containerPacket(intPacket(1)))
        assertEquals(1, result)
    }

    @Test
    fun givenOneIntAndOneList_whenComparing_shouldConvertToListAndCompareInts_WrongOrder() {
        val result = dut!!.compare(containerPacket(intPacket(1)), intPacket(0))
        assertEquals(-1, result)
    }

    @Test
    fun givenTwoSameIntPackets_whenComparing_shouldReturnZero() {
        val result = dut!!.compare(intPacket(1), intPacket(1))
        assertEquals(0, result)
    }

    @Test
    fun givenTwoDifferentPacketTypesWithSameValue_whenComparing_shouldReturnZero() {
        val result = dut!!.compare(containerPacket(intPacket(1)), intPacket(1))
        assertEquals(0, result)
    }

    @Test
    fun givenTwoContainers_whenSameItemValuesAndAmount_shouldContinueChecking() {
        val result = dut!!.compare(containerPacket(*intPackets(1, 2)), containerPacket(*intPackets(1, 2)))
        assertEquals(0, result)
    }

    @Test
    fun givenTwoContainers_whenLeftHasLessItems_shouldBeInRightOrder() {
        assertEquals(1, dut!!.compare(containerPacket(*intPackets(1)), containerPacket(*intPackets(1, 2))))
        assertEquals(
            1,
            dut!!.compare(containerPacket(*intPackets(1)), containerPacket(containerPacket(*intPackets(1, 2))))
        )
        assertEquals(
            1,
            dut!!.compare(
                containerPacket(*intPackets(1), containerPacket(intPacket(1))),
                containerPacket(intPacket(1), containerPacket(*intPackets(1, 2)))
            )
        )
    }

    @Test
    fun givenTwoContainers_whenRightHasLessItems_shouldNotBeInRightOrder() {
        assertEquals(-1, dut!!.compare(containerPacket(*intPackets(1, 2)), containerPacket(*intPackets(1))))
        assertEquals(
            -1,
            dut!!.compare(
                containerPacket(*intPackets(1), containerPacket(*intPackets(2, 2))),
                containerPacket(intPacket(1), containerPacket(*intPackets(2)))
            )
        )
    }

    @Test
    fun someOtherTestCases() {
        assertEquals(
            -1, dut!!.compare(
                containerPacket(*intPackets(1, 2), containerPacket(*intPackets(1, 1, 1, 3))),
                containerPacket(*intPackets(1, 2), containerPacket(*intPackets(1, 1, 1, 2)))
            )
        )

        val otherDut = Day13(listOf("[1,[2,[3,[4,[5,6,7]]]],8,9]", "[1,[2,[3,[4,[5,6,7]]]],7,9]"))
        assertEquals(0, otherDut.part1())
    }

    @Test
    fun part1() {
        assertEquals(13, dut!!.part1())
    }

    @Test
    fun part2() {
        assertEquals(140, dut!!.part2())
    }

    companion object {
        private fun intPacket(value: Int): IntPacket {
            return IntPacket(value)
        }

        private fun containerPacket(vararg packets: PacketValue): ContainerPacket {
            return ContainerPacket(listOf(*packets))
        }
    }
}
