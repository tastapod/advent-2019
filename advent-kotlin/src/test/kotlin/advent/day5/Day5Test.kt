package advent.day5

import advent.intcode.IntcodeComputer
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test

class Day5Test {
    @Test
    fun `read operator`() {
        val computer = IntcodeComputer(
                listOf(-1, -1, -1, -1, 3, 7, -1, -1), 4, input = listOf(111))
        Assertions.assertEquals(IntcodeComputer(
                listOf(-1, -1, -1, -1, 3, 7, -1, 111), 6),
                computer.run())
    }

    @Test
    fun `write operator`() {
        val computer = IntcodeComputer(
                listOf(-1, -1, -1, -1, 4, 7, -1, 222), 4)
        Assertions.assertEquals(IntcodeComputer(
                listOf(-1, -1, -1, -1, 4, 7, -1, 222), 6, output = listOf(222)),
                computer.run())
    }

    @Test
    fun `uses immediate parameters`() {
        val computer = IntcodeComputer(listOf(1101, 6, 5, 4, -1, 11, 22), 0)
        Assertions.assertEquals(
                IntcodeComputer(listOf(1101, 6, 5, 4, 11, 11, 22), 4),
                computer.run())
    }

    @Test
    fun `processes parameter indicators right-to-left`() {
        val computer = IntcodeComputer(listOf(1001, 6, 5, 4, -1, 11, 22), 0)
        Assertions.assertEquals(
                IntcodeComputer(listOf(1001, 6, 5, 4, 27, 11, 22), 4),
                computer.run())
    }
}
