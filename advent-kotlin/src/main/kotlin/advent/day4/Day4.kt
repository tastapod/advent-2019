package advent.day4

data class Result(
    val prev: Int = -1,
    val count: Int = 0,
    val hasPair: Boolean = false,
    val nonDecreasing: Boolean = true
)

fun isPasswordCandidate(guess: Int): Boolean {
    val digits = guess.toString().toCharArray().map { Character.getNumericValue(it) }

    if (digits.size != 6) return false

    var result = digits.fold(Result()) { a, i ->
        if (i != a.prev) {
            // end of a run
            Result(
                prev = i,
                count = 1,
                hasPair = a.hasPair || a.count == 2,
                nonDecreasing = a.nonDecreasing && i >= a.prev
            )
        } else {
            // in a run
            Result(
                prev = i,
                count = a.count + 1,
                hasPair = a.hasPair,
                nonDecreasing = a.nonDecreasing && i >= a.prev
            )
        }
    }
    // check for pair at the end
    result = result.copy(hasPair = result.hasPair || result.count == 2)

    return result.hasPair && result.nonDecreasing
}

fun main() {
    println((353096..843212).count() { isPasswordCandidate(it) })
    // part 1: 579
    // part 2: 358
}
