package advent.day4

data class Result(val prev: Int = -1, val hasPair: Boolean = false, val nonDecreasing: Boolean = true)

fun isPasswordCandidate(guess: Int): Boolean {
    val digits = guess.toString().toCharArray().map { Character.getNumericValue(it) }
    if (digits.size == 6) {
        val result = digits.fold(Result()) { result, i ->
            result.copy(
                i,
                result.hasPair || i == result.prev,
                result.nonDecreasing && i >= result.prev
            )
        }
        return result.hasPair && result.nonDecreasing
    } else {
        return false
    }
}

fun main() {
    println((353096..843212).fold(0) { total, candidate -> total + if (isPasswordCandidate(candidate)) 1 else 0 })

    // part 1: 579
}
