package advent.day2

import advent.intcode.IntcodeComputer
import advent.intcode.Operator
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertAll

class Day2Test {
    @Test
    fun `end operator`() {
        val state = IntcodeComputer(listOf(-1, -1, -1, -1, 99, 10, 9, 8, -1, 11, 22), 4)
        assertEquals(state, Operator.STOP(state))
    }

    @Test
    fun `add operator`() {
        val state = IntcodeComputer(listOf(-1, -1, -1, -1, 1, 10, 9, 8, -1, 11, 22), 4)
        assertEquals(
            IntcodeComputer(listOf(-1, -1, -1, -1, 1, 10, 9, 8, 33, 11, 22), 8),
            Operator.ADD(state)
        )
    }

    @Test
    fun `multiply operator`() {
        val state = IntcodeComputer(listOf(-1, -1, -1, -1, 1, 10, 9, 8, -1, 11, 22), 4)
        assertEquals(
            IntcodeComputer(listOf(-1, -1, -1, -1, 1, 10, 9, 8, 242, 11, 22), 8),
            Operator.MULTIPLY(state)
        )
    }

    @Test
    fun `run example 1`() {
        val state = IntcodeComputer(
            listOf(
                1, 9, 10, 3,
                2, 3, 11, 0,
                99,
                30, 40, 50
            )
        )

        assertEquals(
            listOf(
                3500, 9, 10, 70,
                2, 3, 11, 0,
                99,
                30, 40, 50
            ),
            state.run()
        )
    }

    private infix fun List<Int>.produces(expected: List<Int>) {
        assertEquals(expected, IntcodeComputer(this).run(), this.toString())
    }

    @Test
    fun `run mini`() {
        assertAll(
            { listOf(1, 0, 0, 0, 99) produces listOf(2, 0, 0, 0, 99) },
            { listOf(2, 3, 0, 3, 99) produces listOf(2, 3, 0, 6, 99) },
            { listOf(2, 4, 4, 5, 99, 0) produces listOf(2, 4, 4, 5, 99, 9801) },
            { listOf(1, 1, 1, 4, 99, 5, 6, 0, 99) produces listOf(30, 1, 1, 4, 2, 5, 6, 0, 99) }
        )
    }
}
