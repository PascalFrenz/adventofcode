package me.frenz.day14

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

internal class Day14Test {
    private var dut: Day14? = null

    @BeforeEach
    fun setUp() {
        val exampleInput = """
               498,4 -> 498,6 -> 496,6
               503,4 -> 502,4 -> 502,9 -> 494,9
                """.trimIndent()
        dut = Day14(exampleInput.lines())
    }
    @Test
    fun part1() {
        assertEquals(-1, dut!!.part1())
    }

    @Test
    fun part2() {
        assertEquals(-1, dut!!.part2())
    }
}
