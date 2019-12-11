package advent.day4

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertAll

class Day4Test {
    private infix fun Int.proves(expected: Boolean) =
        assertEquals(expected, isPasswordCandidate(this), this.toString())

    @Test
    fun `allows valid password`() {
        assertAll(
            { 112345 proves true },
            { 111111 proves true }
        )
    }

    @Test
    fun `fails unless six digits`() {
        assertAll(
            {11345 proves false},
            {113456 proves true},
            {1134567 proves false}
        )
    }

    @Test
    fun `fails unless double digit`() {
        assertAll(
            {113456 proves true},
            {123455 proves true},
            {123456 proves false}
        )
    }

    @Test
    fun `fails unless non-decreasing`() {
        assertAll(
            {113456 proves true},
            {123455 proves true},
            {113454 proves false}
        )
    }
}

