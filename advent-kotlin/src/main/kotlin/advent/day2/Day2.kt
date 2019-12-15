package advent.day2

open class Machine(private val intcode: List<Int>, private val pos: Int = 0) {

    private fun apply(op: (Int, Int) -> Int): Machine {
        val result = op(intcode[intcode[pos + 1]], intcode[intcode[pos + 2]])
        val dest = intcode[pos + 3]
        return Machine(
            pos = pos + 4,
            intcode = intcode.mapIndexed { i, x -> if (i == dest) result else x }
        )
    }

    fun add() = apply(Int::plus)

    fun multiply() = apply(Int::times)

    /** Override run() to terminate recursion */
    fun end(): Machine =
        object : Machine(intcode, pos) {
            override fun run() = intcode
        }

    fun run1202() = Machine(intcode.withNounAndVerb(12, 2)).run()

    /** Recursively invoke program until we get to an end instruction */
    open fun run(): List<Int> =
        invoke(intcode[pos]).run()

    fun invoke(opcode: Int) =
        when (opcode) {
            1 -> add()
            2 -> multiply()
            99 -> end()
            else -> {
                throw IllegalStateException("opcode = $opcode")
            }
        }

    // ------------------- generated code below here -------------------

    override fun toString(): String {
        return "Machine(intcode=$intcode, pos=$pos)"
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is Machine) return false

        if (intcode != other.intcode) return false
        if (pos != other.pos) return false

        return true
    }

    override fun hashCode(): Int {
        var result = intcode.hashCode()
        result = 31 * result + pos
        return result
    }
}

val MEMORY = listOf(
    1, 0, 0, 3, 1, 1, 2, 3, 1, 3, 4, 3, 1, 5, 0, 3, 2, 10, 1, 19, 1, 19, 9, 23, 1,
    23, 6, 27, 2, 27, 13, 31, 1, 10, 31, 35, 1, 10, 35, 39, 2, 39, 6, 43, 1,
    43, 5, 47, 2, 10, 47, 51, 1, 5, 51, 55, 1, 55, 13, 59, 1, 59, 9, 63, 2, 9,
    63, 67, 1, 6, 67, 71, 1, 71, 13, 75, 1, 75, 10, 79, 1, 5, 79, 83, 1, 10, 83,
    87, 1, 5, 87, 91, 1, 91, 9, 95, 2, 13, 95, 99, 1, 5, 99, 103, 2, 103, 9, 107,
    1, 5, 107, 111, 2, 111, 9, 115, 1, 115, 6, 119, 2, 13, 119, 123, 1, 123, 5,
    127, 1, 127, 9, 131, 1, 131, 10, 135, 1, 13, 135, 139, 2, 9, 139, 143, 1, 5,
    143, 147, 1, 13, 147, 151, 1, 151, 2, 155, 1, 10, 155, 0, 99, 2, 14, 0, 0
)

/*
def solve_part_2():
    for noun in range(100):
        for verb in range(100):
            intcode = list(memory)
            intcode[1], intcode[2] = noun, verb
            result = run(intcode)[0]
            if result == 19690720:
                print(100 * noun + verb)
                return

 */


fun List<Int>.withNounAndVerb(noun: Int, verb: Int): List<Int> {
    return this.toMutableList().also {
        it[1] = noun
        it[2] = verb
    }.toList()
}

fun solvePart2() {
    val goal = 19690720

    for (noun in 0..99) {
        for (verb in 0..99) {
            val result = Machine(MEMORY.withNounAndVerb(noun, verb)).run()[0]
            if (result == goal) {
                println(noun * 100 + verb)
            }
        }
    }
}

fun main() {
    println(Machine(MEMORY).run1202()[0])
    solvePart2()
}
